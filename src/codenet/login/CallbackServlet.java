package codenet.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import codenet.bean.UserBean;
import codenet.constant.githubConstant;
import codenet.jdbc.UserJDBC;
import codenet.utils.HttpUtils;

@WebServlet(urlPatterns="/login/github/callback")
public class CallbackServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code = req.getParameter("code");
		HashMap<String, String > map = new HashMap<>();
		map.put("client_id", githubConstant.clientId);
		map.put("client_secret", githubConstant.clientSecret);
		map.put("code", code);
		String access_token = HttpUtils.post("https://github.com/login/oauth/access_token", map);
		logger.info(access_token);
		access_token = access_token.substring(13);
		map = new HashMap<>();
		map.put("access_token", access_token);
		StringBuilder arg =  new StringBuilder();
		for(Map.Entry<String, String> en : map.entrySet()){
			arg.append(en.getKey()+"="+en.getValue()).append('&');
		}
		arg.deleteCharAt(arg.length()-1);
		req.getSession().getServletContext().log(arg.toString());
		String userInfo = HttpUtils.get("https://api.github.com/user", map);
		resp.getWriter().write(userInfo);
		resp.getWriter().flush();
		Gson builder = new GsonBuilder().create();
		UserBean user = builder.fromJson(userInfo, UserBean.class);
//		System.out.println(user);
		user = UserJDBC.getOrInsert(user);
//		String userInfoUri = "https://api.github.com/user?"+access_token;
//		resp.sendRedirect(userInfoUri);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
