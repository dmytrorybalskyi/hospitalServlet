package org.example.model.mapper;

import org.example.model.entity.Account;
import org.example.model.entity.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PatientMapper implements ObjectMapper<Patient>{

    @Override
    public Patient extractFromResultSet(ResultSet rs) throws SQLException {
        Patient patient = new Patient(rs.getString("patient.name"),rs.getInt("patient.age"));
        patient.setAccount(new Account(rs.getInt("account_id")));
        return patient;
    }

    @Override
    public Patient makeUnique(Map cache, Patient patient) {
        return null;
    }
}
