package codenet.dataset;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import codenet.bean.CommentBean;
import codenet.bean.DatasetBean;
import codenet.bean.DiscussBean;
import codenet.bean.UserBean;
import codenet.jdbc.DatasetJDBC;
import codenet.jdbc.DiscussJDBS;
import codenet.jdbc.UserJDBC;
import codenet.service.DatasetService;
import codenet.utils.EnvironmentProperty;

@WebServlet(urlPatterns="/list")
public class DatasetServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		switch (action) {
		case "getAll": getAll(req, resp); break;
		case "dowload": download(req, resp); break;
		case "upload": upload(req, resp); break;
		case "get": get(req, resp);break;
		case "getDiscuss": getDiscuss(req, resp); break;
		case "getComment": getComment(req, resp); break;
		case "post": post(req, resp); break;
		case "user": user(req, resp); break;
		case "logout": logout(req, resp); break;
		case "rate": rate(req, resp);break;
		case "registry": registry(req, resp); break;
		case "login": login(req, resp); break;
		default: break;
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		List<DatasetBean> ds = DatasetJDBC.getAll();
		Gson builder = new GsonBuilder().create();
		
		JsonElement element = builder.toJsonTree(ds);
		JsonArray jsonArray = element.getAsJsonArray();
		for (int i=0;i<jsonArray.size();i++) {
			JsonElement element2 = jsonArray.get(i);
			JsonObject object = element2.getAsJsonObject();
            int user=object.get("rateuser").getAsInt();
			int rate=object.get("rate").getAsInt();
			object.addProperty("rateval", user==0?"0.0":String.format("%.1f", rate/(user+.0)));
		}
		
