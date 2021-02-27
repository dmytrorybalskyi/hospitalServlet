package org.example.service;

import org.example.model.dao.ConnectionPoolHolder;
import org.example.model.entity.Category;
import org.example.model.entity.Doctor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;


public class DoctorServiceTest {
    public static int id;
    public static int categoryID;
    public static int doctorID;
    public static final String NURSE_LOGIN_PASSWORD_NAME = "nurseTest";

    DoctorService doctorService = new DoctorService();
    AccountService accountService = new AccountService();

    @BeforeClass
    public static void beforeTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        id = testUtil.createTestDoctorAccount(connection);
        categoryID = testUtil.createTestCategory(connection);
        testUtil.createTestDoctor(connection,id,categoryID);
        connection.close();
    }

    @Test
    public void addDoctor() throws SQLException {
        doctorID = doctorService.addDoctor(NURSE_LOGIN_PASSWORD_NAME, NURSE_LOGIN_PASSWORD_NAME, NURSE_LOGIN_PASSWORD_NAME, 5).getAccount().getId();
        int expected = accountService.findByLogin(NURSE_LOGIN_PASSWORD_NAME).getId();
        assertEquals(expected, doctorID);
    }

    @Test
    public void findByCategory() {
        List<Doctor> doctorList = doctorService.findByCategory(new Category(categoryID));
        Doctor doctor = doctorList.get(0);
        String expected = "testDoctor";
        String actual = doctor.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void findByCategoryAndNurse() {
        List<Doctor> doctorList1 = doctorService.getAllByCategoryAndNurse(categoryID);
        List<Doctor> doctorList2 = doctorService.findByCategory(new Category(categoryID));
        assertTrue(doctorList1.size()>doctorList2.size());
    }

    @AfterClass
    public static void afterTest() throws SQLException {
        Connection connection = ConnectionPoolHolder.getConnection();
        TestUtil testUtil = new TestUtil();
        testUtil.deleteDoctor(connection,id);
        testUtil.deleteDoctor(connection,doctorID);
        testUtil.deleteDoctorAccount(connection);

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE login = ?");
        preparedStatement.setString(1, NURSE_LOGIN_PASSWORD_NAME);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        testUtil.deleteCategory(connection,categoryID);
        connection.close();

    }
}
