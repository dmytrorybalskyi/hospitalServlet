package org.example.model.dao.jdbs;

import org.example.model.dao.DoctorDAO;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDoctorDAO implements DoctorDAO {
    private Connection connection;

    public JDBCDoctorDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Doctor create(Doctor doctor) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO doctor (account_id, name, category_id) VALUES(?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctor.getId());
            preparedStatement.setString(2,doctor.getName());
            preparedStatement.setInt(3,doctor.getCategoryId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            close(preparedStatement);
        }
        return doctor;
    }

    public List<Doctor> findByCategory(Category category){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List result = new ArrayList();
        String query = "SELECT * FROM doctor WHERE category_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,category.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Doctor doctor = new Doctor(resultSet.getString(2),resultSet.getInt(4));
                doctor.setId(resultSet.getInt(1));
                result.add(doctor);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            close(resultSet);
            close(preparedStatement);
            close();
        }
        return result;
    }

    @Override
    public Doctor findByLogin(String login) {
        return null;
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
    public void update(Doctor entity) {

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
