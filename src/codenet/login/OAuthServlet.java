package codenet.login;

import java.io.Console;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import codenet.bean.UserBean;
import codenet.constant.githubConstant;
import codenet.jdbc.UserJDBC;

@WebServlet(urlPatterns="/login/github/oauth")
public class OAuthServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(getClass());


    /**
     * @see HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//		UserBean userBean = UserJDBC.get(7792075l);
//		request.getSession().setAttribute("user", userBean);
		String from = request.getParameter("from");
		System.out.println(from);
		request.getSession().setAttribute("from", from);
        String authorizeUrl = String.format("https://github.com/login/oauth/authorize?client_id=%s&state=%s&redirect_uri=%s", githubConstant.clientId, githubConstant.clientId, githubConstant.redirectUri);
        logger.debug(authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
}
