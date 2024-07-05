package jm.task.core.jdbc.util;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
            System.out.println(" Соединение установлено ");

        } catch (SQLException e) {
            System.out.println(" Ошибка установки соединения");
        }
        return connection;
    }

    public static void closeConnection () {
        try {
            connection.close();
            System.out.println(" Соединение закрыто ");
        } catch (Exception e) {
            System.out.println(" Соединение не закрыто ");
        }
    }
}