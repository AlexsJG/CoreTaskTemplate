package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_USER = "INSERT INTO one.tableIdea (name, lastname, age) VALUES (?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM one.tableIdea WHERE id = ?";
    Util util = new Util();
    Connection getConnect = util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try (Statement statement = getConnect.createStatement()) {
            statement.executeUpdate("CREATE table IF NOT EXISTS one.tableIdea (id int not null auto_increment, " +
                    "name varchar(45) not null, lastName varchar(45) not null, age int not null, PRIMARY KEY (id))");
            System.out.println(" Таблица создана ");
        } catch (SQLException e) {
            System.out.println(" Ошибка создания таблицы ");
        }
    }

    public void dropUsersTable() {

        try (Statement statement = getConnect.createStatement()) {
            statement.executeUpdate("drop table IF EXISTS one.tableIdea");
            System.out.println(" Таблица удалена ");
        } catch (SQLException e) {
            System.out.println(" Ошибка удаления таблицы ");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedstatement = getConnect.prepareStatement(INSERT_USER)) {
            preparedstatement.setString(1, name);
            preparedstatement.setString(2, lastName);
            preparedstatement.setInt(3, age);
            preparedstatement.executeUpdate();
            System.out.println(" User с именем " + lastName + " " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println(" Не удалось добавить User'a  с именем " + lastName + " " + name + " в таблицу ");
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedstatement = getConnect.prepareStatement(REMOVE_USER)) {
            preparedstatement.setLong(1, id);
            preparedstatement.execute();
            System.out.println(" User с номером id " + id + " удален из базы данных");
        } catch (SQLException e) {
            System.out.println(" Не удалось удалить User'a с id " + id + " из таблицы ");
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
        } catch (SQLException e) {
            System.out.println(" Не удалось получить список Users из базы данных ");
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = getConnect.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE one.tableIdea");
            System.out.println(" Таблица очищена ");
        } catch (SQLException e) {
            System.out.println(" Ошибка очистки таблицы ");
        }
    }
}