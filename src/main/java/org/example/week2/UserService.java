package org.example.week2;

import java.sql.*;

import static org.example.week2.PasswordDemos.hashMd5;

public class UserService {

    Connection conn = null;
    String connectionUrl = "jdbc:mysql://localhost:3306/aos_demo";

    public UserService() throws SQLException {
        this.conn = DriverManager.getConnection(connectionUrl, "root", "root");
    }

    // Save user (username, hashedPassword)
    public void insertUser(User user) throws SQLException {
        String query = "INSERT INTO users(username, hashed_password) VALUES( ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getHashedPassword());
        preparedStatement.executeUpdate();
    }

    // Get user (username) -> User
    // You can add the method to get user here
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            username = resultSet.getString("username");
            String hashedPassword = resultSet.getString("hashed_password");
            return new User(username, hashedPassword);
        } else {
            return null;
        }
    }

    public String getSalt(String username) throws SQLException {
        String query = "SELECT hashed_password FROM users WHERE username = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String hashedPasswordAndSalt = resultSet.getString("hashed_password");
            String[] parts = hashedPasswordAndSalt.split(":");
            return parts[1]; // The salt is the second part of the hashed_password field
        } else {
            return null;
        }
    }

    public void loginUser(String username, String hashedPasswordAndSalt) throws SQLException {
        String query = "SELECT hashed_password FROM users WHERE username = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String storedHashedPasswordAndSalt = resultSet.getString("hashed_password");

            if (storedHashedPasswordAndSalt.equals(hashedPasswordAndSalt)) {
                System.out.println("Login successful");
            } else {
                System.out.println("Login failed");
            }
        } else {
            System.out.println("User not found");
        }
    }



}