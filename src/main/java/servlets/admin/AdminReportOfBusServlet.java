package servlets.admin;

import entities.Bus;
import entities.Timetable;
import entities.User;
import services.BusService;
import services.TicketService;
import services.TimetableService;
import services.UserSessionService;
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

/**
 * Created by maxbacinskiy on 30.01.17.
 */
public class AdminReportOfBusServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String sessionId = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(sessionId);
        String bus = request.getParameter("bus");
        String time = request.getParameter("time");

        if (nowUser == null || !nowUser.isAdmin()) {
            RequestDispatcher rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);
        } else {
            List<Bus> buses = BusService.getAllBuses();
            pageVariables.put("listOfBuses", buses);
            if (bus == null) {
                pageVariables.put("selectedBus", null);
                pageVariables.put("listOfTimes", null);
                pageVariables.put("selectedTime", null);
                pageVariables.put("listOfUsers", null);
                response.getWriter().println(PageGenerator.instance().getPage("/admin_report_bus.html", pageVariables));
            } else {
                String numOfBus = bus.split(" ")[0];
                Integer busId = Integer.parseInt(numOfBus);
                pageVariables.put("selectedBus", busId);
                Bus goodBus = BusService.getBus(busId);
                List<Timetable> timetables = TimetableService.getTimetableOfBus(goodBus);
                pageVariables.put("listOfTimes", timetables);
                if (time == null) {
                    pageVariables.put("listOfUsers", null);
                    pageVariables.put("selectedTime", null);
                    response.getWriter().println(PageGenerator.instance().getPage("/admin_report_bus.html", pageVariables));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date;
                    try {
                        date = format.parse(time);
                    } catch (ParseException e) {
                        pageVariables.put("listOfUsers", null);
                        response.getWriter().println(PageGenerator.instance().getPage("/admin_report_bus.html", pageVariables));
                        return;
                    }
                    Time nowTime = new Time(date.getTime());
                    Timetable tt = TimetableService.getTimetebleByTimeAndBus(goodBus, nowTime);
                    pageVariables.put("selectedTime", tt.getId());
                    pageVariables.put("listOfUsers", TicketService.getPassengerOfTime(tt));
                    response.getWriter().println(PageGenerator.instance().getPage("/admin_report_bus.html", pageVariables));
                }
            }
        }
    }
}
