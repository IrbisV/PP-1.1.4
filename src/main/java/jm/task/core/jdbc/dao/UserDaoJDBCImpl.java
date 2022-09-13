package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    private final Connection connection = Util.getConnection();
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE if not exists users (" +
                            "id INT PRIMARY KEY auto_increment, " +
                            "name varchar(100)," +
                            "lastname varchar(100)," +
                            "age smallint(3));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("Drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(name, lastname, age) values (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from users where id = (?)")) {
            preparedStatement.setLong(1, id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * from users");

            while (resultSet.next()) {
                User user1 = new User();
                user1.setId(resultSet.getLong("id"));
                user1.setName(resultSet.getString("name"));
                user1.setLastName(resultSet.getString("lastname"));
                user1.setAge(resultSet.getByte("age"));
                user.add(user1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("delete from users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
