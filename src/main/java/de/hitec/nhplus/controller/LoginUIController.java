package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.datastorage.UserDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


/**
 * Controller für das Login-Fenster.
 * Verarbeitet Benutzereingaben und steuert dann den Login Vorgang.
 */


public class LoginUIController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Zähler für Fehlversuche, wichtig um Hackerangriffe wie einen Brute-Force-Angriff zu verhindern. Timeout wird damit gesteuert.
    private int loginAttempts = 0;

    /**
     * Sperrt das Login-Feld für 30 Seuknden nach 3 Fehlversuchen.
     */
    private void timeout() {
        errorLabel.setText("Zu viele Fehlversuche. Bitte warte 30 Sekunden...");

        // Felder deaktivieren
        usernameField.setDisable(true);
        passwordField.setDisable(true);

        // Wartezeit in neuem Thread
        new Thread(() -> {
            try {
                Thread.sleep(30000); // 30 Sekunden warten
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // UI wieder aktivieren im JavaFX-Thread
            Platform.runLater(() -> {
                usernameField.setDisable(false);
                passwordField.setDisable(false);
                errorLabel.setText("");
                loginAttempts = 0; // Zähler zurücksetzen
            });
        }).start();
    }


    /**
     * Wird beim Klick auf den Login-Button aufgerufen.
     * Prüft die Anmeldedaten und öffnet das Hauptfenster, wenn die Login-Daten stimmen.
     */


    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Connection connection = ConnectionBuilder.getConnection();
            UserDao userDao = new UserDao(connection);

            boolean loginSuccessful = userDao.checkLogin(username, password);

            if (loginSuccessful) {
                // Hauptfenster laden
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
                BorderPane mainView = loader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(mainView));
                stage.show();
            } else {
                loginAttempts++;
                errorLabel.setText("Benutzername oder Passwort ist falsch.");

                if (loginAttempts >= 3) {
                    timeout(); //Sobald zu viele Versuche gebraucht werden, wird die Sperre aktiviert.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Fehler beim Laden der Hauptansicht.");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Fehler beim Login.");
        }
    }
}

