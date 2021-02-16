package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.DAOFactory;
import org.example.model.dao.TreatmentDAO;
import org.example.model.dao.jdbs.JDBCTreatmentDAO;
import org.example.model.entity.Category;
import org.example.model.entity.Treatment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TreatmentService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public List<Treatment> getTreatmentByStatus() {
        JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
        return jdbcTreatmentDAO.findByStatus();
    }

    public boolean setDoctor(Treatment treatment, Integer doctorId) {
        JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
        jdbcTreatmentDAO.setDoctor(treatment, doctorId);
        return true;
    }

    public Category getCategoryById(Integer treatmentId) {
        JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
        return jdbcTreatmentDAO.getCategoryById(treatmentId);
    }

    public Treatment findById(Integer id) {
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.finById(id);
    }

    public Treatment createTreatment(Integer accountId, Integer categoryId) throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(connection);
            if (!jdbcTreatmentDAO.isAppointment(accountId).isEmpty()) {
                throw new SQLException();
            }
            Treatment treatment = jdbcTreatmentDAO.create(new Treatment(accountId, categoryId, 1));
            connection.commit();
            return treatment;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (connection != null)
                connection.close();
        }
    }

}
