package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.dao.DAOFactory;
import org.example.model.dao.DoctorDAO;
import org.example.model.dao.jdbs.JDBCAccountDAO;
import org.example.model.dao.jdbs.JDBCDoctorDAO;
import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;
import org.example.model.entity.Roles;

import java.sql.SQLException;
import java.util.List;

public class DoctorService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public List<Doctor> findByCategory(Category category){
        DoctorDAO doctorDAO = daoFactory.createDoctorDAO();
        return doctorDAO.findByCategory(category);
    }

    public Doctor addDoctor(String login,String password,String name, Integer categoryId) throws SQLException {
        Account account = new Account(login,password);
        account.setRole(Roles.doctor);
        Doctor doctor = new Doctor(name,categoryId);
        doctor.setAccount(account);
        DoctorDAO doctorDAO = daoFactory.createDoctorDAO();
        return doctorDAO.create(doctor);
    }

}
