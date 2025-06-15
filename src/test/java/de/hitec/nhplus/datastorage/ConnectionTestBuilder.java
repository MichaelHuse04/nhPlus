package de.hitec.nhplus.datastorage;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTestBuilder {

    private static final String DB_NAME = "nursingHomeTest.db";
    private static final String URL = "jdbc:sqlite:db/" + DB_NAME;

    private static Connection connection;

    synchronized public static Connection getConnection() {
        try {
            if (ConnectionTestBuilder.connection == null) {
                SQLiteConfig configuration = new SQLiteConfig();
                configuration.enforceForeignKeys(true);
                ConnectionTestBuilder.connection = DriverManager.getConnection(URL, configuration.toProperties());
            }
        } catch (SQLException exception) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            exception.printStackTrace();
        }
        return ConnectionTestBuilder.connection;
    }

    synchronized public static void closeConnection() {
        try {
            if (ConnectionTestBuilder.connection != null) {
                ConnectionTestBuilder.connection.close();
                ConnectionTestBuilder.connection = null;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
