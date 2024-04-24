package org.example.week2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserServiceV2 {
    Connection conn = null;
    String connectionUrl = "jdbc:mysql://localhost:3306/aos_demo";

    public UserServiceV2() throws SQLException {
        conn = DriverManager.getConnection(connectionUrl, "root", "dockerpass");
    }

    // Save user (User)
    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO users(username, hashed_password) VALUES(?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getHashedPassword());
        preparedStatement.executeUpdate();
    }

    public User getUserByUsername(String username) throws SQLException {
        var query = "SELECT * FROM users WHERE username = ?;";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);

        var resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            var user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setHashedPassword(resultSet.getString("hashed_password"));

            return user;
        }

        return null;
    }

}
