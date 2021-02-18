package org.example.model.dao;

import org.example.model.entity.Patient;

public interface PatientDAO extends GenericDAO<Patient>{
    boolean removeDoctor(Patient patient);
}
