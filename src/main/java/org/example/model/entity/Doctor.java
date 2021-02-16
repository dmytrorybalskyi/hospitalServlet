package org.example.model.entity;

import java.util.List;

public class Doctor {
    private Account account;
    private String name;
    private Integer categoryId;
    private int patientNumber;
    private List<Treatment> treatmentLint;
    private List<Procedures> proceduresList;

    public Doctor(String name, Integer categoryId){
        this.name=name;
        this.categoryId=categoryId;
    }

    public Doctor(Account account){
        this.account = account;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(int patientNumber) {
        this.patientNumber = patientNumber;
    }

    public List<Treatment> getTreatmentLint() {
        return treatmentLint;
    }

    public void setTreatmentLint(List<Treatment> treatmentLint) {
        this.treatmentLint = treatmentLint;
    }

    public List<Procedures> getProceduresList() {
        return proceduresList;
    }

    public void setProceduresList(List<Procedures> proceduresList) {
        this.proceduresList = proceduresList;
    }
}
