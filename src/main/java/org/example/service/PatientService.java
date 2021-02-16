package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.DAOFactory;
import org.example.model.dao.jdbs.JDBCAccountDAO;
import org.example.model.dao.jdbs.JDBCPatientDAO;
import org.example.model.entity.Account;
import org.example.model.entity.Patient;

import java.sql.Connection;
import java.sql.SQLException;

public class PatientService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public Account addPatient(Account account, Patient patient) throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCAccountDAO jdbcAccountDAO = new JDBCAccountDAO(connection);
            JDBCPatientDAO jdbcPatientDAO = new JDBCPatientDAO(connection);
            patient.setId(jdbcAccountDAO.create(account).getId());
            jdbcPatientDAO.create(patient);
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
