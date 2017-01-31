package servlets.user;

import entities.Bus;
import entities.Timetable;
import entities.User;
import services.BusService;
import services.StationService;
import services.TimetableService;
import services.UserSessionService;
import servlets.main.ServletHelper;
import templater.PageGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maxbacinskiy on 29.01.17.
 */
public class UserStationTimetableServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String sessionId = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(sessionId);

        String station = request.getParameter("station");


        if (nowUser==null || nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {

            pageVariables.put("stations", StationService.getAllStationNames());

            if (ServletHelper.checkVariables(station)) {
                List<Bus> buses = BusService.getBusesByBeginStation(station);
                pageVariables.put("timetables", TimetableService.getTimetableOfBuses(buses));
                pageVariables.put("status", "All is Ok");
            } else {
                pageVariables.put("status", "Fill the fields");
            }
            response.getWriter().println(PageGenerator.instance().getPage("user_station_timetable.html", pageVariables));


        }
    }
}
