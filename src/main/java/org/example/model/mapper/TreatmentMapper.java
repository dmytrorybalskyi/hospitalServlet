package org.example.model.mapper;

import org.example.model.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TreatmentMapper implements ObjectMapper<Treatment> {
    @Override
    public Treatment extractFromResultSet(ResultSet rs) throws SQLException {
        Treatment treatment = null;
        if (rs.next()) {
            treatment = new Treatment(rs.getInt("treatment.id"));
            treatment.setDiagnosis(rs.getString("diagnosis"));
            Doctor doctor = new Doctor(rs.getString("doctor.name"),
                    new Category(rs.getInt("category.id"), rs.getString("category.name")));
            treatment.setDoctor(doctor);
            if(rs.getString("procedures.name")!=null) {
                Procedures procedures = new Procedures(rs.getString("procedures.name"));
                procedures.setStatus(Status.valueOf(rs.getString("procedure_status")));
                procedures.setType(Types.valueOf(rs.getString("procedures.type")));
                treatment.getProceduresList().add(procedures);
                while (rs.next()) {
                    Procedures procedures1 = new Procedures(rs.getString("procedures.name"));
                    procedures1.setStatus(Status.valueOf(rs.getString("procedure_status")));
                    procedures1.setType(Types.valueOf(rs.getString("procedures.type")));
                    treatment.getProceduresList().add(procedures1);
                }
            }
        }
        return treatment;
    }

    @Override
    public Treatment makeUnique(Map cache, Treatment treatment) {
        return null;
    }

    public List<Treatment> AllTreatmentFromResultSet(ResultSet rs) throws SQLException {
        List<Treatment> treatmentList = new LinkedList<>();
        Treatment treatment = new Treatment(0);
        while (rs.next()) {
            if (rs.getInt("treatment.id") != treatment.getId()) {
                treatmentList.add(treatment);
                treatment = new Treatment(rs.getInt("treatment.id"));
                treatment.setDiagnosis(rs.getString("diagnosis"));
                treatment.setPatient(new Patient(
                        rs.getString("name"),
                        rs.getInt("age"),
                        new Account(rs.getInt("account_id"))));
            }
            if (rs.getString("procedures.name") != null) {
                Procedures procedures = new Procedures(rs.getInt("procedures.id"));
                procedures.setName(rs.getString("procedures.name"));
                procedures.setType(Types.valueOf(rs.getString("type")));
                procedures.setStatus(Status.valueOf(rs.getString("procedures.procedure_status")));
                treatment.getProceduresList().add(procedures);
            }
        }
        treatmentList.add(treatment);
        treatmentList.remove(0);
        return treatmentList;
    }
}
