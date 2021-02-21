package org.example.model.entity;

import java.util.List;

public class Patient {
    private Integer id;
    private Account account;
    private String name;
    private int age;
    private Doctor doctor;
    private List<Procedures> proceduresList;
    private List<Treatment> treatmentList;

    public Patient(Integer id) {
        this.id = id;
    }

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Patient(String name, int age, Integer id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Procedures> getProceduresList() {
        return proceduresList;
    }

    public void setProceduresList(List<Procedures> proceduresList) {
        this.proceduresList = proceduresList;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
