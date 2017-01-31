package services;

import database.HibernateUtil;
import entities.User;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maxbacinskiy on 23.01.17.
 */
public class UserService {

    private static void addNewUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public UserService() {
    }

    public static void addFirstAdmins() {
        User admin1 = getUser("admin");
        if (admin1 == null) {
            addAdmin("admin", "admin"); // adding "admin" user
            addAdmin("test", "test");
        }
    }

    public static void addSimpleUser(String login, String password, String firstName, String secondName, Date date) {
        addNewUser(new User(login, password, false, firstName, secondName, date));
    }

    private static void addAdmin(String user, String password) {
        addNewUser(new User(user, password, true, user, user, Calendar.getInstance().getTime()));
    }

    public static User getUser(String user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query =  session.createQuery("from User where userName = :userName");
        query.setParameter("userName", user);
        User userAccount;
        if (query.getResultList().size() == 0) {
            userAccount = null;
        } else {
            userAccount = (User) query.getSingleResult();
        }
        session.close();
        return userAccount;
    }



}
