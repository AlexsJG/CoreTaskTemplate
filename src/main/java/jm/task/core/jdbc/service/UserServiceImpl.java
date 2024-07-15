package jm.task.core.jdbc.service;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoJDBCImpl();
    public void createUsersTable() {
        dao.createUsersTable();
    }

    public void dropUsersTable() {
        dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        dao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> result = dao.getAllUsers();
        if ( result.size() != 0) {
            System.out.println("Получен следующий список Users из базы данных:");
            for (User element : result) {
                System.out.println(element);
            }
        }
        return result;
    }

    public void cleanUsersTable() {
        dao.cleanUsersTable();
    }
}
