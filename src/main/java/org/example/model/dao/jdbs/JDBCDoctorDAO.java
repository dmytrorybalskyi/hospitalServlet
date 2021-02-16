package org.example.model.dao.jdbs;

import org.example.model.dao.DoctorDAO;
import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDoctorDAO implements DoctorDAO {
    private Connection connection;

    public JDBCDoctorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Doctor create(Doctor doctor) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO doctor (account_id, name, category_id) VALUES(?,?,?)";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(connection);
            doctor.setAccount(jdbcAccountDAO.create(doctor.getAccount()));
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctor.getAccount().getId());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setInt(3, doctor.getCategoryId());
            preparedStatement.executeUpdate();
            connection.commit();
            return doctor;
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
    public List<Doctor> findByCategory(Category category) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List result = new ArrayList();
        String query = "SELECT * FROM doctor WHERE category_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, category.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Doctor doctor = new Doctor(resultSet.getString(2), resultSet.getInt(4));
                doctor.setAccount(new Account(resultSet.getInt(1)));
                result.add(doctor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return result;
    }

    @Override
    public Doctor finById(int id) {
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        return null;
    }

    @Override
    public boolean update(Doctor doctor) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Doctor  SET patients_number = ? WHERE account_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor.getPatientNumber());
            preparedStatement.setInt(2,doctor.getAccount().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            close(preparedStatement);
        }return true;
    }

    public boolean changePatientNumber(Integer doctor_account_id, int patientNumber){
        PreparedStatement preparedStatement = null;
        String query = "UPDATE Doctor  SET patients_number = patients_number + ? WHERE account_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patientNumber);
            preparedStatement.setInt(2,doctor_account_id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            close(preparedStatement);
        }return true;
    }

    @Override
    public void close() {
        try {
            if (connection != null)
                this.connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
