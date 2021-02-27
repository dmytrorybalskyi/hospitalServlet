package org.example.model.dao.jdbs;

import org.example.model.dao.TreatmentDAO;
import org.example.model.entity.*;
import org.example.model.mapper.PatientMapper;
import org.example.model.mapper.ProceduresMapper;
import org.example.model.mapper.TreatmentMapper;

import java.sql.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class JDBCTreatmentDAO implements TreatmentDAO {

    private Connection connection;
    private PatientMapper patientMapper = new PatientMapper();
    private TreatmentMapper treatmentMapper = new TreatmentMapper();
    private ProceduresMapper proceduresMapper = new ProceduresMapper();

    public JDBCTreatmentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Treatment create(Treatment treatment) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO treatment (patient_account_id, category_id, treatment_status) VALUES(?,?,?)";
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            if (!isAppointment(treatment.getPatient().getId())) {
                close();
                throw new SQLException("you already appointment");
            }
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, treatment.getPatient().getId());
            preparedStatement.setInt(2, treatment.getCategory().getId());
            preparedStatement.setString(3, treatment.getStatus().name());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                treatment.setId(resultSet.getInt(1));
            }
            connection.commit();
            return treatment;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            close(resultSet);
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
    public boolean setDoctor(Treatment treatment, Integer doctorId) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE treatment SET doctor_account_id = ?, treatment_status = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            checkStatus(treatment.getId());
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
        }
        return true;
    }

    @Override
    public Page pageByStatus(Page page) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment  LEFT JOIN patient ON patient_account_id = account_id LEFT JOIN" +
                " category ON category_id = category.id  WHERE treatment_status = ? LIMIT ?, ?";
        try {
            page = getPageNumber(page);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Status.registration.name());
            preparedStatement.setInt(2, page.getPage() * page.getObjectsOnPage());
            preparedStatement.setInt(3, page.getObjectsOnPage());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Treatment treatment = new Treatment(
                        patientMapper.extractFromResultSet(resultSet),
                        new Category(resultSet.getInt("category.id"), resultSet.getString("category.name")),
                        Status.valueOf(resultSet.getString("treatment_status")));
                treatment.setId(resultSet.getInt("treatment.id"));
                page.getList().add(treatment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return page;
    }

    @Override
    public Page getPageNumber(Page page) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(id) FROM treatment WHERE treatment_status = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "registration");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                page.setTotalPages(resultSet.getInt("COUNT(id)"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(preparedStatement);
            close(resultSet);
        }
        return page;
    }


    @Override
    public Treatment finById(int id) {
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id " +
                "LEFT JOIN category ON category_id = category.id  WHERE treatment.id = ?";
        Treatment treatment = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                treatment = treatmentMapper.treatmentById(rs);
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
    public Deque<Treatment> getAllByDoctorAndStatus(Account account) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM treatment LEFT JOIN patient ON patient_account_id = account_id LEFT JOIN" +
                " procedures ON treatment_id = treatment.id WHERE treatment.doctor_account_id = ? AND treatment_status = ?";
        Deque<Treatment> treatmentDeque = new LinkedList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account.getId());
            preparedStatement.setString(2, Status.treatment.name());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                treatmentMapper.treatmentWithPatient(resultSet, treatmentDeque);
                treatmentDeque.getFirst().getProceduresList().add(proceduresMapper.extractFromResultSet(resultSet));
            }
            return treatmentDeque;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return null;
    }

    @Override
    public boolean setDiagnosis(String diagnosis, Integer id) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE treatment SET diagnosis = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, diagnosis);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            close(preparedStatement);
            close();
        }
        return true;
    }

    @Override
    public Treatment findTreatmentByPatient(Integer id) {
        String query = "SELECT * FROM treatment LEFT JOIN category ON category_id = category.id LEFT JOIN doctor ON doctor_account_id = doctor.account_id " +
                "LEFT JOIN procedures ON treatment.id = treatment_id WHERE patient_account_id = ? AND treatment_status = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Procedures> proceduresList = new LinkedList<>();
        Treatment treatment = new Treatment();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, Status.treatment.name());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                treatment = treatmentMapper.extractFromResultSet(resultSet);
                proceduresList.add(proceduresMapper.extractFromResultSet(resultSet));
            }
            treatment.setProceduresList(proceduresList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return treatment;
    }

    @Override
    public boolean discharge(Treatment treatment) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE treatment  SET treatment_status = ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCDoctorDAO jdbcDoctorDAO = new JDBCDoctorDAO(connection);
            JDBCPatientDAO jdbcPatientDAO = new JDBCPatientDAO(connection);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Status.done.name());
            preparedStatement.setInt(2, treatment.getId());
            preparedStatement.executeUpdate();
            jdbcPatientDAO.removeDoctor(treatment.getPatient());
            jdbcDoctorDAO.changePatientNumber(treatment.getDoctor().getId(), -1);
            connection.commit();
        } catch (SQLException e) {
            assert connection != null;
            connection.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            close(preparedStatement);
            close();
        }
        return true;
    }

    public boolean checkStatus(Integer treatmentID) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String status = null;
        String query = "SELECT * FROM treatment WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, treatmentID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                status = resultSet.getString("treatment_status");
            }
            if (status.equals("treatment") || status.equals("done"))
                throw new SQLException("doctor already set");
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return true;
    }

    @Override
    public boolean update(Treatment entity) {
        return true;
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
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
