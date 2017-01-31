package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.UserService;
import servlets.admin.*;
import servlets.main.LoginServlet;
import servlets.main.RegisterServlet;
import servlets.user.UserBuyTicketServlet;
import servlets.user.UserFindBusServlet;
import servlets.user.UserMenuServlet;
import servlets.user.UserStationTimetableServlet;

/**
 * Created by maxbacinskiy on 23.01.17.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        UserService.addFirstAdmins();

        LoginServlet loginServlet = new LoginServlet();
        RegisterServlet registerServlet = new RegisterServlet();
        AdminMenuServlet adminMenuServlet = new AdminMenuServlet();
        UserMenuServlet userMenuServlet = new UserMenuServlet();
        AdminNewStationServlet addingNewStationServlet = new AdminNewStationServlet();
        AdminNewBusServlet addingNewBusServlet = new AdminNewBusServlet();
        UserFindBusServlet findBusServlet = new UserFindBusServlet();
        AdminNewTimetableServlet addingNewTimetableServlet = new AdminNewTimetableServlet();
        UserBuyTicketServlet buyingTicketServlet = new UserBuyTicketServlet();
        UserStationTimetableServlet showTimetableServlet = new UserStationTimetableServlet();
        AdminReportOfBusServlet showReportOfBusServlet = new AdminReportOfBusServlet();


        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        handler.addServlet(new ServletHolder(loginServlet), "/*");
        handler.addServlet(new ServletHolder(registerServlet), "/register.html");
        handler.addServlet(new ServletHolder(adminMenuServlet), "/admin_menu.html");
        handler.addServlet(new ServletHolder(userMenuServlet), "/user_menu.html");
        handler.addServlet(new ServletHolder(addingNewStationServlet), "/admin_add_station.html");
        handler.addServlet(new ServletHolder(addingNewBusServlet), "/admin_add_bus.html");
        handler.addServlet(new ServletHolder(findBusServlet), "/user_find_bus.html");
        handler.addServlet(new ServletHolder(addingNewTimetableServlet), "/admin_add_timetable.html");
        handler.addServlet(new ServletHolder(buyingTicketServlet), "/user_find_bus.html/buy");
        handler.addServlet(new ServletHolder(showTimetableServlet), "/user_station_timetable.html");
        handler.addServlet(new ServletHolder(showReportOfBusServlet), "/admin_report_bus.html");




        Server server = new Server(8080);
        server.setHandler(handler);

        server.start();
        server.join();
    }
}
