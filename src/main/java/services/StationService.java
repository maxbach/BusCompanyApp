package services;

import database.HibernateUtil;
import entities.Station;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxbacinskiy on 25.01.17.
 */
public class StationService {

    private static void addNewStation(Station station) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(station);
        session.getTransaction().commit();
        session.close();
    }

    public static Station getStation(String station) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Station where name=:name");
        query.setParameter("name", station);
        List<Station> allGoodStations = query.getResultList();
        session.close();
        if (allGoodStations.isEmpty()) {
            return null;
        } else {
            return allGoodStations.get(0);
        }
    }

    public static void addStation(String name) {
        addNewStation(new Station(name));
    }

    //get all stations

    private static List<Station> getAllStations() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Station> allStations = session.createQuery("from Station").getResultList();
        session.close();
        return allStations;
    }

    public static List<String> getAllStationNames() {
        List<String> stations = new ArrayList<>();
        for (Station station : getAllStations()) {
            stations.add(station.getName());
        }
        return stations;

    }

}
