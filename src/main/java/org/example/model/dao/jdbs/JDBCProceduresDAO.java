package org.example.model.dao.jdbs;

import org.example.model.dao.ProceduresDAO;
import org.example.model.entity.Doctor;
import org.example.model.entity.Procedures;
import org.example.model.entity.Status;
import org.example.model.mapper.ProceduresMapper;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JDBCProceduresDAO implements ProceduresDAO {
    private Connection connection;
    private ProceduresMapper proceduresMapper = new ProceduresMapper();

    public JDBCProceduresDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Procedures create(Procedures procedures) throws SQLException {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO procedures (name,treatment_id,doctor_account_id,type,procedure_status) VALUES(?,?,?,?,?)";
        ResultSet resultSet = null;
        try {
            JDBCDoctorDAO jdbcDoctorDAO = new JDBCDoctorDAO(connection);
            Doctor doctor = jdbcDoctorDAO.finById(procedures.getDoctor().getId());
            if (doctor.getCategory().getId() == 5 && procedures.getType().name().equals("operation")) {
                throw new SQLException("Nurse cannot do operation");
            }
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, procedures.getName());
            preparedStatement.setInt(2, procedures.getTreatment().getId());
            preparedStatement.setInt(3, procedures.getDoctor().getId());
            preparedStatement.setString(4, procedures.getType().name());
            preparedStatement.setString(5, procedures.getStatus().name());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                procedures.setId(resultSet.getInt(1));
            }
            return procedures;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
    }

    @Override
    public List<Procedures> findALlByDoctor(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Procedures> proceduresList = new LinkedList<>();
        String query = "SELECT * FROM procedures LEFT JOIN treatment ON treatment_id = treatment.id LEFT JOIN patient ON treatment.patient_account_id = patient.account_id WHERE procedures.doctor_account_id = ? AND procedure_status = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, Status.treatment.name());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                proceduresList.add(proceduresMapper.extractFromResultSetWihPatient(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return proceduresList;
    }

    @Override
    public boolean doProcedures(Integer id) {
        PreparedStatement preparedStatement = null;
        String query = "UPDATE procedures  SET procedure_status = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Status.done.name());
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
    public Procedures finById(int id) {
        return null;
    }

    @Override
    public List<Procedures> findAll() {
        return null;
    }

    @Override
    public boolean update(Procedures entity) {
        return false;
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
