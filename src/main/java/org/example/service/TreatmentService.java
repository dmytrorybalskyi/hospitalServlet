package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.DAOFactory;
import org.example.model.dao.TreatmentDAO;
import org.example.model.dao.jdbs.JDBCTreatmentDAO;
import org.example.model.entity.*;
import java.sql.SQLException;
import java.util.Deque;

public class TreatmentService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public boolean setDoctor(Treatment treatment, Integer doctorId) {
        JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
        treatment.getPatient().setDoctor(new Doctor(doctorId));
        jdbcTreatmentDAO.setDoctor(treatment, doctorId);
        return true;
    }

    public Page getPageByStatus(Page page){
        JDBCTreatmentDAO jdbcTreatmentDAO =  new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
        return jdbcTreatmentDAO.pageByStatus(page);
        }


   // public Category getCategoryById(Integer treatmentId) {
   //     JDBCTreatmentDAO jdbcTreatmentDAO = new JDBCTreatmentDAO(ConnectionPoolHolder.getConnection());
  //      return jdbcTreatmentDAO.getCategoryById(treatmentId);
   // }

    public Treatment findById(Integer id) {
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.finById(id);
    }

    public Treatment createTreatment(Account account, Integer categoryId) throws SQLException {
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.create(new Treatment(new Patient(account.getId()),new Category(categoryId),Status.registration));
    }

    public Deque<Treatment> getAllByDoctorAndStatus(Account account){
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.getAllByDoctorAndStatus(account);
    }

    public boolean setDiagnosis(String diagnosis, Integer id){
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.setDiagnosis(diagnosis,id);
    }

    public boolean discharge(Integer doctorId,Integer patientId,Integer treatmentId) throws SQLException {
        Treatment treatment = new Treatment(treatmentId);
        treatment.setDoctor(new Doctor(doctorId));
        treatment.setPatient(new Patient(patientId));
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.discharge(treatment);
    }

    public Treatment getTreatmentByPatient(Account account){
        TreatmentDAO treatmentDAO = daoFactory.createTreatmentDAO();
        return treatmentDAO.findTreatmentByPatient(account.getId());
    }

}
