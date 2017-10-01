package codenet.dataset;

import java.io.File;
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
import codenet.bean.PreviewBean;
import codenet.bean.UserBean;
import codenet.jdbc.DatasetJDBC;
import codenet.jdbc.DiscussJDBC;
import codenet.jdbc.UserJDBC;
import codenet.utils.EnvironmentProperty;
import codenet.utils.Utils;

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
		case "download": download(req, resp); break;
		case "uploadFile": uploadFile(req, resp); break;
		case "uploadDataset": uploadDataset(req, resp); break;
		case "get": get(req, resp);break;
		case "getDiscuss": getDiscuss(req, resp); break;
		case "getComment": getComment(req, resp); break;
		case "post": post(req, resp); break;
		case "user": user(req, resp); break;
		case "logout": logout(req, resp); break;
		case "rate": rate(req, resp);break;
		case "registry": registry(req, resp); break;
		case "mylist": mylist(req, resp); break;
		case "login": login(req, resp); break;
		case "delete": delete(req, resp); break;
		case "checkUploadFilename": checkUploadFilename(req, resp); break;
		case "preview": preview(req, resp); break;
		case "getPreview": getPreview(req, resp); break;
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
		List<DiscussBean>  list = DiscussJDBC.getDiscuss(id);
		Gson builder = new GsonBuilder().create();
		JsonElement element = builder.toJsonTree(list);
		JsonArray jsonArray = element.getAsJsonArray();
		for (int i=0;i<jsonArray.size();i++) {
			JsonObject object = jsonArray.get(i).getAsJsonObject();
			List<CommentBean> comments = DiscussJDBC.getComments(object.get("id").getAsInt());
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
		List<CommentBean> comments = DiscussJDBC.getComments(id);
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
		if (DiscussJDBC.post(uid, did, id, content)) {
			object.addProperty("code", 0);
		}
		else {
			message(resp, 1, "Error!");
			return;
		}
		resp.getWriter().write(object.toString());
	}
	
	private void rate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		int rate=(int)(Double.parseDouble(req.getParameter("rate"))*2);
		DatasetJDBC.rate(id, rate);
		//resp.getWriter().write("{\"code\": 0}");
	}
	
	
	private static String dowloadAddr = EnvironmentProperty.readConf("DowloadAddr");
	private static String tmpAddr = EnvironmentProperty.readConf("TmpAddr");
	private void download(HttpServletRequest req, HttpServletResponse resp){
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("filename");
		String username = req.getParameter("contributor");
		String path = String.format("%s%s/%s", dowloadAddr, username, name);
		if(DatasetJDBC.dowload(id)){
			try(InputStream in = new BufferedInputStream(new FileInputStream(path));
			          OutputStream out = resp.getOutputStream()){
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

	private void uploadFile(HttpServletRequest req,
							HttpServletResponse resp) throws IOException{
		try{
			UserBean user = (UserBean) req.getSession().getAttribute("user");
			if (user == null) {
				message(resp, 1, "Not login!");
				return;
			}

			// String path = dowloadAddr+user.getUsername()+"/";
			String path=tmpAddr;
			File temp = new File(path);
			if(!temp.exists())temp.mkdirs();

			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setSizeThreshold(4 * 1024);
			diskFactory.setRepository(temp);
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			upload.setSizeMax(1024L * 1024L * 1024L);
			List<FileItem> fileItems = upload.parseRequest(req);
			Iterator<FileItem> iter = fileItems.iterator();
			while(iter.hasNext())
			{
				FileItem item = iter.next();
				if (!item.isFormField()) {
					String filename = item.getName();
					System.out.println("filename：" + filename);
					/*int index = Math.max(filename.lastIndexOf("\\"), filename.lastIndexOf("/"));
					filename = filename.substring(index + 1, filename.length()); */
					long fileSize = item.getSize();
					String name=System.currentTimeMillis()+"";

					if(fileSize == 0)
					{
						message(resp, 1, "Empty file!");
						return;
					}
					File uploadFile = new File(tmpAddr +  name);
					item.write(uploadFile);
					JsonObject object=new JsonObject();
					object.addProperty("code", 0);
					object.addProperty("size", Utils.getFileSize(fileSize));
					object.addProperty("name", name);
					resp.getWriter().write(object.toString());
					// message(resp, 0, Utils.getFileSize(fileSize));
				}
			}
		} catch (Exception e){
			message(resp, 1, e.getMessage());
			e.printStackTrace();
		}
	}

	private void uploadDataset(HttpServletRequest req,
							   HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		String title=req.getParameter("title"), filename=req.getParameter("filename"), size=req.getParameter("size"),
				tmpname=req.getParameter("tmpname"), shortdetail=req.getParameter("shortdetail"),
				detail=req.getParameter("detail"), reference=req.getParameter("reference");
		detail=Utils.markdownToHtml(detail);
		if (DatasetJDBC.insert(title, filename, shortdetail, detail, user.getUsername(), reference, size)) {
			String toPath=dowloadAddr+user.getUsername()+"/"+filename;
			File toFile=new File(toPath);
			toFile.delete();
			File tmpFile=new File(tmpAddr+tmpname);
			tmpFile.renameTo(toFile);
			DatasetJDBC.deletePreview(Integer.parseInt(req.getParameter("id")), user.getUsername());
			message(resp, 0, "Success");
		}
		else {
			new File(tmpAddr+tmpname).delete();
			message(resp, 1, "Failed!");
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
			message(resp, 1, "Username exists!");
			return;
		}
		if (UserJDBC.exists(email)) {
			message(resp, 1, "Email exists!");
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
			message(resp, 1, "User not exists!");
			return;
		}
		if (!password.equals(user.getPassword())) {
			message(resp, 1, "Wrong Password!");
			return;
		}
		req.getSession().setAttribute("user", user);
		object.addProperty("code", 0);
		resp.getWriter().write(object.toString());
	}

	private void mylist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		JsonObject object = new JsonObject();
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		object.addProperty("code", 0);
		List<DatasetBean> ds = DatasetJDBC.getAll(user.getUsername());
		JsonArray jsonArray=new JsonArray();
		for (int i=0;i<ds.size();i++) {
			DatasetBean bean=ds.get(i);
			JsonObject one=new JsonObject();
			one.addProperty("id", bean.getId());
			one.addProperty("filename", bean.getFilename());
			one.addProperty("size", bean.getSize());
			one.addProperty("downloads", bean.getDownloads());
			jsonArray.add(one);
		}
		object.add("data", jsonArray);
		resp.getWriter().write(object.toString());
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		int id = Integer.parseInt(req.getParameter("id"));
		List<DatasetBean> ds = DatasetJDBC.get(id);
		if (ds.isEmpty()) {
			message(resp, 1, "Dataset Not Found!");
			return;
		}
		DatasetBean dataset=ds.get(0);
		if (!dataset.getContributor().equals(user.getUsername())) {
			message(resp, 1, "You are not the owner!");
			return;
		}
		// delete
		if (DatasetJDBC.delete(id)) {
			new File(dowloadAddr+user.getUsername()+"/"+dataset.getFilename()).delete();
			message(resp, 0, "Success!");
		}
		else {
			message(resp, 1, "Error!");
		}
	}

	private void checkUploadFilename(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		String filename=req.getParameter("filename");
		if (filename==null || "".equals(filename)) {
			message(resp, 1, "Invalid filename!");
			return;
		}
		List<DatasetBean> ds = DatasetJDBC.getAll(user.getUsername());
		for (DatasetBean bean: ds) {
			if (bean.getFilename().equals(filename)) {
				message(resp, 1,"Filename exists!");
				return;
			}
		}
		message(resp, 0, "Ok");
	}

	private void preview(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		int id=Integer.parseInt(req.getParameter("id"));
		String title=req.getParameter("title"), content=req.getParameter("content");
		content=Utils.markdownToHtml(content);
		String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		id=DatasetJDBC.preview(id, user.getUsername(), title, content, time);
		if (id>0) {
			message(resp, 0, id+"");
		}
		else {
			message(resp, 1, "Failed!");
		}
	}

	private void getPreview(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserBean user = (UserBean) req.getSession().getAttribute("user");
		if (user == null) {
			message(resp, 1, "Not login!");
			return;
		}
		int id=Integer.parseInt(req.getParameter("id"));
		List<PreviewBean> list=DatasetJDBC.getPreview(id, user.getUsername());
		if (list==null || list.isEmpty()) {
			message(resp, 1, "Nothing to preview!");
		}
		else {
			JsonObject object=new JsonObject();
			object.addProperty("code", 0);
			object.add("data", new Gson().toJsonTree(list.get(0)));
			resp.getWriter().write(object.toString());
		}
	}

	private void message(HttpServletResponse resp, int code, String msg) throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("code", code);
		object.addProperty("msg", msg);
		resp.getWriter().write(object.toString());
	}
	
}
