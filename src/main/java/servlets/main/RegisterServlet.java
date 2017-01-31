package servlets.main;

import entities.User;
import services.UserService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxbacinskiy on 23.01.17.
 */
public class RegisterServlet extends HttpServlet {
    public RegisterServlet() {
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("status", "");

        response.getWriter().println(PageGenerator.instance().getPage("register.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String dateOfBirth = request.getParameter("dateBirth");
        String passwordAgain = request.getParameter("passwordAgain");
        boolean isAllOk = true;

        response.setContentType("text/html;charset=utf-8");

        if (!checkVariables(login, password, firstName, secondName, dateOfBirth, passwordAgain)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            pageVariables.put("status", "You have input empty field. Please, return.");
            isAllOk = false;
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }


        if (isAllOk) {
            User user = UserService.getUser(login);
            if (user != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                pageVariables.put("status", "You have input login, which is used. Please, return.");
                isAllOk = false;
            } else {
                if (password.length() < 7) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    pageVariables.put("status", "You have input short password. Please, return.");
                    isAllOk = false;
                } else if (!password.equals(passwordAgain)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    pageVariables.put("status", "Your passwords aren't equals. Please, return.");
                    isAllOk = false;
                }
            }
        }

        if (isAllOk) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                UserService.addSimpleUser(login, password, firstName, secondName, formatter.parse(dateOfBirth));
                pageVariables.put("status", "");
            } catch (ParseException e) {
                UserService.addSimpleUser(login, password, firstName, secondName, Calendar.getInstance().getTime());
                pageVariables.put("status", "Something wrong with date of birth.");
            }

            response.getWriter().println(PageGenerator.instance().getPage("login.html", pageVariables));
        } else {
            response.getWriter().println(PageGenerator.instance().getPage("register.html", pageVariables));
        }

    }


    private boolean checkVariables(String... variables) {
        int checkVars = 0;
        for(String var : variables) {
            if (var.isEmpty()) return false;
            checkVars++;
        }
        return checkVars == variables.length;
    }
}
