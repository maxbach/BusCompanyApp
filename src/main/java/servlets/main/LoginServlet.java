package servlets.main;

import entities.User;
import services.UserService;
import services.UserSessionService;
import templater.PageGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxbacinskiy on 23.01.17.
 */
public class LoginServlet extends HttpServlet {

    public LoginServlet() {
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("status", "");

        String idSession = request.getSession().getId();
        User userBySession = UserSessionService.getUserBySession(idSession);
        if (userBySession != null) {
            pageVariables.put("name", userBySession.getFirstName());
            if (userBySession.isAdmin()) {
                response.getWriter().println(PageGenerator.instance().getPage("admin_menu.html", pageVariables));
            } else {
                response.getWriter().println(PageGenerator.instance().getPage("user_menu.html", pageVariables));

            }
        } else {
            response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean isAllOk = true;

        response.setContentType("text/html;charset=utf-8");

        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            pageVariables.put("status", "You have input empty login or password. Please, return.");
            isAllOk = false;
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        User user = null;

        if (isAllOk) {
            user = UserService.getUser(login);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                pageVariables.put("status", "You have input wrong login. Please, return.");
                isAllOk = false;
            } else {
                user = UserService.getUser(login);
                if (!user.getPassword().equals(password)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    pageVariables.put("status", "You have input wrong password. Please, return.");
                    isAllOk = false;
                }
            }
        }

        if (isAllOk) {
            String idSession = request.getSession().getId();
            UserSessionService.addSession(idSession, user);
            String address = user.isAdmin() ? "admin_menu.html" : "user_menu.html";
            RequestDispatcher rd = request.getRequestDispatcher(address);
            rd.forward(request, response);
        } else {
            response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));
        }

    }


    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String idSession = request.getSession().getId();
        UserSessionService.deleteSession(idSession);
    }

}
