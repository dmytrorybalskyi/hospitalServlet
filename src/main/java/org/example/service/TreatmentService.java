package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.DAOFactory;
import org.example.model.dao.TreatmentDAO;
import org.example.model.dao.jdbs.JDBCTreatmentDAO;
import org.example.model.entity.*;
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
        treatment.getPatient().setDoctor(new Doctor(new Account(doctorId)));
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

    public Treatment createTreatment(Account account, Integer categoryId) throws SQLException {
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.create(new Treatment(new Patient(account),new Category(categoryId),Status.registration));
    }

    public List<Treatment> getAllByDoctorAndStatus(Account account){
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.getAllByDoctorAndStatus(account);
    }

}
