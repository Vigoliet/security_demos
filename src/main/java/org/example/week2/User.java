package org.example.week2;

public class User {
    private int id;
    private String username;
    private String hashedPassword;

    public User() {
    }

    public User(String username, String hashedPassword) { // New user before db
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public User(int id, String username, String hashedPassword) { // Existing user from db
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHash(){
        var parts = hashedPassword.split(":");
        return parts[0];
    }

    public String getSalt(){
        var parts = hashedPassword.split(":");
        return parts[1];
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                '}';
    }
}
