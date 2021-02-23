package org.example.service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.*;

public class TestUtil {
    public TestUtil(){}

    public Integer createTestPatientAccount(Connection con) throws SQLException {
        Integer patientID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO account (login,password,role) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "testPatient");
        p.setString(2, "testPatient");
        p.setString(3, "patient");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            patientID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return patientID;
    }

    public Integer createTestPatientAccount2(Connection con) throws SQLException {
        Integer patientID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO account (login,password,role) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "testPatient2");
        p.setString(2, "testPatient2");
        p.setString(3, "patient");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            patientID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return patientID;
    }

    public Integer createTestDoctorAccount(Connection con) throws SQLException {
        Integer doctorID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO account (login,password,role) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "testDoctor");
        p.setString(2, "testDoctor");
        p.setString(3, "doctor");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            doctorID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return doctorID;
    }

    public Integer createTestCategory(Connection con) throws SQLException {
        Integer categoryID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO category (name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "test");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            categoryID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return categoryID;
    }

    public void createTestDoctor(Connection con, Integer doctorID, Integer categoryID) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO doctor (account_id,name,category_id) VALUES(?,?,?)");
        p.setInt(1, doctorID);
        p.setString(2, "testDoctor");
        p.setInt(3, categoryID);
        p.executeUpdate();
        p.close();
    }

    public Integer createTestNurseAccount(Connection con) throws SQLException {
        Integer nurseID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO account (login,password,role) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "testNurse");
        p.setString(2, "testNurse");
        p.setString(3, "nurse");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            nurseID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return nurseID;
    }

    public void createTestNurse(Connection con,Integer nurseID) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO doctor (account_id,name,category_id) VALUES(?,?,?)");
        p.setInt(1, nurseID);
        p.setString(2, "testNurse");
        p.setInt(3, 5);
        p.executeUpdate();
        p.close();
    }

    public void createTestPatient(Connection con,Integer patientID) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO patient (account_id,name,age) VALUES(?,?,?)");
        p.setInt(1, patientID);
        p.setString(2, "testPatient");
        p.setInt(3, 27);
        p.executeUpdate();
        p.close();
    }

    public Integer createTestTreatment(Connection con,Integer patientID,Integer doctorID,Integer categoryID) throws SQLException {
        Integer treatmentID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO treatment (patient_account_id,doctor_account_id,category_id,treatment_status) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, patientID);
        p.setInt(2, doctorID);
        p.setInt(3, categoryID);
        p.setString(4, "treatment");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            treatmentID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return treatmentID;
    }


    public Integer createTestTreatmentRegistration(Connection con,Integer patientID,Integer doctorID,Integer categoryID) throws SQLException {
        Integer treatmentID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO treatment (patient_account_id,doctor_account_id,category_id,treatment_status) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setInt(1, patientID);
        p.setInt(2, doctorID);
        p.setInt(3, categoryID);
        p.setString(4, "registration");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            treatmentID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return treatmentID;
    }

    public Integer createTestProcedure(Connection con,Integer treatmentID,Integer doctorID) throws SQLException {
        Integer procedureID = null;
        PreparedStatement p = con.prepareStatement("INSERT INTO procedures (name,treatment_id,doctor_account_id,type,procedure_status) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        p.setString(1, "testProcedure");
        p.setInt(2, treatmentID);
        p.setInt(3, doctorID);
        p.setString(4, "operation");
        p.setString(5, "treatment");
        p.executeUpdate();
        ResultSet resultSet = p.getGeneratedKeys();
        if (resultSet.next()) {
            procedureID = resultSet.getInt(1);
        }
        resultSet.close();
        p.close();
        return procedureID;
    }
    public void deleteProcedure(Connection con,Integer procedureID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM procedures WHERE id = ?");
        p.setInt(1, procedureID);
        p.executeUpdate();
        p.close();
    }

    public void deleteTreatment(Connection con,Integer treatmentID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM treatment WHERE id = ?");
        p.setInt(1, treatmentID);
        p.executeUpdate();
        p.close();
    }

    public void deleteDoctor(Connection con, Integer doctorID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM doctor WHERE account_id = ?");
        p.setInt(1, doctorID);
        p.executeUpdate();
        p.close();
    }
    public void deletePatient(Connection con, Integer patientID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM patient WHERE account_id = ?");
        p.setInt(1, patientID);
        p.executeUpdate();
        p.close();
    }

    public void deleteNurse(Connection con, Integer nurseID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM doctor WHERE account_id = ?");
        p.setInt(1, nurseID);
        p.executeUpdate();
        p.close();
    }

    public void deletePatientAccount(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM account WHERE login = ?");
        p.setString(1, "testPatient");
        p.executeUpdate();
        p.close();
    }

    public void deletePatientAccount2(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM account WHERE login = ?");
        p.setString(1, "testPatient2");
        p.executeUpdate();
        p.close();
    }

    public void deleteNurseAccount(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM account WHERE login = ?");
        p.setString(1, "testNurse");
        p.executeUpdate();
        p.close();
    }

    public void deleteDoctorAccount(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM account WHERE login = ?");
        p.setString(1, "testDoctor");
        p.executeUpdate();
        p.close();
    }

    public void deleteCategory(Connection con,Integer categoryID) throws SQLException {
        PreparedStatement p = con.prepareStatement("DELETE FROM category WHERE id = ?");
        p.setInt(1, categoryID);
        p.executeUpdate();
        p.close();
    }
}
