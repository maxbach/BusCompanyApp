package services;

import database.HibernateUtil;
import entities.Bus;
import entities.Timetable;
import org.hibernate.Session;

import javax.persistence.Query;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxbacinskiy on 27.01.17.
 */
public class TimetableService {
    public static void addTimetable(Timetable timetable) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(timetable);
        session.getTransaction().commit();
        session.close();
    }

    public static boolean isCreated(Bus checkingBus, Time checkingTime) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Timetable where bus=:bus and time=:time");
        query.setParameter("bus", checkingBus);
        query.setParameter("time", checkingTime);
        List<Timetable> timetables = query.getResultList();
        session.close();
        return !timetables.isEmpty();
    }

    public static List<Timetable> queryTimetable(String stationFrom, String stationTo, Time timeFrom, Time timeTo) {
        Session session =  HibernateUtil.getSessionFactory().openSession();
        Query query;
        if (timeFrom.compareTo(timeTo) < 0) {
            query = session.createQuery("from Timetable where time >= :timeA and time <= :timeB " +
                    "and bus.stationA.name = :stationA and bus.stationB.name = :stationB");
        } else {
            query = session.createQuery("from Timetable where (time >= :timeA or time <= :timeB) " +
                    "and bus.stationA.name = :stationA and bus.stationB.name = :stationB");
        }
        query.setParameter("timeA", timeFrom);
        query.setParameter("timeB", timeTo);
        query.setParameter("stationA", stationFrom);
        query.setParameter("stationB", stationTo);
        List<Timetable> goodTimetables = query.getResultList();
        session.close();
        return goodTimetables;

    }

    public static Timetable getTimetableById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Timetable timetable = session.get(Timetable.class, id);
        session.close();
        return timetable;
    }

    public static List<Timetable> getTimetableOfBus(Bus bus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Timetable> timetables = session.createQuery("from Timetable where bus=:bus").setParameter("bus", bus)
                .getResultList();
        session.close();
        return timetables;
    }

    public static Timetable getTimetebleByTimeAndBus(Bus bus, Time time) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Timetable where time=:time and bus=:bus");
        query.setParameter("time", time);
        query.setParameter("bus", bus);
        Timetable tt = (Timetable) query.getSingleResult();
        session.close();
        return tt;
    }

    public static List<List<Timetable>> getTimetableOfBuses(List<Bus> buses) {
        List<List<Timetable>> listOfTimetables = new ArrayList<>();

        for (Bus bus : buses) {
            List<Timetable> timetableOfBus = TimetableService.getTimetableOfBus(bus);
            if (!timetableOfBus.isEmpty()) {
                listOfTimetables.add(timetableOfBus);
            }
        }
        return listOfTimetables;
    }

    public static List<List<Timetable>> getAllTimetables() {
        return getTimetableOfBuses(BusService.getAllBuses());
    }

}
