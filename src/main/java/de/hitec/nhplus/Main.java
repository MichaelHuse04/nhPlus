package de.hitec.nhplus;


import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.utils.UpdateTreatmentToFinishedTreatment;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginWindow(); // Login-Fenster anzeigen
    }

    /**
     * showLoginWindow ist dafür da, damit beim Starten der Applikation die Login-Maske angezeigt wird.
     */

    public void showLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/LoginUI.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("Login - NHPlus");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();

            this.primaryStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection();
                Platform.exit();
                System.exit(0);
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UpdateTreatmentToFinishedTreatment.createCheckTimer();
        launch(args);
    }
}