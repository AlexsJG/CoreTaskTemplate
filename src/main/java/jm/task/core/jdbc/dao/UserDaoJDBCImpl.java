package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static jm.task.core.jdbc.Logging.Logging.logger;


public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_USER = "INSERT INTO one.tableIdea (name, lastname, age) VALUES (?,?,?)";
    private static final String AVAILABILITAY_USER = "SELECT ID FROM one.tableIdea WHERE id = ?";
    private static final String REMOVE_USER = "DELETE FROM one.tableIdea WHERE id = ?";
    Util util = new Util();
    Connection getConnect = util.getConnection();


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (Statement statement = getConnect.createStatement()) {
            statement.executeUpdate("CREATE table IF NOT EXISTS one.tableIdea (id BIGINT not null auto_increment, " +
                    "name varchar(45) not null, lastName varchar(45) not null, age int not null, PRIMARY KEY (id))");

            logger.log(Level.INFO, "Таблица создана");

        } catch (SQLException e) {

            logger.log(Level.SEVERE, " Ошибка создания таблицы", e);
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            DatabaseMetaData md = getConnect.getMetaData();
            ResultSet rs = md.getTables(null, null, "one.tableIdea", null);
            if (rs.next()) {
                statement = getConnect.createStatement();
                statement.executeUpdate("DROP TABLE one.tableIdea");
                logger.log(Level.INFO, "Таблица успешно удалена");
            } else {
                logger.log(Level.INFO, "Таблицу one.tableIdea не удалить, так как ее не существует");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Ошибка удаления таблицы", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, " Не удалось закрыть preparedstatement", e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedstatement = getConnect.prepareStatement(INSERT_USER)) {
            preparedstatement.setString(1, name);
            preparedstatement.setString(2, lastName);
            preparedstatement.setInt(3, age);
            preparedstatement.executeUpdate();
            System.out.println("User с именем " + lastName + " " + name + " добавлен в таблицу");
            logger.log(Level.INFO, "User добавлен в таблицу");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Не удалось добавить User'a  с именем " + lastName + " " + name + " в таблицу ", e);
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedstatement = null;
        try {
            preparedstatement = getConnect.prepareStatement(AVAILABILITAY_USER);
            preparedstatement.setLong(1, id);
            ResultSet resultSet = preparedstatement.executeQuery();
            if (resultSet.next()) {
                preparedstatement = getConnect.prepareStatement(REMOVE_USER);
                preparedstatement.setLong(1, id);
                preparedstatement.executeUpdate();
                logger.log(Level.INFO, "User с номером id " + id + " удален из таблицы");
            } else {
                logger.log(Level.INFO, "В таблице нет записи под номером " + id);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Ошибка удаления User'a с id " + id + " из таблицы ", e);
        } finally {
            try {
                if (preparedstatement != null) {
                    preparedstatement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, " Не удалось закрыть preparedstatement", e);
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        try (Statement statement = getConnect.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM one.tableIdea");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = (byte) rs.getInt("age");
                users.add(new User(id, name, lastName, age));
            }
            logger.log(Level.INFO, "Список Users получен");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Не удалось получить список Users из базы данных", e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = getConnect.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE one.tableIdea");
            logger.log(Level.INFO, "Таблица очищена ");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, " Ошибка очистки таблицы", e);
        }
    }
}