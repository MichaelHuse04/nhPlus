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
     * Temporarily disables the login form after too many failed attempts.
     *
     * Displays a warning message and disables the username and password input fields
     * for 30 seconds to prevent further login attempts.
     * After the timeout, the fields are re-enabled and the error message is cleared. This logic runs on a background
     * thread, and UI updates are performed on the JavaFX Application Thread.
     *
     * Note: Resets the {@code loginAttempts} counter after the timeout.
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
     * Handles the login process when the user submits their credentials.
     *
     * Retrieves the entered username and password, then checks their validity using the {@link UserDao}.
     * If the login is successful, the main application window is loaded and shown.
     * If the login fails, an error message is displayed and the failed attempt counter is incremented.
     * After three failed attempts, the login form is temporarily disabled via the {@code timeout()} method.
     *
     *
     * Note: This method is triggered by a JavaFX event (e.g., button press)
     * and expects {@code usernameField}, {@code passwordField}, and {@code errorLabel}
     * to be properly initialized in the corresponding FXML file.
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

