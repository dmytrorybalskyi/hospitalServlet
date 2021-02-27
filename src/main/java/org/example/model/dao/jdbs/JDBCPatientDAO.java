package org.example.model.dao.jdbs;

import org.example.model.dao.PatientDAO;
import org.example.model.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCPatientDAO implements PatientDAO {
    private Connection connection;

    public JDBCPatientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Patient create(Patient patient) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO patient (account_id, name, age) VALUES(?,?,?)";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(connection);
            patient.setAccount(jdbcAccountDAO.create(patient.getAccount()));
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getAccount().getId());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setInt(3, patient.getAge());
            preparedStatement.executeUpdate();
            connection.commit();
            return patient;
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            close(preparedStatement);
            close();
        }
    }

    @Override
    public Patient finById(int id) {
        return null;
    }

    @Override
    public boolean update(Patient patient) {
        String query = "UPDATE patient SET doctor_account_id = ? WHERE account_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getDoctor().getId());
            preparedStatement.setInt(2, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(preparedStatement);
        }
        return true;
    }

    @Override
    public boolean removeDoctor(Patient patient) {
        String query = "UPDATE patient SET doctor_account_id = null WHERE account_id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(preparedStatement);
        }
        return true;
    }


    @Override
    public void close(ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
    }
}
