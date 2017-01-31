package servlets.admin;

import entities.Station;
import entities.User;
import services.StationService;
import services.UserSessionService;
import servlets.main.ServletHelper;
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
public class AdminNewStationServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("status", "");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String id = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(id);

        if (nowUser == null || !nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {
            pageVariables.put("stations", StationService.getAllStationNames());
            response.getWriter().println(PageGenerator.instance().getPage("admin_add_station.html", pageVariables));
        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();
        String stationName = request.getParameter("login");
        String status;

        if (ServletHelper.checkVariables(stationName)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "You have input empty field. Please, return.";
        } else {
            Station station = StationService.getStation(stationName);
            if (station != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                status = "You have input existing station. Please, return.";
            } else {
                StationService.addStation(stationName);
                status = "New station was successfully added: " + stationName;
            }
        }
        pageVariables.put("status", status);
        pageVariables.put("stations", StationService.getAllStationNames());
        response.getWriter().println(PageGenerator.instance().getPage("admin_add_station.html", pageVariables));


    }
}
