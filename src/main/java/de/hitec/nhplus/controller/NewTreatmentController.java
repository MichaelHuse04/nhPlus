package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Caregiver;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {

    @FXML
    private Label labelFirstName;

    @FXML
    private Label labelSurname;

    @FXML
    private TextField textFieldBegin;

    @FXML
    private TextField textFieldEnd;

    @FXML
    private TextField textFieldDescription;

    @FXML
    private TextArea textAreaRemarks;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<Caregiver> comboBoxCaregiverSelection;

    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;
    private final ObservableList<Caregiver> caregiverSelection = FXCollections.observableArrayList();
    private ArrayList<Caregiver> caregiverList;
    protected final String defaultValueForCaregiverSelection = "-";


    /**
     * Initializes the controller for creating a new treatment.
     *
     * Sets up UI components and listeners, assigns the current {@link Patient}, parent controller,
     * and stage. It configures the date picker with a custom string converter, disables the
     * "Add" button by default, and attaches listeners to input fields to enable the button only
     * when valid data is entered. It also populates the caregiver combo box and displays
     * basic patient information.
     *
     *
     * @param controller the parent {@link AllTreatmentController} that manages the treatment list
     * @param stage      the {@link Stage} used for the new treatment window
     * @param patient    the {@link Patient} for whom the treatment is being created
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller = controller;
        this.patient = patient;
        this.stage = stage;

        comboBoxCaregiverSelection.setItems(caregiverSelection);
        comboBoxCaregiverSelection.getSelectionModel().select(0);

        this.buttonAdd.setDisable(true);
        ChangeListener<String> inputNewPatientListener = (observableValue, oldText, newText) ->
                NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid());
        this.textFieldBegin.textProperty().addListener(inputNewPatientListener);
        this.textFieldEnd.textProperty().addListener(inputNewPatientListener);
        this.textFieldDescription.textProperty().addListener(inputNewPatientListener);
        this.textAreaRemarks.textProperty().addListener(inputNewPatientListener);
        this.datePicker.valueProperty().addListener((observableValue, localDate, t1) -> NewTreatmentController.this.buttonAdd.setDisable(NewTreatmentController.this.areInputDataInvalid()));
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate == null) ? "" : DateConverter.convertLocalDateToString(localDate);
            }

            @Override
            public LocalDate fromString(String localDate) {
                return DateConverter.convertStringToLocalDate(localDate);
            }
        });
        this.showPatientData();
        this.createComboBoxData();
    }

    private void showPatientData() {
        this.labelFirstName.setText(patient.getFirstName());
        this.labelSurname.setText(patient.getSurname());
    }


    /**
     * Handles the action of adding a new treatment for the selected patient.
     *
     * Collects input data from the form fields including date, time, description, and remarks,
     * creates a {@link Treatment} object, and saves it using {@code createTreatment()}. After
     * saving, it updates the treatment table in the parent controller and closes the current window.
     *
     *
     */
    @FXML
    public void handleAdd() {
        LocalDate date = this.datePicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(textFieldBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(textFieldEnd.getText());
        String description = textFieldDescription.getText();
        String remarks = textAreaRemarks.getText();
        Treatment treatment = new Treatment(patient.getPid(), comboBoxCaregiverSelection.getSelectionModel().getSelectedItem().getCaregiverID(), date, begin, end, description, remarks);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }


    /**
     * Persists the given {@link Treatment} object to the database.
     *
     * Uses the {@link TreatmentDao} to insert the treatment record. If an SQL error occurs
     * during the operation, the exception is caught and its stack trace is printed.
     *
     * @param treatment the {@code Treatment} to be saved in the database
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            dao.create(treatment);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Handles the cancel action by closing the current window.
     */
    @FXML
    public void handleCancel() {
        stage.close();
    }


    /**
     * Validates the input fields for creating a new treatment.
     *
     * Checks for the following conditions:
     *
     *     - {@param Begin} and {@param End} time fields must not be null and must be valid {@link LocalTime} values.
     *     - {@param End} time must be after {@param Begin} time.
     *     - {@param Description} field must not be blank.
     *     - A valid date must be selected.
     *    - A caregiver must be selected from the combo box.
     *
     * @return {@code  true} if any required field is invalid or missing, {@code false} otherwise
     */
    private boolean areInputDataInvalid() {
        if (this.textFieldBegin.getText() == null || this.textFieldEnd.getText() == null) {
            return true;
        }
        try {
            LocalTime begin = DateConverter.convertStringToLocalTime(this.textFieldBegin.getText());
            LocalTime end = DateConverter.convertStringToLocalTime(this.textFieldEnd.getText());
            if (!end.isAfter(begin)) {
                return true;
            }
        } catch (Exception exception) {
            return true;
        }
        if (this.textFieldDescription.getText().isBlank() || this.datePicker.getValue() == null) {
            return true;
        }
        return this.comboBoxCaregiverSelection.getSelectionModel().getSelectedItem() == null;
    }


    /**
     * Populates the caregiver selection combo box with data from the database.
     *
     * Retrieves all caregivers using the {@link CaregiverDao} and adds them to the
     * {@code caregiverSelection} list, which is used as the data source for the combo box.
     *
     * Note: If a {@link SQLException} occurs during data retrieval, the stack trace is printed and no caregivers are added.
     */
    private void createComboBoxData() {
        CaregiverDao dao = DaoFactory.getDaoFactory().createCaregiverDAO();
        try {
            caregiverList = (ArrayList<Caregiver>) dao.readAll();
            this.caregiverSelection.addAll(caregiverList);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}