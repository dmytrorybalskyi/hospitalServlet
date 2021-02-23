package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.entity.Account;
import org.example.model.entity.Page;
import org.example.model.entity.Treatment;
import org.junit.*;
import java.sql.*;
import java.util.Deque;


import static org.junit.Assert.*;

public class TreatmentServiceTest {
    private static Integer doctorID;
    private static Integer patientID;
    private static Integer patientID2;
    private static Integer treatmentID2;
    private static Integer treatmentID;
    private static Integer categoryID;
    private static Integer procedureID;
    private TreatmentService treatmentService = new TreatmentService();


    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        patientID = testUtil.createTestPatientAccount(connection);
        patientID2 = testUtil.createTestPatientAccount2(connection);
        categoryID = testUtil.createTestCategory(connection);
        doctorID = testUtil.createTestDoctorAccount(connection);
        testUtil.createTestDoctor(connection, doctorID, categoryID);
        testUtil.createTestPatient(connection, patientID);
        testUtil.createTestPatient(connection, patientID2);
        treatmentID2 = testUtil.createTestTreatmentRegistration(connection, patientID2, doctorID, categoryID);
        treatmentID = testUtil.createTestTreatment(connection, patientID, doctorID, categoryID);
        procedureID = testUtil.createTestProcedure(connection, treatmentID, doctorID);
        connection.close();
    }

    @Test
    public void discharge() throws SQLException {
        boolean discharge = treatmentService.discharge(doctorID, patientID, treatmentID);
        assertTrue(discharge);
        assertEquals("done", treatmentService.findById(treatmentID).getStatus().name());
    }

    @Test
    public void findById() {
        String expectedPatientName = "testPatient";
        String actualPatientName = treatmentService.findById(treatmentID).getPatient().getName();
        assertEquals(expectedPatientName, actualPatientName);
    }

    @Test(expected = SQLException.class)
    public void setDoctor() throws SQLException {
        Treatment treatment = treatmentService.findById(treatmentID);
        assertTrue(treatmentService.setDoctor(treatment, doctorID));
    }

    @Test(expected = SQLException.class)
    public void createTreatmentOneMoreTime() throws SQLException {
        treatmentService.createTreatment(new Account(patientID), categoryID);
    }

    @Test
    public void getAllByDoctorAndStatus() {
        Deque<Treatment> treatmentDeque = treatmentService.getAllByDoctorAndStatus(new Account(doctorID));
        assertEquals(1, treatmentDeque.size());
        assertEquals(1, treatmentDeque.getFirst().getProceduresList().size());
        assertEquals("testPatient", treatmentDeque.getFirst().getPatient().getName());
    }

    @Test
    public void setDiagnosis() {
        String expected = "caries";
        boolean setDiagnosis = treatmentService.setDiagnosis(expected, treatmentID);
        assertTrue(setDiagnosis);
        assertEquals(expected, treatmentService.findById(treatmentID).getDiagnosis());
    }

    @Test
    public void getPageByStatus() {
        Page<Treatment> page = treatmentService.getPageByStatus(new Page(0));
        assertEquals(1, page.getList().size());
        assertEquals("registration",page.getList().get(0).getStatus().name());
    }

    @Test
    public void getTreatmentByPatient() {
        Treatment actual = treatmentService.getTreatmentByPatient(new Account(patientID));
        Treatment expected = treatmentService.findById(treatmentID);
        assertEquals(expected.getId(), actual.getId());
    }

    @AfterClass
    public static void afterTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.deleteProcedure(connection, procedureID);
        testUtil.deleteTreatment(connection, treatmentID);
        testUtil.deleteTreatment(connection, treatmentID2);
        testUtil.deletePatient(connection, patientID);
        testUtil.deletePatient(connection, patientID2);
        testUtil.deleteDoctor(connection, doctorID);
        testUtil.deleteDoctorAccount(connection);
        testUtil.deletePatientAccount(connection);
        testUtil.deletePatientAccount2(connection);
        testUtil.deleteCategory(connection, categoryID);
        connection.close();

    }
}
