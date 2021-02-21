package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.junit.AfterClass;
import org.junit.Test;
import java.sql.*;

import static org.junit.Assert.assertEquals;


public class PatientServiceTest {
    public static int id;
    PatientService patientService = new PatientService();
    AccountService accountService = new AccountService();
    public static final String PATIENT_INFO = "testPatient";

    @Test
    public void AddPatient() throws SQLException {
        id = patientService.addPatient(PATIENT_INFO,PATIENT_INFO,PATIENT_INFO,23).getAccount().getId();
        int expected = accountService.findByLogin(PATIENT_INFO).getId();
        assertEquals(expected,id);
    }

    @AfterClass
    public static void afterTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.deletePatient(connection,id);
        testUtil.deletePatientAccount(connection);
        connection.close();
    }

}
