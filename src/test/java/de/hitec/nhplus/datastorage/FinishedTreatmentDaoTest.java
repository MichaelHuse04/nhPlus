package de.hitec.nhplus.datastorage;

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

class FinishedTreatmentDaoTest {
    private static Connection connection;
    private static FinishedTreatmentDao finishedTreatmentDao;

    @BeforeAll
    static void initilize() throws SQLException {
        connection = SetUpTestDB.setUpDb();
        finishedTreatmentDao = new FinishedTreatmentDao(connection);
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
        stmt.executeUpdate("DELETE FROM finished_treatment");
    }

    @AfterAll
    static void clearTableEnd() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM patient");
    }

    @Test
    void testFindByID() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO finished_treatment VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
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
        Assertions.assertNotNull(finishedTreatmentDao.read(1));
    }

    @Test
    void testCreationOk() throws SQLException {
        Treatment finishedTreatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        finishedTreatmentDao.create(finishedTreatment);
        List<Treatment> finishedTreatments = finishedTreatmentDao.readAll();
        Treatment foundFinishedTreatment = null;
        for (Treatment t : finishedTreatments) {
            if (t.getPid()==(finishedTreatment.getPid())
                    && t.getCid()==(finishedTreatment.getCid())
                    && t.getDate().equals(finishedTreatment.getDate())
                    && t.getBegin().equals(finishedTreatment.getBegin())
                    && t.getEnd().equals(finishedTreatment.getEnd())
                    && t.getDescription().equals(finishedTreatment.getDescription())
                    && t.getRemarks().equals(finishedTreatment.getRemarks())) {
                foundFinishedTreatment = t;
                break;
            }
        }
        assertNotNull(foundFinishedTreatment);
    }

    @Test
    void testUpdateOk() throws SQLException {
        Treatment finishedTreatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        finishedTreatmentDao.create(finishedTreatment);
        List<Treatment> finishedTreatments = finishedTreatmentDao.readAll();
        Treatment foundFinishedTreatment = null;
        for (Treatment t : finishedTreatments) {
            if ( t.getPid()==(finishedTreatment.getPid())
                    && t.getCid()==(finishedTreatment.getCid())
                    && t.getDate().equals(finishedTreatment.getDate())
                    && t.getBegin().equals(finishedTreatment.getBegin())
                    && t.getEnd().equals(finishedTreatment.getEnd())
                    && t.getDescription().equals(finishedTreatment.getDescription())
                    && t.getRemarks().equals(finishedTreatment.getRemarks())) {
                foundFinishedTreatment = t;
                break;
            }
        }
        foundFinishedTreatment.setDate("2000-01-01");
        foundFinishedTreatment.setBegin("01:00");
        foundFinishedTreatment.setEnd("02:00");
        foundFinishedTreatment.setDescription("DescriptionTest2");
        foundFinishedTreatment.setRemarks("RemarkTest2");
        finishedTreatmentDao.update(foundFinishedTreatment);
        Treatment treatment2 = finishedTreatmentDao.read(foundFinishedTreatment.getTid());
        assertEquals(foundFinishedTreatment.getPid(), treatment2.getPid());
        assertEquals(foundFinishedTreatment.getCid(), treatment2.getCid());
        assertEquals(foundFinishedTreatment.getDate(), treatment2.getDate());
        assertEquals(foundFinishedTreatment.getBegin(), treatment2.getBegin());
        assertEquals(foundFinishedTreatment.getEnd(), treatment2.getEnd());
        assertEquals(foundFinishedTreatment.getDescription(), treatment2.getDescription());
        assertEquals(foundFinishedTreatment.getRemarks(), treatment2.getRemarks());
    }

    @Test
    void testDeleteOk() throws SQLException {
        Treatment finishedTreatment = new Treatment(1, 999, 1, convertStringToLocalDate("1999-12-12"), convertStringToLocalTime("11:00"), convertStringToLocalTime("12:00"), "DescriptionTest", "RemarkTest" );
        finishedTreatmentDao.create(finishedTreatment);
        List<Treatment> finishedTreatments = finishedTreatmentDao.readAll();
        Treatment foundFinishedTreatment = null;
        for (Treatment t : finishedTreatments) {
            if (t.getPid()==(finishedTreatment.getPid())
                    && t.getCid()==(finishedTreatment.getCid())
                    && t.getDate().equals(finishedTreatment.getDate())
                    && t.getBegin().equals(finishedTreatment.getBegin())
                    && t.getEnd().equals(finishedTreatment.getEnd())
                    && t.getDescription().equals(finishedTreatment.getDescription())
                    && t.getRemarks().equals(finishedTreatment.getRemarks())) {
                foundFinishedTreatment = t;
                break;
            }
        }
        finishedTreatmentDao.deleteById(foundFinishedTreatment.getTid());
        assertNull(finishedTreatmentDao.read(foundFinishedTreatment.getTid()));

    }

}