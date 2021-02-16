package org.example.model.dao;

import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Treatment;

import java.util.List;

public interface TreatmentDAO extends GenericDAO<Treatment>{

    boolean setDoctor(Treatment treatment, Integer doctorId);
    boolean isAppointment(Integer patientAccountId);
    List<Treatment> findByStatus();
    Category getCategoryById(int id);
    List<Treatment> getAllByDoctorAndStatus(Account account);

}
