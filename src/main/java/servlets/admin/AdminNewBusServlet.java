package servlets.admin;

import entities.User;
import services.BusService;
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
public class AdminNewBusServlet extends HttpServlet {
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
            pageVariables.put("buses", BusService.getAllBuses());
            response.getWriter().println(PageGenerator.instance().getPage("admin_add_bus.html", pageVariables));
        }



    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> pageVariables = new HashMap<>();

        String numberOfBusString = request.getParameter("numberOfBus");
        String seatsString = request.getParameter("quanitySeats");
        String stationA = request.getParameter("stationA");
        String stationB = request.getParameter("stationB");

        int numberOfBus = Integer.parseInt(numberOfBusString);
        int seats = Integer.parseInt(seatsString);
        String status;
        if (!ServletHelper.checkVariables(numberOfBusString, seatsString, stationA, stationB)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "You have input empty field. Please, return.";
        } else if (stationA.equals(stationB)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "You have chosen equals stations. Please, return.";
        } else if (0 >= seats || seats >= Integer.MAX_VALUE) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "You have typed wrong number of seats. Please, return.";
        } else if (BusService.getBus(numberOfBus) != null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "This bus have been also created before you. Please, return.";
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            status = "Bus has successfully added";
            BusService.addBus(numberOfBus, seats, stationA, stationB);
        }

        pageVariables.put("status", status);
        pageVariables.put("stations", StationService.getAllStationNames());
        pageVariables.put("buses", BusService.getAllBuses());
        response.getWriter().println(PageGenerator.instance().getPage("admin_add_bus.html", pageVariables));

    }



}
