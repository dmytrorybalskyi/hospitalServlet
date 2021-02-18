package org.example.service;

import org.example.model.dao.DAOFactory;
import org.example.model.dao.DoctorDAO;
import org.example.model.dao.ProceduresDAO;
import org.example.model.entity.*;

import java.sql.SQLException;
import java.util.List;

public class ProceduresService {
    private DAOFactory daoFactory = DAOFactory.getInstance();

    public Procedures addProcedures(String procedureName,Integer doctor_id,
                                    Integer treatment_id,String type) throws SQLException {
        Procedures procedures = new Procedures(procedureName);
        procedures.setDoctor(new Doctor(new Account(doctor_id)));
        procedures.setTreatment(new Treatment(treatment_id));
        procedures.setStatus(Status.treatment);
        procedures.setType(Types.valueOf(type));
        ProceduresDAO proceduresDAO = daoFactory.createProceduresDAO();
        return proceduresDAO.create(procedures);
    }

    public List<Procedures> findAllByDoctor(Integer id){
        ProceduresDAO proceduresDAO = daoFactory.createProceduresDAO();
        return proceduresDAO.findALlByDoctor(id);
    }

    public boolean doProcedures(Integer id){
        ProceduresDAO proceduresDAO = daoFactory.createProceduresDAO();
        return proceduresDAO.doProcedures(id);
    }

}
