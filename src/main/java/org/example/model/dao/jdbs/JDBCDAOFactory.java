package org.example.model.dao.jdbs;

import org.example.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDAOFactory extends DAOFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public AccountDAO createAccountDAO() {
        return new JDBCAccountDAO(getConnection());
    }

    @Override
    public PatientDAO createPatientDAO() {
        return new JDBCPatientDAO(getConnection());
    }

    @Override
    public CategoryDAO createCategoryDAO() {
        return new JDBCCategoryDAO(getConnection());
    }

    @Override
    public TreatmentDAO createTreatmentDAO() {
        return new JDBCTreatmentDAO(getConnection());
    }

    @Override
    public DoctorDAO createDoctorDAO() {
        return new JDBCDoctorDAO(getConnection());
    }

    @Override
    public ProceduresDAO createProceduresDAO() {
        return new JDBCProceduresDAO(getConnection());
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
