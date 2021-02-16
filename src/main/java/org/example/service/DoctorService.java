package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.jdbs.JDBCAccountDAO;
import org.example.model.dao.jdbs.JDBCDoctorDAO;
import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DoctorService {

    public List<Doctor> findByCategory(Category category){
        JDBCDoctorDAO jdbcDoctorDAO= new JDBCDoctorDAO(ConnectionPoolHolder.getConnection());
        return jdbcDoctorDAO.findByCategory(category);
    }

    public Account addDoctor(String login,String password,String name, Integer categoryId) throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        Account account = new Account(login,password);
        account.setRole("doctor");
        Doctor doctor = new Doctor(name,categoryId);
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(connection);
            JDBCDoctorDAO jdbcDoctorDAO = new JDBCDoctorDAO(connection);
            doctor.setId(jdbcAccountDAO.create(account).getId());
            jdbcDoctorDAO.create(doctor);
            connection.commit();
            return account;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            try {
                assert connection != null;
                connection.rollback();
                throw e;
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        } return null;
    }
}
