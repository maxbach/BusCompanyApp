package servlets.main;

/**
 * Created by maxbacinskiy on 31.01.17.
 */
public class ServletHelper {
    public static boolean checkVariables(String... variables) {

        for(String var : variables) {
            if (var == null || var.isEmpty()) return false;
        }
        return true;
    }
}
