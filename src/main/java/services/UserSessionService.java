package services;

import entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxbacinskiy on 31.01.17.
 */
public class UserSessionService {
    private static Map<String, User> mapOfNowUsers = new HashMap<>();

    public static void addSession(String id, User user) {
        mapOfNowUsers.put(id, user);
    }

    public static User getUserBySession(String id) {
        return mapOfNowUsers.get(id);
    }

    public static void deleteSession(String id) {
        mapOfNowUsers.remove(id);
    }
}
