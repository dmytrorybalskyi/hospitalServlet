package org.example.model.mapper;

import org.example.model.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TreatmentMapper implements ObjectMapper<Treatment> {

    @Override
    public Treatment extractFromResultSet(ResultSet rs) throws SQLException {
        Treatment treatment = new Treatment(rs.getInt("treatment.id"));
        treatment.setDiagnosis(rs.getString("diagnosis"));
        Doctor doctor = new Doctor(rs.getString("doctor.name"),
                new Category(rs.getInt("category.id"), rs.getString("category.name")));
        treatment.setDoctor(doctor);
        return treatment;
    }

    @Override
    public Treatment makeUnique(Map cache, Treatment treatment) {
        return null;
    }

    public Deque<Treatment> treatmentWithPatient(ResultSet rs, Deque<Treatment> deque) throws SQLException {
        if (deque.peek()==null||deque.getFirst().getId() != rs.getInt("treatment.id")) {
            Treatment treatment = new Treatment(rs.getInt("treatment.id"));
            treatment.setDiagnosis(rs.getString("diagnosis"));
            treatment.setPatient(new Patient(rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("account_id")));
            deque.addFirst(treatment);
        }
        return deque;
    }

}
