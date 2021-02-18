package org.example.model.mapper;

import org.example.model.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProceduresMapper implements ObjectMapper<Procedures> {
    @Override
    public Procedures extractFromResultSet(ResultSet rs) throws SQLException {
        Procedures procedures = new Procedures(rs.getString("name"));
        procedures.setId(rs.getInt("id"));
        procedures.setStatus(Status.valueOf(rs.getString("procedure_status")));
        procedures.setType(Types.valueOf(rs.getString("type")));
        return procedures;
    }

    @Override
    public Procedures makeUnique(Map<Integer, Procedures> cache, Procedures teacher) {
        return null;
    }

}
