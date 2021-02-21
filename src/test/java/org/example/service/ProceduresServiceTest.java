package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.entity.Procedures;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class ProceduresServiceTest {
    private static Integer doctorID;
    private static Integer patientID;
    private static Integer treatmentID;
    private static Integer categoryID;
    private static Integer procedureID;
    private static Integer procedureID2;
    private static Integer nurseID;
    private ProceduresService proceduresService = new ProceduresService();

    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        patientID = testUtil.createTestPatientAccount(connection);
        nurseID = testUtil.createTestNurseAccount(connection);
        categoryID = testUtil.createTestCategory(connection);
        doctorID = testUtil.createTestDoctorAccount(connection);
        testUtil.createTestNurse(connection, nurseID);
        testUtil.createTestDoctor(connection, doctorID, categoryID);
        testUtil.createTestPatient(connection, patientID);
        treatmentID = testUtil.createTestTreatment(connection, patientID, doctorID, categoryID);
        procedureID = testUtil.createTestProcedure(connection, treatmentID, doctorID);
        connection.close();
    }

    @Test
    public void addProcedures() throws SQLException {
        Procedures procedures = proceduresService.addProcedures("testProcedure", doctorID, treatmentID, "operation");
        procedureID2 = procedures.getId();
        assertTrue(procedureID2 != null);
        assertTrue(procedureID >= 0);
        assertEquals("treatment", procedures.getStatus().name());
    }

    @Test(expected = SQLException.class)
    public void addProceduresByNurseAndOperation() throws SQLException {
        proceduresService.addProcedures("testProcedure", nurseID, treatmentID, "operation");
    }


    @Test
    public void findAllByDoctor() {
        List<Procedures> proceduresList = proceduresService.findAllByDoctor(doctorID);
        String expected = "testProcedure";
        String actual = proceduresList.get(0).getName();
        assertTrue(proceduresList.size() >= 1);
        assertEquals(expected, actual);
    }

    @Test
    public void doProcedures() {
        boolean actual = proceduresService.doProcedures(procedureID);
        assertTrue(actual);
    }


    @AfterClass
    public static void afterTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.deleteProcedure(connection, procedureID);
        if (procedureID2 != null) {
            testUtil.deleteProcedure(connection, procedureID2);
        }
        testUtil.deleteTreatment(connection, treatmentID);
        testUtil.deleteDoctor(connection, doctorID);
        testUtil.deletePatient(connection, patientID);
        testUtil.deleteNurse(connection, nurseID);
        testUtil.deleteNurseAccount(connection);
        testUtil.deleteDoctorAccount(connection);
        testUtil.deletePatientAccount(connection);
        testUtil.deleteCategory(connection, categoryID);
        connection.close();

    }

}
