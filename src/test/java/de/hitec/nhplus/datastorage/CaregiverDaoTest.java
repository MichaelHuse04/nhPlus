package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaregiverDaoTest {
    private static Connection connection;
    private static CaregiverDao caregiverDao;

    @BeforeAll
    static void initilize() throws SQLException {
        connection = SetUpTestDB.setUpDb();
        caregiverDao = new CaregiverDao(connection);
    }

    @BeforeEach
    void clearTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM caregiver");
    }

    @AfterAll
    static void clearTableEnd() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM caregiver");
    }


    @Test
    void testFindByID() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO caregiver VALUES (?, ?, ?, ?)");
        stmt.setInt(1, 999);
        stmt.setString(2, "testname");
        stmt.setString(3, "testsurname");
        stmt.setString(4, "1234");
        int inserted = stmt.executeUpdate();
        assertEquals(inserted, 1);
        Assertions.assertNotNull(caregiverDao.read(999));
    }

    @Test
    void testCreationOk() throws SQLException {
        Caregiver caregiver = new Caregiver("TESTFALL1", "TESTFALL2", "123");
        caregiverDao.create(caregiver);
        List<Caregiver> caregivers = caregiverDao.readAll();
        Caregiver foundCaregiver = null;
        for (Caregiver c : caregivers) {
            if (c.getSurname().equals(caregiver.getSurname())
                    && c.getFirstName().equals(caregiver.getFirstName())
                    && c.getPhoneNumber().equals(caregiver.getPhoneNumber())) {
                foundCaregiver = c;
                break;
            }
        }
        assertNotNull(foundCaregiver);
    }

    @Test
    void testUpdateOk() throws SQLException {
        Caregiver caregiver = new Caregiver("TESTFALL1", "TESTFALL2", "123");
        caregiverDao.create(caregiver);
        List<Caregiver> caregivers = caregiverDao.readAll();
        Caregiver foundCaregiver = null;
        for (Caregiver c : caregivers) {
            if (c.getSurname().equals(caregiver.getSurname())
                    && c.getFirstName().equals(caregiver.getFirstName())
                    && c.getPhoneNumber().equals(caregiver.getPhoneNumber())) {
                foundCaregiver = c;
                break;
            }
        }
        foundCaregiver.setSurname("ABCD");
        foundCaregiver.setPhoneNumber("321");
        foundCaregiver.setFirstName("ABCDE");
        caregiverDao.update(foundCaregiver);
        Caregiver caregiver2 = caregiverDao.read(foundCaregiver.getCaregiverID());
        assertEquals(foundCaregiver.getSurname(), caregiver2.getSurname());
        assertEquals(foundCaregiver.getFirstName(), caregiver2.getFirstName());
        assertEquals(foundCaregiver.getPhoneNumber(), caregiver2.getPhoneNumber());
    }

    @Test
    void testDeleteOk() throws SQLException {
        Caregiver caregiver = new Caregiver("TESTFALL1", "TESTFALL2", "123");
        caregiverDao.create(caregiver);
        List<Caregiver> caregivers = caregiverDao.readAll();
        Caregiver foundCaregiver = null;
        for (Caregiver c : caregivers) {
            if (c.getSurname().equals(caregiver.getSurname())
                    && c.getFirstName().equals(caregiver.getFirstName())
                    && c.getPhoneNumber().equals(caregiver.getPhoneNumber())) {
                foundCaregiver = c;
                break;
            }
        }
        caregiverDao.deleteById(foundCaregiver.getCaregiverID());
        assertNull(caregiverDao.read(foundCaregiver.getCaregiverID()));

    }

}