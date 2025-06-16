package de.hitec.nhplus.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class Caregiver extends Person {
    private SimpleLongProperty caregiverID;
    private SimpleStringProperty phoneNumber;


    /**
     * Constructor to initiate an object of class <code>Caregiver</code> with the given parameter. Use this constructor
     * to initiate objects, which are not persisted yet, because it will not have a caregiver id (caregiverID).
     *
     * @param firstName First name of the caregiver.
     * @param surname Last name of the caregiver.
     * @param phoneNumber phone number of the caregiver.
     */
    public Caregiver(String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    /**
     * Constructor to initiate an object of class <code>Patient</code> with the given parameter. Use this constructor
     * to initiate objects, which are already persisted and have a patient id (pid).
     *
     * @param caregiverID Caregiver id.
     * @param firstName First name of the caregiver.
     * @param surname Last name of the caregiver.
     * @param phoneNumber phone number of the caregiver.
     */
    public Caregiver(long caregiverID, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.caregiverID = new SimpleLongProperty(caregiverID);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    //Getter & Setter
    public long getCaregiverID() {
        return caregiverID.get();
    }

    public SimpleLongProperty caregiverIDProperty() {
        return caregiverID;
    }

    public void setCaregiverID(long caregiverID) {
        this.caregiverID.set(caregiverID);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    @Override
    public String toString() {
        return this.getSurname();
    }
}
