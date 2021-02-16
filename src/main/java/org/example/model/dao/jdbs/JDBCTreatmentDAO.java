package org.example.model.dao.jdbs;

import org.example.model.dao.TreatmentDAO;
import org.example.model.entity.Category;
import org.example.model.entity.Patient;
import org.example.model.entity.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCTreatmentDAO implements TreatmentDAO {

    private Connection connection;

    public JDBCTreatmentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Treatment create(Treatment treatment) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO treatment (patient_account_id, category_id, status_id) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, treatment.getPatientId());
            preparedStatement.setInt(2, treatment.getCategoryId());
            preparedStatement.setInt(3, treatment.getStatusId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(preparedStatement);
        }
        return treatment;
    }

    public List<Treatment> isAppointment(Integer patientAccountId) {
        List<Treatment> result = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment WHERE patient_account_id = ? AND (status_id = ? OR status_id = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientAccountId);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 2);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Treatment treatment = new Treatment(
                        resultSet.getInt(3),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                result.add(treatment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }

    public boolean setDoctor(Treatment treatment, Integer doctorId) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE treatment SET doctor_account_id = ?, status_id = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setInt(2, 2);
            preparedStatement.setInt(3, treatment.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(preparedStatement);
            close();
        }
        return true;
    }

    public List<Treatment> findByStatus() {
        List<Treatment> result = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id LEFT JOIN category ON category_id = category.id  WHERE status_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Treatment treatment = new Treatment(
                        resultSet.getInt(3),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                treatment.setId(resultSet.getInt(1));
                treatment.setPatient(new Patient(resultSet.getString(8), resultSet.getInt(9)));
                treatment.setCategory(new Category(resultSet.getInt(11), resultSet.getString(12)));
                result.add(treatment);
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
    public Treatment findByLogin(String login) {
        return null;
    }

    @Override
    public Treatment finById(int id) {
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id " +
                "LEFT JOIN category ON category_id = category.id  WHERE treatment.id = ?";
        Treatment treatment = new Treatment();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "" + id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                treatment.setId(rs.getInt(1));
                treatment.setCategory(new Category(rs.getInt(11), rs.getString(12)));
                treatment.setPatient(new Patient(rs.getString(8), rs.getInt(9), rs.getInt(7)));
                treatment.setStatusId(rs.getInt(6));
            }
            return treatment;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        } finally {
            close(rs);
            close(preparedStatement);
            close();
        }
        return null;
    }

    public Category getCategoryById(int id) {
        String query = "SELECT category_id FROM treatment WHERE id = ?";
        Category category = new Category();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "" + id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                category.setId(rs.getInt("category_id"));
            }
            return category;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        } finally {
            close(rs);
            close(preparedStatement);
        }
        return null;
    }

    @Override
    public List<Treatment> findAll() {
        return null;
    }

    @Override
    public void update(Treatment entity) {

    }

    @Override
    public void delete(int id) {

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
