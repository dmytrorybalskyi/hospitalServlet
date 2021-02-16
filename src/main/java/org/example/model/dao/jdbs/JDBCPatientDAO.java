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
    public Patient create(Patient patient) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO patient (account_id, name, age) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patient.getId());
            preparedStatement.setString(2,patient.getName());
            preparedStatement.setInt(3,patient.getAge());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            close(preparedStatement);
        }
        return patient;
    }

    @Override
    public Patient findByLogin(String login) {
        return null;
    }

    @Override
    public Patient finById(int id) {
        return null;
    }

    @Override
    public List<Patient> findAll() {
        return null;
    }

    @Override
    public void update(Patient entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close(ResultSet resultSet) {
        try {
            if(resultSet!=null)
                resultSet.close();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close(PreparedStatement preparedStatement) {
        try {
            if(preparedStatement!=null)
                preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public void close(){
        try {
            if(connection!=null)
                connection.close();
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }
}
