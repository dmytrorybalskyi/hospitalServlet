package org.example.model.entity;

import java.util.List;

public class Doctor {
    private Integer id;
    private Account account;
    private String name;
    private Category category;
    private int patientNumber;
    private List<Treatment> treatmentLint;
    private List<Procedures> proceduresList;

    public Doctor(String name, Category category){
        this.name=name;
        this.category = category;
    }

    public Doctor(Integer id){
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
