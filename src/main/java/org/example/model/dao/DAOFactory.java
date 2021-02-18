package org.example.model.dao;

import org.example.model.dao.jdbs.JDBCDAOFactory;

public abstract class DAOFactory {
    private static DAOFactory daoFactory;

    public abstract AccountDAO createAccountDAO();
    public abstract PatientDAO createPatientDAO();
    public abstract CategoryDAO createCategoryDAO();
    public abstract TreatmentDAO createTreatmentDAO();
    public abstract DoctorDAO createDoctorDAO();
    public abstract ProceduresDAO createProceduresDAO();


    public static DAOFactory getInstance(){
        if(daoFactory==null){
            synchronized (DAOFactory.class){
                if(daoFactory==null){
                    DAOFactory temp = new JDBCDAOFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
