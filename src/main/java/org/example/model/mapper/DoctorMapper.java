package org.example.model.mapper;

import org.example.model.entity.Account;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DoctorMapper implements ObjectMapper<Doctor> {

    @Override
    public Doctor extractFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category(rs.getInt("category_id"));
        category.setName(rs.getString("category.name"));
        Doctor doctor = new Doctor(rs.getString("name"), category);
        doctor.setAccount(new Account(rs.getInt("account_id")));
        return doctor;
    }

    @Override
    public Doctor makeUnique(Map<Integer, Doctor> cache, Doctor teacher) {
        return null;
    }
}
