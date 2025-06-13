package de.hitec.nhplus.datastorage;

import java.sql.Connection;


/**
 * Diese Klasse ist dafür da, um neue Benutzer zu erstellen und diese dann mit insertUser in die Datenbank einzufügen.
 * Sie wird einmalig ausgeführt, z.B. um einen Admin-Benutzer zu erstellen.
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