		resp.getWriter().write(element.toString());
		//resp.getWriter().write(builder.toJson(ds));
	}
	
	private void get(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		List<DatasetBean> ds = DatasetJDBC.get(id);
		Gson builder = new GsonBuilder().create();
		JsonElement element = builder.toJsonTree(ds);
		JsonArray jsonArray = element.getAsJsonArray();
		for (int i=0;i<jsonArray.size();i++) {
			JsonElement element2 = jsonArray.get(i);
			JsonObject object = element2.getAsJsonObject();
			int user=object.get("rateuser").getAsInt();
			int rate=object.get("rate").getAsInt();
			object.addProperty("rateval", user==0?"0.0":String.format("%.1f", rate/(user+.0)));
		}
		
		resp.getWriter().write(element.toString());
	}
	
	private void getDiscuss(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		List<DiscussBean>  list = DiscussJDBS.getDiscuss(id);
		Gson builder = new GsonBuilder().create();
		JsonElement element = builder.toJsonTree(list);
		JsonArray jsonArray = element.getAsJsonArray();
		for (int i=0;i<jsonArray.size();i++) {
			JsonObject object = jsonArray.get(i).getAsJsonObject();
			List<CommentBean> comments = DiscussJDBS.getComments(object.get("id").getAsInt());
			object.add("comments", builder.toJsonTree(comments));
			object.addProperty("cnt", comments.size());
			object.addProperty("date", 
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(object.get("timestamp").getAsLong())));
		}
		JsonObject object = new JsonObject();
		object.add("data", element);
		//System.out.println(element.toString());
		//resp.getWriter().write(builder.toJson(ob));
		resp.getWriter().write(object.toString());
	}
	
	private void getComment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Gson builder = new GsonBuilder().create();
		List<CommentBean> comments = DiscussJDBS.getComments(id);
		JsonObject object = new JsonObject();
		object.add("comments", builder.toJsonTree(comments));
		resp.getWriter().write(object.toString());
	}
	
	private void post(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		long uid = Long.parseLong(req.getParameter("uid"));
		String _id = req.getParameter("id");
		int id = 0;
		if (_id != null && !"".equals(_id) && !"new".equals(_id)) id=Integer.parseInt(_id);
		int did = Integer.parseInt(req.getParameter("did"));
		String content = req.getParameter("content");
		JsonObject object = new JsonObject();
		if (DiscussJDBS.post(uid, did, id, content)) {
			object.addProperty("code", 0);
		}
		else {
			object.addProperty("code", 1);
			object.addProperty("msg", "Error!");
		}
		resp.getWriter().write(object.toString());
	}
	
	private void rate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		int rate=(int)(Double.parseDouble(req.getParameter("rate"))*2);
		DatasetJDBC.rate(id, rate);
		//resp.getWriter().write("{\"code\": 0}");
	}
	
	
	private static String DowloadAddr = EnvironmentProperty.readConf("DowloadAddr");
	private void download(HttpServletRequest req, HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("filename");
		String path = String.format("%s%s", DowloadAddr, name);
		if(DatasetJDBC.dowload(id)){
			try(InputStream in = new BufferedInputStream(new FileInputStream(path));
			          OutputStream out = resp.getOutputStream();){
			  resp.setContentType(getServletContext().getMimeType(name));
	          resp.setHeader("Content-Disposition", "attachment;filename=" + name);
	         
	          int temp;
	          while ((temp = in.read()) != -1) {
	              out.write(temp);
	          }
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void upload(HttpServletRequest req,
							  HttpServletResponse resp){
		try{
			String path = DowloadAddr+req.getParameter("username")+"/";
			File temp = new File(path);
			if(!temp.exists())temp.mkdirs();
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			PrintWriter pw = resp.getWriter();
			diskFactory.setSizeThreshold(4 * 1024);
			diskFactory.setRepository(temp);
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			upload.setSizeMax(100 * 1024 * 1024);
			List<FileItem> fileItems = upload.parseRequest(req);
			Iterator<FileItem> iter = fileItems.iterator();
			while(iter.hasNext())
			{
				FileItem item = iter.next();
				if(item.isFormField())
				{
					DatasetService.processFormField(item, pw);
				}else{
					DatasetService.processUploadFile(item, pw, path);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void user(HttpServletRequest req, HttpServletResponse resp){
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		Gson builder = new GsonBuilder().create();
		try {
			resp.getWriter().write(builder.toJson(user));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp){
		req.getSession().removeAttribute("user");
	}
	
	private void registry(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("user"),
				password = req.getParameter("pass"),
				email = req.getParameter("email");
		String avatar = "/codenet/static/images/no-avatar.jpg";
		
		// check if username exists
		JsonObject object = new JsonObject();
		if (UserJDBC.exists(username)) {
			object.addProperty("code", 1);
			object.addProperty("msg", "Username exists!");
			resp.getWriter().write(object.toString());
			return;
		}
		if (UserJDBC.exists(email)) {
			object.addProperty("code", 1);
			object.addProperty("msg", "Email exists!");
			resp.getWriter().write(object.toString());
			return;
		}
		
		while (true) {
			long id=new Random().nextLong()/10000;
			if (UserJDBC.get(id)!=null) continue;
			
			UserBean user = new UserBean();
			user.setId(id);
			user.setUsername(username);
			user.setAvatar(avatar);
			user.setPassword(password);
			user.setEmail(email);
			UserJDBC.getOrInsert(user);
			object.addProperty("code", 0);
			resp.getWriter().write(object.toString());
			return;
		}
		
	}
	
	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String username = req.getParameter("user"),
				password = req.getParameter("pass");
		UserBean user = UserJDBC.login(username);
		JsonObject object = new JsonObject();
		if (user == null) {
			object.addProperty("code", 1);
			object.addProperty("msg", "User not exists!");
			resp.getWriter().write(object.toString());
			return;
		}
		if (!password.equals(user.getPassword())) {
			object.addProperty("code", 1);
			object.addProperty("msg", "Wrong password!");
			resp.getWriter().write(object.toString());
			return;
		}
		req.getSession().setAttribute("user", user);
		object.addProperty("code", 0);
		resp.getWriter().write(object.toString());
	}
	
}
