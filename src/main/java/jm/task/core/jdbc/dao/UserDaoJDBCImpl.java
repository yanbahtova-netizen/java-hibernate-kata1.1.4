package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            try (Connection connection = Util.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR (50), " +
                        "lastName VARCHAR (50), " +
                        "age TINYINT)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            try (Connection connection = Util.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS users");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastName, age) VALUES(?,?,?)";
        try {
            try (
                 Connection connection = Util.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql);
                 ) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            try (
                    Connection connection = Util.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            try (Connection connection = Util.getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql);
                 ) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            resultSet.getByte("age")
                            );
                    user.setId(resultSet.getLong("id"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try {
            try (
                    Connection connection = Util.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql);
            ) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
