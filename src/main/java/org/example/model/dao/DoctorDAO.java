package org.example.model.dao;

import org.example.model.entity.Category;
import org.example.model.entity.Doctor;

import java.util.List;

public interface DoctorDAO extends GenericDAO<Doctor>{
    List<Doctor> findByCategory(Category category);
    List<Doctor> getAllByCategoryAndNurse(Integer categoryId);
    boolean changePatientNumber(Integer doctor_account_id, int patientNumber);
}
