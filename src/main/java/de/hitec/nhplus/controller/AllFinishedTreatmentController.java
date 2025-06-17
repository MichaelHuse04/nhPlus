package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.FinishedTreatmentDao;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The <code>AllFinishedTreatmentController</code> contains the entire logic of the finished treatment view. It determines which data is displayed and how to react to events.
 */
public class AllFinishedTreatmentController {

    @FXML
    private TableView<Treatment> tableView;

    @FXML
    private TableColumn<Treatment, Integer> columnId;

    @FXML
    private TableColumn<Treatment, Integer> columnPid;

    @FXML
    private TableColumn<Treatment, Integer> columnCid;

    @FXML
    private TableColumn<Treatment, String> columnDate;

    @FXML
    private TableColumn<Treatment, String> columnBegin;

    @FXML
    private TableColumn<Treatment, String> columnEnd;

    @FXML
    private TableColumn<Treatment, String> columnDescription;

    @FXML
    private ComboBox<String> comboBoxPatientSelection;

    @FXML
    private Button buttonDelete;

    private final ObservableList<Treatment> finishedTreatments = FXCollections.observableArrayList();
    private FinishedTreatmentDao dao;
    private final ObservableList<String> patientSelection = FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;

    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     */
    public void initialize() {
        readAllAndShowInTableView();
        comboBoxPatientSelection.setItems(patientSelection);
        comboBoxPatientSelection.getSelectionModel().select(0);

        this.columnId.setCellValueFactory(new PropertyValueFactory<>("tid"));
        this.columnPid.setCellValueFactory(new PropertyValueFactory<>("pid"));
        this.columnCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        this.columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.columnBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.tableView.setItems(this.finishedTreatments);

        // Disabling the button to delete finishedTreatments as long, as no treatment was selected.
        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTreatment, newTreatment) ->
                        AllFinishedTreatmentController.this.buttonDelete.setDisable(newTreatment == null));

        this.createComboBoxData();
    }

    /**
     * Reloads all finished treatments to the table by clearing the list of all finished treatments and filling it again by all persisted
     * finished treatments, delivered by {@link FinishedTreatmentDao}.
     */
    public void readAllAndShowInTableView() {
        this.finishedTreatments.clear();
        comboBoxPatientSelection.getSelectionModel().select(0);
        this.dao = DaoFactory.getDaoFactory().createFinishedTreatmentDao();
        try {
            this.finishedTreatments.addAll(dao.readAll());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Populates the patient selection combo box with data from the database.
     *
     * Retrieves all patients using the {@link PatientDao} and adds their surnames
     * to the {@code patientSelection} list. The entry {@code "alle"} (meaning "all")
     * is added as the first item to allow selection of all patients.
     *
     * Assumes: {@code patientSelection} is a list backing a UI ComboBox.
     */
    private void createComboBoxData() {
        PatientDao dao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            this.patientSelection.add("alle");
            for (Patient patient: patientList) {
                this.patientSelection.add(patient.getSurname());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the selection event of the patient combo box.
     *
     * Based on the selected patient name from the combo box, this method either:
     *
     *   - Loads all finished treatments if {@code "alle"} ("all") is selected, or
     *   - Loads only the finished treatments for the selected patient.
     *
     * The method clears the current list of finished treatments and repopulates it
     * based on the selection. Patient lookup is done via {@code searchInList()} using
     * the selected surname.
     *
     * Assumes: the patient list is already populated and that surnames are unique or sufficiently distinguishable.
     */
    @FXML
    public void handleComboBox() {
        String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
        this.finishedTreatments.clear();
        this.dao = DaoFactory.getDaoFactory().createFinishedTreatmentDao();

        if (selectedPatient.equals("alle")) {
            try {
                this.finishedTreatments.addAll(this.dao.readAll());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        Patient patient = searchInList(selectedPatient);
        if (patient !=null) {
            try {
                this.finishedTreatments.addAll(this.dao.readFinishedTreatmentsByPid(patient.getPid()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Searches the patient list for a patient with the given surname.
     *
     * @param surname the surname to search for
     * @return the first {@code Patient} object with a matching surname, or {@code null} if no match is found
     *
     * Note: This method performs a linear search and assumes that
     * surnames are either unique or that returning the first match is sufficient.
     */
    private Patient searchInList(String surname) {
        for (Patient patient : this.patientList) {
            if (patient.getSurname().equals(surname)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * This method handles events fired by the button to delete finished treatments. It calls {@link FinishedTreatmentDao} to delete the
     * finished treatments from the database and removes the object from the list, which is the data source of the
     * <code>TableView</code>.
     */
    @FXML
    public void handleDelete() {
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        Treatment t = this.finishedTreatments.remove(index);
        FinishedTreatmentDao dao = DaoFactory.getDaoFactory().createFinishedTreatmentDao();
        try {
            dao.deleteById(t.getTid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles mouse click events on the treatment table view.
     *
     * Registers a listener that detects double-clicks on table rows. When a row is
     * double-clicked and a treatment is selected, the corresponding {@code Treatment}
     * object is retrieved from the {@code finishedTreatments} list based on the selected index,
     * and a detailed treatment window is opened via {@code treatmentWindow()}.
     *
     *
     * Assumes: the {@code finishedTreatments} list is in sync with the table view's displayed items.
     */
    @FXML
    public void handleMouseClick() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment finishedTreatments = this.finishedTreatments.get(index);
                treatmentWindow(finishedTreatments);
            }
        });
    }

    /**
     * Opens a new window displaying the details of a selected finished treatment.
     *
     * Loads the {@code FinishedTreatmentView.fxml} layout, initializes its controller
     * with the provided {@code Treatment} object, and displays the view in a modal window
     * (blocking the current window until closed).
     *
     * @param treatment the {@code Treatment} object to display in the new window
     *
     * Note: The main application window remains in the background,
     * and the new window is non-resizable.
     */
    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/FinishedTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();
            FinishedTreatmentController controller = loader.getController();
            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
