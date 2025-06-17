package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.FinishedTreatmentDao;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllTreatmentController {

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

    private final ObservableList<Treatment> treatments = FXCollections.observableArrayList();
    private TreatmentDao dao;
    private final ObservableList<String> patientSelection = FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;

    /**
     * Initializes the view components and prepares the treatment data for display.
     * Sets the items for the patient selection ComboBox, initializes the column
     * mappings for the TableView, loads all treatments from the data source,
     * and sets up a listener to enable or disable the delete button based on the
     * selected treatment. Also populates additional ComboBox data required for the view.
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
        this.tableView.setItems(this.treatments);

        // Disabling the button to delete treatments as long, as no treatment was selected.
        this.buttonDelete.setDisable(true);
        this.tableView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldTreatment, newTreatment) ->
                        AllTreatmentController.this.buttonDelete.setDisable(newTreatment == null));

        this.createComboBoxData();
    }

    /**
     * Loads all treatments from the database and displays them in the table view.
     *
     * This method:
     *
     *  - Clears existing items from the table view and the patient selection combo box
     *  - Clears the internal {@code treatments} list
     *  - Retrieves all treatment records using the {@link TreatmentDao}
     *  - Adds the retrieved treatments to the {@code treatments} list, which is bound to the table view
     *
     *
     * Note: This method should be called after initializing the combo box to ensure the correct data is loaded and displayed.
     */
    public void readAllAndShowInTableView() {
        this.tableView.getItems().clear();
        comboBoxPatientSelection.getItems().clear();
        this.treatments.clear();
        comboBoxPatientSelection.getSelectionModel().select(0);
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            this.treatments.addAll(dao.readAll());
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
        this.treatments.clear();
        this.dao = DaoFactory.getDaoFactory().createTreatmentDao();

        if (selectedPatient.equals("alle")) {
            try {
                this.treatments.addAll(this.dao.readAll());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        Patient patient = searchInList(selectedPatient);
        if (patient !=null) {
            try {
                this.treatments.addAll(this.dao.readTreatmentsByPid(patient.getPid()));
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
        Treatment t = this.treatments.remove(index);
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.deleteById(t.getTid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handles the creation of a new treatment for the selected patient.
     *
     * Retrieves the selected patient from the combo box and opens a new treatment window
     * for that patient. If no patient is selected (resulting in a {@code NullPointerException}),
     * an informational alert dialog is shown to prompt the user to select a patient first.
     *
     */
    @FXML
    public void handleNewTreatment() {
        try{
            String selectedPatient = this.comboBoxPatientSelection.getSelectionModel().getSelectedItem();
            Patient patient = searchInList(selectedPatient);
            newTreatmentWindow(patient);
        } catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Patienten aus!");
            alert.showAndWait();
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
                Treatment treatment = this.treatments.get(index);
                treatmentWindow(treatment);
            }
        });
    }

    /**
     * Opens a modal window for creating a new treatment for the specified patient.
     *
     * Loads the {@code NewTreatmentView.fxml} layout, initializes its controller with the
     * given {@link Patient} object, and displays the window in a blocking (modal) fashion.
     * The main application window remains in the background while the treatment window is open.
     *
     * @param patient the {@code Patient} for whom the new treatment will be created
     *
     * Note: If an {@code IOException} occurs during FXML loading, the stack trace is printed, but no error dialog is shown to the user.
     */
    public void newTreatmentWindow(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Opens a modal window to view or edit the details of a specific treatment.
     *
     * Loads the {@code TreatmentView.fxml} file, initializes its controller with the
     * provided {@link Treatment} object, and displays the window in a modal (blocking) state.
     * The main application window remains open but inactive until this dialog is closed.
     *
     * @param treatment the {@code Treatment} instance to be displayed or edited
     *
     * Note: If an {@code IOException} occurs during FXML loading,
     * the error is printed to the console, but no user-facing alert is shown.
     */
    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);

            // the primary stage should stay in the background
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();
            controller.initializeController(this, stage, treatment);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
