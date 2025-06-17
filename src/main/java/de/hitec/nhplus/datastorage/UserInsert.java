package de.hitec.nhplus.datastorage;

import java.sql.Connection;


/**
 * This class is used to create new users and insert them into the database using insertUser.
 * It is executed once, for example to create an admin user.
 */


public class UserInsert {
    public static void main(String[] args) {
        //Hier wird die Datenbank Verbindung aufgebaut
        Connection connection = ConnectionBuilder.getConnection();
        UserDao userDao = new UserDao(connection);
        // Einfügen eines Benutzers
        boolean success = userDao.insertUser("admin", "meinSicheresPasswort", "ADMIN");

        if (success) {
            System.out.println("Benutzer erfolgreich hinzugefügt!");
        } else {
            System.out.println("Fehler beim Hinzufügen des Benutzers.");
        }
        // Verbindung schließen (Wichtig um keine unnötigen Verbindungen zu der Datenbank zu haben)
        ConnectionBuilder.closeConnection();
    }
}
