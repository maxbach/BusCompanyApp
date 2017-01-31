package servlets.user;

import entities.Timetable;
import entities.User;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserFindBusServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        pageVariables.put("status", "");

        String id = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(id);

        if (nowUser == null || nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {

            String stationA = request.getParameter("stationA");
            String stationB = request.getParameter("stationB");
            String timeA = request.getParameter("time_begin");
            String timeB = request.getParameter("time_end");

            String status;

            if (!ServletHelper.checkVariables(stationA, stationB, timeA, timeB)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                status = "You have empty fields.";
                pageVariables.put("ListOfBuses", null);
            } else if (stationA.equals(stationB)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                status = "You have chosen equal stations. Please, return.";
            } else if (timeA.equals(timeB)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                status = "You have chosen equal times. Please, return.";
            } else {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                Date date1;
                Date date2;
                try {
                    date1 = timeFormat.parse(timeA);
                    date2 = timeFormat.parse(timeB);
                    Time time1 = new Time(date1.getTime());
                    Time time2 = new Time(date2.getTime());
                    response.setStatus(HttpServletResponse.SC_OK);
                    List<Timetable> timetables = TimetableService.queryTimetable(stationA, stationB, time1, time2);

                    status = "All is OK!";
                    pageVariables.put("listOfTimetables", timetables);
                } catch (ParseException e) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    status = "Bullshit with a time";
                }

            }

            List<String> stations = StationService.getAllStationNames();
            pageVariables.put("status", status);
            pageVariables.put("stations", stations);
            response.getWriter().println(PageGenerator.instance().getPage("user_find_bus.html", pageVariables));
        }



    }



}
