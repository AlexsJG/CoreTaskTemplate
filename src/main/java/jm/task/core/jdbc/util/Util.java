package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import static jm.task.core.jdbc.Logging.Logging.logger;

public class Util {
    private final static String DB_URL = "jdbc:mysql://localhost:3306";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "s1ql2";

    static Connection connection = null;

    public Connection getConnection() {


        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.log(Level.INFO, "Соединение установлено");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Ошибка установки соединения", e);
        }
        return connection;
    }


    public static void closeConnection() {
        try {
            connection.close();
            logger.log(Level.INFO, "Соединение закрыто");
        } catch (Exception e) {
            logger.log(Level.SEVERE, " Ошибка закрытия соединения", e);
        }
    }
}