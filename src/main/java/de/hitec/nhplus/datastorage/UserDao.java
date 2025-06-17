package de.hitec.nhplus.datastorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {

    private final Connection connection;

    /**
     * Constructor that takes over an existing database connection.
     * @param connection The connection to the SQLite database.
     */


    public UserDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * Checks the username and password.
     * The password is compared with the stored hash.
     *
     * @param username The entered username.
     * @param password The entered password (in plain text).
     * @return true if the login credentials are correct, otherwise false.
     */


    public boolean checkLogin(String username, String password) {
        String sql = "SELECT password FROM user WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.checkpw(password, storedHash); // Hier wird das Passwort verglichen
            }
        } catch (SQLException e) {
            e.printStackTrace(); //Fehlerausgabe eingebaut
        }
        return false;
    }


    /**
     * Inserts a new user with a hashed password into the database (important for data protection).
     *
     * @param username The username.
     * @param plainPassword The password in plain text.
     * @param role The role of the user (e.g., "ADMIN").
     * @return true if the user was successfully inserted, otherwise false.
     */



    public boolean insertUser(String username, String plainPassword, String role) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt()); //Passwort verschlüsseln mit BCrypt

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


