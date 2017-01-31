package servlets.admin;

import entities.Bus;
import entities.Timetable;
import entities.User;
import services.BusService;
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
import java.util.*;

/**
 * Created by maxbacinskiy on 27.01.17.
 */
public class AdminNewTimetableServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        pageVariables.put("status", "");

        String id = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(id);

        if (nowUser == null || !nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {
            pageVariables.put("buses", BusService.getAllBuses());
            pageVariables.put("timetables", TimetableService.getAllTimetables());
            response.getWriter().println(PageGenerator.instance().getPage("admin_add_timetable.html", pageVariables));
        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");

        String mode = request.getParameter("mode");
        String busNumString = request.getParameter("bus");

        switch (mode) {
            case "once":
                String timeString = request.getParameter("once_time");


                if (!ServletHelper.checkVariables(busNumString, timeString)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "You have input empty field. Please, return.");
                }
                int busNum = Integer.parseInt(busNumString);
                Bus bus = BusService.getBus(busNum);
                Date date;
                try {
                    date = timeFormat.parse(timeString);
                } catch (ParseException e) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "Bullshit with time parsing");
                    return;
                }
                Time time = new Time(date.getTime());

                if (TimetableService.isCreated(bus, time)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "You have added this timetable also.");
                } else {
                    TimetableService.addTimetable(new Timetable(bus, time));
                    sendPage(response, pageVariables, "New timetable has added successfully");
                }

                break;

            case "multi":
                String fromTime = request.getParameter("multi_time_begin");
                String toTime = request.getParameter("multi_time_end");
                String intervalString = request.getParameter("multi_interval");

                // parse time, parse bus, for-loop
                if (!ServletHelper.checkVariables(busNumString, fromTime, toTime, intervalString)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "You have input empty field. Please, return.");
                    return;
                } else if (fromTime.equals(toTime)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "You have input equal times. Please, return.");
                    return;
                }

                int interval = Integer.parseInt(intervalString);
                if (interval <= 0 || interval >= 3600) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "You have input wrong interval. Please, return.");
                    return;
                }

                busNum = Integer.parseInt(busNumString);
                bus = BusService.getBus(busNum);

                Date date1;
                Date date2;
                try {
                    date1 = timeFormat.parse(fromTime);
                    date2 = timeFormat.parse(toTime);
                } catch (ParseException e) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    sendPage(response, pageVariables, "Bullshit with time parsing");
                    return;
                }

                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(date1);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(date2);
                if (date1.getTime() > date2.getTime()) {
                    cal2.add(Calendar.DATE, 1);
                }

                while (cal1.compareTo(cal2) <= 0) {
                    Time nowTime = new Time(cal1.getTime().getTime());
                    if (!TimetableService.isCreated(bus, nowTime)) {
                        TimetableService.addTimetable(new Timetable(bus, nowTime));
                    }
                    cal1.add(Calendar.MINUTE, interval);
                }
                sendPage(response, pageVariables, "New timetables has added successfully");


                break;
        }


    }

    private void sendPage(HttpServletResponse response, Map<String, Object> pageVariables, String status)
            throws IOException {
        pageVariables.put("status", status);
        pageVariables.put("timetables", TimetableService.getAllTimetables());
        pageVariables.put("buses", BusService.getAllBuses());
        response.getWriter().println(PageGenerator.instance().getPage("admin_add_timetable.html", pageVariables));
    }

}
