package org.example.service;

import org.example.model.dao.DAOFactory;
import org.example.model.dao.PatientDAO;
import org.example.model.entity.Account;
import org.example.model.entity.Patient;
import org.example.model.entity.Roles;

import java.sql.SQLException;

public class PatientService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public Patient addPatient(String login, String password,String name,Integer age) throws SQLException {
        Account account = new Account(login,password);
        account.setRole(Roles.patient);
        Patient patient = new Patient(name,age);
        patient.setAccount(account);
        PatientDAO patientDAO = daoFactory.createPatientDAO();
        return patientDAO.create(patient);
    }

}
