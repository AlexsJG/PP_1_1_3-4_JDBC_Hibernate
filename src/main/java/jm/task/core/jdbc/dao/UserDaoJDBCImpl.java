package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_USER = "INSERT INTO Task_1_1_3_4.Users (name, lastname, age) VALUES (?,?,?)";
    private static final String AVAILABILITAY_USER = "SELECT ID FROM Task_1_1_3_4.Users WHERE id = ?";
    private static final String REMOVE_USER = "DELETE FROM Task_1_1_3_4.Users WHERE id = ?";
    public UserDaoJDBCImpl() {

    }
    Connection connect;

    {
        try {
            connect = Util.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try (Statement statement = connect.createStatement()) {
                statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS Task_1_1_3_4");
                statement.executeUpdate("CREATE table IF NOT EXISTS Task_1_1_3_4.Users (id BIGINT not null auto_increment, " +
                        "name varchar(45) not null, lastName varchar(45) not null, age int not null, PRIMARY KEY (id))");
            }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connect.createStatement()) {
                statement.executeUpdate("DROP table IF EXISTS Task_1_1_3_4.Users");
                statement.executeUpdate("DROP SCHEMA IF EXISTS Task_1_1_3_4");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedstatement = connect.prepareStatement(INSERT_USER)) {
            preparedstatement.setString(1, name);
            preparedstatement.setString(2, lastName);
            preparedstatement.setInt(3, age);
            preparedstatement.executeUpdate();
            System.out.println("User с именем " + lastName + " " + name + " добавлен в таблицу");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedstatement = null;
        try {
            preparedstatement = connect.prepareStatement(AVAILABILITAY_USER);
            preparedstatement.setLong(1, id);
            ResultSet resultSet = preparedstatement.executeQuery();
            if (resultSet.next()) {
                preparedstatement = connect.prepareStatement(REMOVE_USER);
                preparedstatement.setLong(1, id);
                preparedstatement.executeUpdate();
                System.out.println("User с номером id " + id + " удален из таблицы");
            } else {
                System.out.println("В таблице нет записи под номером " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedstatement != null) {
                        preparedstatement.close();
                }
            } catch (SQLException e) {
                    throw new RuntimeException(e);
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connect.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Task_1_1_3_4.Users");
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = (byte) rs.getInt("age");
                users.add(new User(name, lastName, age));
            }
            System.out.println("Список Users получен");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Task_1_1_3_4.Users");
            System.out.println("Таблица очищена ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
