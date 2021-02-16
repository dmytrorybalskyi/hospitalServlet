package org.example.model.dao.jdbs;

import org.example.model.dao.TreatmentDAO;
import org.example.model.entity.*;
import org.example.model.mapper.PatientMapper;
import org.example.model.mapper.TreatmentMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JDBCTreatmentDAO implements TreatmentDAO {

    private Connection connection;
    private PatientMapper patientMapper = new PatientMapper();
    private TreatmentMapper treatmentMapper = new TreatmentMapper();

    public JDBCTreatmentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Treatment create(Treatment treatment) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO treatment (patient_account_id, category_id, treatment_status) VALUES(?,?,?)";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            if(!isAppointment(treatment.getPatient().getAccount().getId())){
                throw new SQLException("you already appointment");
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, treatment.getPatient().getAccount().getId());
            preparedStatement.setInt(2, treatment.getCategory().getId());
            preparedStatement.setString(3, treatment.getStatus().name());
            preparedStatement.executeUpdate();
            connection.commit();
            return treatment;
        }catch (SQLException e) {
            assert connection != null;
            connection.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            close(preparedStatement);
            close();
        }
    }

    @Override
    public boolean isAppointment(Integer patientAccountId) {
        List<Treatment> result = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM treatment WHERE patient_account_id = ? AND (treatment_status = ? OR treatment_status = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patientAccountId);
            preparedStatement.setString(2, Status.registration.name());
            preparedStatement.setString(3, Status.treatment.name());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Treatment treatment = new Treatment(resultSet.getInt("id"));
                result.add(treatment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result.isEmpty();
    }

    @Override
    public boolean setDoctor(Treatment treatment, Integer doctorId) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE treatment SET doctor_account_id = ?, treatment_status = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCDoctorDAO jdbcDoctorDAO = new JDBCDoctorDAO(connection);
            JDBCPatientDAO jdbcPatientDAO = new JDBCPatientDAO(connection);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, Status.treatment.name());
            preparedStatement.setInt(3, treatment.getId());
            preparedStatement.executeUpdate();
            jdbcPatientDAO.update(treatment.getPatient());
            jdbcDoctorDAO.changePatientNumber(doctorId, 1);
            connection.commit();
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            close(preparedStatement);
            close();
            return true;
        }
    }

    @Override
    public List<Treatment> findByStatus() {
        List<Treatment> result = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id LEFT JOIN category ON category_id = category.id  WHERE treatment_status = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,Status.registration.name() );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Treatment treatment = new Treatment(
                        patientMapper.extractFromResultSet(resultSet),
                        new Category(resultSet.getInt("category.id"),resultSet.getString("category.name")),
                        Status.valueOf(resultSet.getString("treatment_status")));
                treatment.setId(resultSet.getInt("treatment.id"));
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
    public Treatment finById(int id) {
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id " +
                "LEFT JOIN category ON category_id = category.id  WHERE treatment.id = ?";
        Treatment treatment = new Treatment();
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                treatment.setId(rs.getInt(1));
                treatment.setCategory(new Category(rs.getInt(11), rs.getString(12)));
                treatment.setPatient(new Patient(rs.getString(8), rs.getInt(9), new Account(rs.getInt(7))));
                treatment.setStatus(Status.valueOf(rs.getString(6)));
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

    @Override
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
    public List<Treatment> getAllByDoctorAndStatus(Account account) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id LEFT JOIN procedures ON treatment_id = treatment.id WHERE treatment.doctor_account_id = ? AND treatment_status = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, Status.treatment.name());
            resultSet = preparedStatement.executeQuery();
            return treatmentMapper.AllTreatmentFromResultSet(resultSet);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return null;
    }

    @Override
    public List<Treatment> findAll() {
        return null;
    }

    @Override
    public boolean update(Treatment entity) {
    return  true;
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
