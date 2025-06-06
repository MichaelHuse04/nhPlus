package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.datastorage.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class LoginUIController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Verbindung zur Datenbank aufbauen
            Connection connection = ConnectionBuilder.getConnection();
            UserDao userDao = new UserDao(connection);

            // Login prüfen
            boolean loginSuccessful = userDao.checkLogin(username, password);

            if (loginSuccessful) {
                // Hauptfenster laden
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
                BorderPane mainView = loader.load();

                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(mainView));
                stage.show();
            } else {
                errorLabel.setText("Benutzername oder Passwort ist falsch.");
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
