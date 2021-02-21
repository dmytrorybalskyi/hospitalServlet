package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assume.*;

public class AccountServiceTest {
    private AccountService accountService = new AccountService();

    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.createTestPatientAccount(connection);
        connection.close();
    }

    @Test
    public void findByLogin() {
        String expected = "testPatient";
        assumeNotNull(accountService.findByLogin("testPatient").getLogin());
        String actual = accountService.findByLogin("testPatient").getLogin();
        assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void findByLoginWrongLogin() {
        String expected = "admin100500";
        String actual = accountService.findByLogin(expected).getLogin();
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void findByLoginNull() {
        String actual = accountService.findByLogin(null).getLogin();
        assertEquals(null, actual.isEmpty());
    }

    @AfterClass
    public static void afterTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.deletePatientAccount(connection);
        connection.close();
    }

}
