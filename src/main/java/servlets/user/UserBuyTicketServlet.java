package servlets.user;

import entities.Ticket;
import entities.Timetable;
import entities.User;
import services.StationService;
import services.TicketService;
import services.TimetableService;
import services.UserSessionService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxbacinskiy on 28.01.17.
 */
public class UserBuyTicketServlet extends HttpServlet {
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("stations", StationService.getAllStationNames());
        String id = request.getSession().getId();
        User nowUser = UserSessionService.getUserBySession(id);
        String timetableID = request.getParameter("timetable");
        Integer ttID = Integer.parseInt(timetableID);
        Timetable timetable = TimetableService.getTimetableById(ttID);

        Calendar nowCal = Calendar.getInstance();
        nowCal.add(Calendar.MINUTE, 10);
        Time nowTime = new Time(nowCal.getTimeInMillis());


        if (compareTimes(nowTime, timetable.getTime()) > 0) {
            pageVariables.put("status", "You are late. This tickets are not for sale");
        } else if (TicketService.hasPassengersOfTimetableWithNameAndDateBirth(timetable, nowUser.getFirstName(),
                nowUser.getSecondName(), nowUser.getDateOfBirth())) {
            pageVariables.put("status", "You have ever bought ticket for this bus");
        } else if (TicketService.howManyTicketsBought(timetable) == timetable.getBus().getSeats()) {
            pageVariables.put("status", "We're so sorry. There are no tickets for this bus");
        } else {
            pageVariables.put("status", "All is OK. You have successfully bought ticket.");
            TicketService.addTicket(new Ticket(timetable, nowUser));
        }
        response.getWriter().println(PageGenerator.instance().getPage("user_find_bus.html", pageVariables));


    }

    public int compareTimes(Date d1, Date d2)
    {
        int     t1;
        int     t2;

        t1 = (int) (d1.getTime() % (24*60*60*1000L));
        t2 = (int) (d2.getTime() % (24*60*60*1000L));
        return (t1 - t2);
    }
}
