package org.example.model.dao;

import org.example.model.entity.Procedures;
import java.util.List;

public interface ProceduresDAO extends GenericDAO<Procedures>{
    List<Procedures> findALlByDoctor(Integer id);
    boolean doProcedures(Integer id);
}
