package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Treatment;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalTime;
import static org.junit.jupiter.api.Assertions.*;

class TreatmentDaoTest {
    private static Connection connection;
    private static TreatmentDao treatmentDao;

    @BeforeAll
    static void initilize() throws SQLException {
        connection = SetUpTestDB.setUpDb();
        treatmentDao = new TreatmentDao(connection);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, 999);
        preparedStatement.setString(2, "testname");
        preparedStatement.setString(3, "testsurname");
        preparedStatement.setString(4, "1949-04-16");
        preparedStatement.setString(5, "1");
        preparedStatement.setString(6, "101");
        preparedStatement.executeUpdate();
    }

    @BeforeEach
    void clearTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM treatment");
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM patient");
    }

    @Test
    void testFindByID() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO treatment VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, 1);
        stmt.setInt(2, 999);
        stmt.setInt(3, 1);
        stmt.setString(4, "1999-12-12");
        stmt.setString(5, "11:00");
        stmt.setString(6, "12:00");
        stmt.setString(7, "DescriptionTest");
        stmt.setString(8, "RemarkTest");
        int inserted = stmt.executeUpdate();
        assertEquals(inserted, 1);
        Assertions.assertNotNull(treatmentDao.read(1));
    }

    @Test
    void testCreationOk() throws SQLException {
        Treatment treatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        treatmentDao.create(treatment);
        List<Treatment> treatments = treatmentDao.readAll();
        Treatment foundTreatment = null;
        for (Treatment t : treatments) {
            if (t.getPid()==(treatment.getPid())
                    && t.getCid()==(treatment.getCid())
                    && t.getDate().equals(treatment.getDate())
                    && t.getBegin().equals(treatment.getBegin())
                    && t.getEnd().equals(treatment.getEnd())
                    && t.getDescription().equals(treatment.getDescription())
                    && t.getRemarks().equals(treatment.getRemarks())) {
                foundTreatment = t;
                break;
            }
        }
        assertNotNull(foundTreatment);
    }

    @Test
    void testUpdateOk() throws SQLException {
        Treatment treatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        treatmentDao.create(treatment);
        List<Treatment> treatments = treatmentDao.readAll();
        Treatment foundTreatment = null;
        for (Treatment t : treatments) {
            if ( t.getPid()==(treatment.getPid())
                    && t.getCid()==(treatment.getCid())
                    && t.getDate().equals(treatment.getDate())
                    && t.getBegin().equals(treatment.getBegin())
                    && t.getEnd().equals(treatment.getEnd())
                    && t.getDescription().equals(treatment.getDescription())
                    && t.getRemarks().equals(treatment.getRemarks())) {
                foundTreatment = t;
                break;
            }
        }
        foundTreatment.setDate("2000-01-01");
        foundTreatment.setBegin("01:00");
        foundTreatment.setEnd("02:00");
        foundTreatment.setDescription("DescriptionTest2");
        foundTreatment.setRemarks("RemarkTest2");
        treatmentDao.update(foundTreatment);
        Treatment treatment2 = treatmentDao.read(foundTreatment.getTid());
        assertEquals(foundTreatment.getPid(), treatment2.getPid());
        assertEquals(foundTreatment.getCid(), treatment2.getCid());
        assertEquals(foundTreatment.getDate(), treatment2.getDate());
        assertEquals(foundTreatment.getBegin(), treatment2.getBegin());
        assertEquals(foundTreatment.getEnd(), treatment2.getEnd());
        assertEquals(foundTreatment.getDescription(), treatment2.getDescription());
        assertEquals(foundTreatment.getRemarks(), treatment2.getRemarks());
    }

    @Test
    void testDeleteOk() throws SQLException {
        Treatment treatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        treatmentDao.create(treatment);
        List<Treatment> treatments = treatmentDao.readAll();
        Treatment foundTreatment = null;
        for (Treatment t : treatments) {
            if (t.getPid()==(treatment.getPid())
                    && t.getCid()==(treatment.getCid())
                    && t.getDate().equals(treatment.getDate())
                    && t.getBegin().equals(treatment.getBegin())
                    && t.getEnd().equals(treatment.getEnd())
                    && t.getDescription().equals(treatment.getDescription())
                    && t.getRemarks().equals(treatment.getRemarks())) {
                foundTreatment = t;
                break;
            }
        }
        treatmentDao.deleteById(foundTreatment.getTid());
        assertNull(treatmentDao.read(foundTreatment.getTid()));

    }

}