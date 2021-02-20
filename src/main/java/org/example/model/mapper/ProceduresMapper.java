package org.example.model.mapper;

import org.example.model.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProceduresMapper implements ObjectMapper<Procedures> {
    @Override
    public Procedures extractFromResultSet(ResultSet rs) throws SQLException {
        if (rs.getString("procedure_status") == null) {
            return null;
        }
        Procedures procedures = new Procedures(rs.getString("procedures.name"));
        procedures.setId(rs.getInt("id"));
        procedures.setStatus(Status.valueOf(rs.getString("procedure_status")));
        procedures.setType(Types.valueOf(rs.getString("type")));
        return procedures;

    }

    public Procedures extractFromResultSetWihPatient(ResultSet rs) throws SQLException {
        if (rs.getString("procedure_status") == null) {
            return null;
        }
        Procedures procedures = new Procedures(rs.getString("procedures.name"));
        procedures.setId(rs.getInt("procedures.id"));
        procedures.setStatus(Status.valueOf(rs.getString("procedure_status")));
        procedures.setType(Types.valueOf(rs.getString("type")));
        procedures.setTreatment(new Treatment(rs.getInt("treatment.id")));
        procedures.getTreatment().setPatient(new Patient(rs.getString("patient.name"),rs.getInt("patient.age")));
        return procedures;
    }

    @Override
    public Procedures makeUnique(Map<Integer, Procedures> cache, Procedures teacher) {
        return null;
    }

}
