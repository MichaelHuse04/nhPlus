package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.model.Treatment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class UpdateTreatmentToFinishedTreatment {

    Connection connection = ConnectionBuilder.getConnection();


    public UpdateTreatmentToFinishedTreatment() {
        this.connection = connection;
    }

    public static void createCheckTimer() {

        Connection connection = ConnectionBuilder.getConnection();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {
                try{
                    String SQL = "SELECT * FROM treatment WHERE 1";
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    System.out.println("Checking treatment for updates...");
                    while (resultSet.next()) {
                        String date = resultSet.getString(3);
                        String timeEnd = resultSet.getString(5);
                        if (checkIfTreatmentIsFinished(date, timeEnd)) {
                            Treatment foundTreatment = new Treatment(
                                resultSet.getLong("tid"),
                                resultSet.getLong("pid"),
                                DateConverter.convertStringToLocalDate(resultSet.getString("treatment_date")),
                                DateConverter.convertStringToLocalTime(resultSet.getString("begin")),
                                DateConverter.convertStringToLocalTime(resultSet.getString("end")),
                                resultSet.getString("description"),
                                resultSet.getString("remark")
                            );
                            moveOneTreatmentToFinishedTreatment(foundTreatment);

                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        timer.schedule(task, 1000, 10000);
    }

    private static boolean checkIfTreatmentIsFinished(String date, String time) {
        int comparedDateResult = date.compareTo(LocalDate.now().toString());
        int comparedTimeResult = time.compareTo(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString());
        if (comparedDateResult < 0){
            return true;
        } else if (comparedDateResult > 0) {
            return false;
        } else {
            if (comparedTimeResult < 0){
                return true;
            } else {
                return false;
            }
        }
    }

    public static void moveOneTreatmentToFinishedTreatment(Treatment treatment) {

        Connection connection = ConnectionBuilder.getConnection();

        try {
            String copySQL = "INSERT INTO finished_treatment VALUES(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedCopyStatement = connection.prepareStatement(copySQL);
            preparedCopyStatement.setInt(1, (int) treatment.getTid());
            preparedCopyStatement.setInt(2, (int) treatment.getPid());
            preparedCopyStatement.setString(3, treatment.getDate());
            preparedCopyStatement.setString(4, treatment.getBegin());
            preparedCopyStatement.setString(5, treatment.getEnd());
            preparedCopyStatement.setString(6, treatment.getDescription());
            preparedCopyStatement.setString(7, treatment.getRemarks());

            preparedCopyStatement.execute();
            System.out.println("Moving treatment to finished treatment...");


            String deleteSQL = "DELETE FROM treatment WHERE tid = ?;";
            PreparedStatement preparedDeleteStatement = connection.prepareStatement(deleteSQL);
            preparedDeleteStatement.setInt(1, (int) treatment.getTid());
            preparedDeleteStatement.execute();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
