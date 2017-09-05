package codenet.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import codenet.constant.githubConstant;

@WebServlet(urlPatterns="/login/github/oauth")
public class OAuthServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(getClass());


    /**
     * @see HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
