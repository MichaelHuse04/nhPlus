package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements.
 */
public class CaregiverDao extends DaoImp<Caregiver> {
    /**
     * The constructor initiates an object of <code>CaregiverDAO</code> and passes the connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the SQL-statements.
     */
    public CaregiverDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of <code>Caregiver</code>.
     *
     * @param caregiver Object of <code>Caregiver</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given caregiver.
     */
    @Override
    protected PreparedStatement getCreateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO caregiver (firstname, surname, phoneNumber) " +
                    "VALUES (?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query a caregiver by a given caregiver id (caregiverID).
     *
     * @param caregiverID Caregiver id to query.
     * @return <code>PreparedStatement</code> to query the caregiver.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long caregiverID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE caregiverID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, caregiverID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one caregiver to an object of <code>Caregiver</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an object of class <code>Caregiver</code>.
     * @return Object of class <code>Caregiver</code> with the data from the resultSet.
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Caregiver(
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4));
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all caregivers.
     *
     * @return <code>PreparedStatement</code> to query all caregivers.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM caregiver";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all caregivers to an <code>ArrayList</code> of <code>Caregiver</code> objects.
     *
     * @param result ResultSet with all rows. The Columns will be mapped to objects of class <code>Caregiver</code>.
     * @return <code>ArrayList</code> with objects of class <code>Caregiver</code> of all rows in the
     * <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<>();
        while (result.next()) {
            Caregiver caregiver = new Caregiver(result.getInt(1), result.getString(2),
                    result.getString(3),
                    result.getString(4));
            list.add(caregiver);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given caregiver, identified
     * by the id of the caregiver (caregiverID).
     *
     * @param caregiver Caregiver object to update.
     * @return <code>PreparedStatement</code> to update the given caregiver.
     */
    @Override
    protected PreparedStatement getUpdateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL =
                    "UPDATE caregiver SET " +
                            "firstname = ?, " +
                            "surname = ?, " +
                            "phoneNumber = ? " +
                            "WHERE caregiverID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
            preparedStatement.setLong(4, caregiver.getCaregiverID());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }


    /**
     * Generates a <code>PreparedStatement</code> to delete a caregiver with the given id.
     *
     * @param caregiverID Id of the caregiver to delete.
     * @return <code>PreparedStatement</code> to delete caregiver with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long caregiverID) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM caregiver WHERE caregiverID = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, caregiverID);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
