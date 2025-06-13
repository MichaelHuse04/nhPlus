package de.hitec.nhplus.datastorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {

    private final Connection connection;

    /**
      * Konstruktor, der eine bestehende Datenbankverbindung übernimmt.
      * @param connection Die Verbindung zur SQLite-Datenbank.
     */


    public UserDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * Prüfung des Benutzernamen und Passwort.
     * Das Passwort wird mit dem gespeicherten Hash verglichen.
     *
     * @param username Der eingegebene Benutzername.
     * @param password Das eingegebene Passwort (im Klartext).
     * @return true, wenn die Anmeldedaten korrekt sind, sonst false.
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
     * Fügt einen neuen Benutzer mit gehashtem Passwort in die Datenbank ein (Für den Datenschutz wichtig).
     *
     * @param username Der Benutzername.
     * @param plainPassword Das Passwort im Klartext.
     * @param role Die Rolle des Benutzers (z.B. "ADMIN").
     * @return true, wenn der Benutzer erfolgreich eingefügt wurde, sonst false.
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


