package codenet.dataset;

import java.util.List;
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







import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import codenet.bean.DatasetBean;
import codenet.jdbc.DatasetJDBC;
import codenet.utils.EnvironmentProperty;

@WebServlet(urlPatterns="/list")
public class DatasetServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		switch (action) {
		case "getAll": getAll(req, resp); break;
		case "dowload": download(req, resp); break;
		case "get": get(req, resp);break;
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
		resp.getWriter().write(builder.toJson(ds));
	}
	
	private void get(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		List<DatasetBean> ds = DatasetJDBC.get(id);
		Gson builder = new GsonBuilder().create();
		resp.getWriter().write(builder.toJson(ds));
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
	
}
