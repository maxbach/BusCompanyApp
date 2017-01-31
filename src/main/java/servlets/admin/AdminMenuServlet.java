package servlets.admin;

import entities.User;
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
 * Created by maxbacinskiy on 25.01.17.
 */
public class AdminMenuServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        String id = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(id);


        if (nowUser == null || !nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {
            pageVariables.put("name", nowUser.getFirstName());
            response.getWriter().println(PageGenerator.instance().getPage("admin_menu.html", pageVariables));
        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
