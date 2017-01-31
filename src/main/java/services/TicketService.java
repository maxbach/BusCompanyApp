package services;

import database.HibernateUtil;
import entities.Ticket;
import entities.Timetable;
import entities.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maxbacinskiy on 28.01.17.
 */
public class TicketService {
    public static void addTicket(Ticket ticket) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public static int howManyTicketsBought(Timetable timetable) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Ticket where timetable=:tt");
        query.setParameter("tt", timetable);
        List<Ticket> tickets = query.getResultList();
        session.close();
        return tickets.size();
    }

    public static List<Ticket> getTicketsForUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Ticket where user.id=:user");
        query.setParameter("user", user.getId());
        List<Ticket> goodTickets = query.getResultList();
        session.close();
        return goodTickets;
    }

    public static List<User> getPassengerOfTime(Timetable timetable) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Ticket where timetable=:timetable");
        query.setParameter("timetable", timetable);
        List<Ticket> goodTickets = query.getResultList();
        session.close();
        List<User> goodUsers = new ArrayList<>();
        for (Ticket ticket: goodTickets) {
            goodUsers.add(ticket.getUser());
        }
        return goodUsers;
    }

    public static boolean hasPassengersOfTimetableWithNameAndDateBirth(Timetable timetable, String firstName,
                                                                String secondName, Date dateBirth) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Ticket where timetable=:tt and user.firstName=:fn " +
                "and user.secondName=:sn and user.dateOfBirth=:db");
        query.setParameter("tt", timetable);
        query.setParameter("fn", firstName);
        query.setParameter("sn", secondName);
        query.setParameter("db", dateBirth);
        List<Ticket> tickets = query.getResultList();
        session.close();
        return tickets.size() > 0;
    }
}
