package database;

import org.hibernate.dialect.MySQLDialect;

/**
 * Created by maxbacinskiy on 29.01.17.
 */
public class SelfMySQLDialect extends MySQLDialect {

    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
