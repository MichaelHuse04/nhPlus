package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Patient;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PatientDaoTest {
    private static Connection connection;
    private static PatientDao patientDao;

    @BeforeAll
    static void initilize() throws SQLException {
        connection = SetUpTestDB.setUpDb();
        patientDao = new PatientDao(connection);
    }

    @BeforeEach
    void clearTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM patient");
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM patient");
    }

    @Test
    void testFindByID() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO patient VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, 999);
        stmt.setString(2, "testname");
        stmt.setString(3, "testsurname");
        stmt.setString(4, "1949-04-16");
        stmt.setString(5, "1");
        stmt.setString(6, "101");

        int inserted = stmt.executeUpdate();
        assertEquals(inserted, 1);
        Assertions.assertNotNull(patientDao.read(999));
    }

    @Test
    void testCreationOk() throws SQLException {
        Patient patient = new Patient("testname", "testsurname",convertStringToLocalDate("1949-04-16"), "1", "101");
        patientDao.create(patient);
        List<Patient> patients = patientDao.readAll();
        Patient foundPatient = null;
        for (Patient p : patients) {
            if (p.getSurname().equals(patient.getSurname())
                    && p.getFirstName().equals(patient.getFirstName())
                    && p.getDateOfBirth().equals(patient.getDateOfBirth())
                    && p.getCareLevel().equals(patient.getCareLevel())
                    && p.getRoomNumber().equals(patient.getRoomNumber())) {
                foundPatient = p;
                break;
            }
        }
        assertNotNull(foundPatient);
    }

    @Test
    void testUpdateOk() throws SQLException {
        Patient patient = new Patient("testname", "testsurname",convertStringToLocalDate("1949-04-16"), "1", "101");
        patientDao.create(patient);
        List<Patient> patients = patientDao.readAll();
        Patient foundPatient = null;
        for (Patient p : patients) {
            if (p.getSurname().equals(patient.getSurname())
                    && p.getFirstName().equals(patient.getFirstName())
                    && p.getDateOfBirth().equals(patient.getDateOfBirth())
                    && p.getCareLevel().equals(patient.getCareLevel())
                    && p.getRoomNumber().equals(patient.getRoomNumber())) {
                foundPatient = p;
                break;
            }
        }
        foundPatient.setSurname("ABCD");
        foundPatient.setFirstName("ABCDE");
        foundPatient.setDateOfBirth("2000-01-01");
        foundPatient.setCareLevel("5");
        foundPatient.setRoomNumber("123");

        patientDao.update(foundPatient);
        Patient patient2 = patientDao.read(foundPatient.getPid());
        assertEquals(foundPatient.getSurname(), patient2.getSurname());
        assertEquals(foundPatient.getFirstName(), patient2.getFirstName());
        assertEquals(foundPatient.getDateOfBirth(), patient2.getDateOfBirth());
        assertEquals(foundPatient.getCareLevel(), patient2.getCareLevel());
        assertEquals(foundPatient.getRoomNumber(), patient2.getRoomNumber());
    }

    @Test
    void testDeleteOk() throws SQLException {
        Patient patient = new Patient("testname", "testsurname",convertStringToLocalDate("1949-04-16"), "1", "101");
        patientDao.create(patient);
        List<Patient> patients = patientDao.readAll();
        Patient foundPatient = null;
        for (Patient p : patients) {
            if (p.getSurname().equals(patient.getSurname())
                    && p.getFirstName().equals(patient.getFirstName())
                    && p.getDateOfBirth().equals(patient.getDateOfBirth())
                    && p.getCareLevel().equals(patient.getCareLevel())
                    && p.getRoomNumber().equals(patient.getRoomNumber())) {
                foundPatient = p;
                break;
            }
        }
        patientDao.deleteById(foundPatient.getPid());
        assertNull(patientDao.read(foundPatient.getPid()));

    }

}