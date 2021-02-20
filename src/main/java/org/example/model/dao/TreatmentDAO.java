package org.example.model.dao;

import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Page;
import org.example.model.entity.Treatment;

import java.sql.SQLException;
import java.util.Deque;

public interface TreatmentDAO extends GenericDAO<Treatment> {

    boolean setDoctor(Treatment treatment, Integer doctorId);

    boolean isAppointment(Integer patientAccountId);

    Page pageByStatus(Page page);

    Page getPageNumber(Page page);

    Category getCategoryById(int id);

    Deque<Treatment> getAllByDoctorAndStatus(Account account);

    boolean setDiagnosis(String diagnosis, Integer id);

    boolean discharge(Treatment treatment) throws SQLException;

    Treatment findTreatmentByPatient(Integer id);

}
