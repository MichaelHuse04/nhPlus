package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.utils.DateConverter;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class FinishedTreatmentController {

    @FXML
    private Label labelPatientName;

    @FXML
    private Label labelCareLevel;

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

    private AllFinishedTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Treatment finishedTreatment;

    /**
     * When <code>initialize()</code> gets called, all fields are already initialized. For example from the FXMLLoader
     * after loading an FXML-File. At this point of the lifecycle of the Controller, the fields can be accessed and
     * configured.
     *
     * @param controller the controller managing the treatment overview or list
     * @param stage      the current JavaFX stage (window) in which this controller is operating
     * @param finishedTreatment  the finished treatment to be displayed and potentially edited
     */
    public void initializeController(AllFinishedTreatmentController controller, Stage stage, Treatment finishedTreatment) {
        this.stage = stage;
        this.controller= controller;
        PatientDao pDao = DaoFactory.getDaoFactory().createPatientDAO();
        try {
            this.patient = pDao.read((int) finishedTreatment.getPid());
            this.finishedTreatment = finishedTreatment;
            showData();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Displays the finihsed treatment and patient information in the user interface.
     *
     * Sets the values of various UI components using the data
     * from the {@code Patient} and {@code FInishedTreatment} objects.
     *
     * Displays patient's name and care level, and fills the finihsed treatment-related
     * fields such as date, start time, end time, description, and remarks.
     *
     *
     * Assumes: {@code patient} and {@code finished treatment} are already initialized.
     */
    private void showData(){
        this.labelPatientName.setText(patient.getSurname()+", "+patient.getFirstName());
        this.labelCareLevel.setText(patient.getCareLevel());
        LocalDate date = DateConverter.convertStringToLocalDate(finishedTreatment.getDate());
        this.datePicker.setValue(date);
        this.textFieldBegin.setText(this.finishedTreatment.getBegin());
        this.textFieldEnd.setText(this.finishedTreatment.getEnd());
        this.textFieldDescription.setText(this.finishedTreatment.getDescription());
        this.textAreaRemarks.setText(this.finishedTreatment.getRemarks());
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }
}