
package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    /**
     * Handles the event for displaying the view that lists all patients.
     *
     * Loads the {@code AllPatientView.fxml} file and sets its content in the center
     * of the application's main layout pane ({@code mainBorderPane}). If the FXML
     * file fails to load due to an {@code IOException}, an error alert is shown to
     * the user, and the application will exit upon confirmation.
     *
     * @param event the {@code ActionEvent} that triggered this method (e.g., menu item or button)
     *
     */
    @FXML
    private void handleShowAllPatient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Interner Systemfehler: AllPatientView.fxml konnte nicht geladen werden.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    /**
     * Handles the action event for displaying the view that lists all treatments.
     *
     * Loads the {@code AllTreatmentView.fxml} file and displays its content in the center
     * of the main application layout ({@code mainBorderPane}). If loading the FXML file fails
     * due to an {@code IOException}, an error alert is shown. Upon confirmation, the application exits.
     *
     * @param event the {@code ActionEvent} that triggered this method (e.g., menu item or button click)
     *
     */
    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Interner Systemfehler: AllTreatmentView.fxml konnte nicht geladen werden.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    /**
     * Handles the action event to display the view that lists all finished treatments.
     *
     * Loads the {@code AllFinishedTreatmentView.fxml} file and sets its content in the center
     * of the main layout pane ({@code mainBorderPane}). If the FXML file cannot be loaded
     * due to an {@code IOException}, an error alert is shown to the user. If the user confirms,
     * the application will exit.
     *
     * @param event the {@code ActionEvent} that triggered this method (e.g., button or menu selection)
     *
     */
    @FXML
    private void handleShowAllFinishedTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllFinishedTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Interner Systemfehler: AllFinishedTreatmentView.fxml konnte nicht geladen werden.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    /**
     * Handles the action event to display the view that lists all caregivers.
     *
     * Loads the {@code AllCaregiverView.fxml} layout and sets it in the center
     * of the main application pane ({@code mainBorderPane}). If loading the FXML fails
     * due to an {@code IOException}, an error dialog is shown. Upon user confirmation,
     * the application exits.
     *
     * @param event the {@code ActionEvent} triggered by a UI element (e.g., button or menu item)
     *
     */
    @FXML
    private void handleShowAllCaregiver(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Interner Systemfehler: AllCaregiverView.fxml konnte nicht geladen werden.", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }
}
