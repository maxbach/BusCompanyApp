package services;

import database.HibernateUtil;
import entities.Bus;
import entities.Station;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by maxbacinskiy on 25.01.17.
 */
public class BusService {
    private static void addNewBus(Bus bus) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(bus);
        session.getTransaction().commit();
        session.close();
    }

    // specific query

    public static Bus getBus(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Bus bus = session.get(Bus.class, id);
        session.close();
        return bus;
    }

    public static void addBus(int number, int seats, String stationFrom, String stationTo) {
        Station stationA = StationService.getStation(stationFrom);
        Station stationB = StationService.getStation(stationTo);
        addNewBus(new Bus(number, seats, stationA, stationB));

    }

    public static List<Bus> getAllBuses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Bus");
        List<Bus> allBuses = query.getResultList();
        session.close();
        return allBuses;

    }

    public static List<Bus> getBusesByBeginStation(String stationFrom) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Bus where stationA.name=:station");
        query.setParameter("station", stationFrom);
        List<Bus> goodBuses = query.getResultList();
        session.close();
        return goodBuses;
    }
}
