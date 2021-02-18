package org.example.model.entity;

import java.util.LinkedList;
import java.util.List;

public class Treatment {
    private Integer id;
    private Patient patient;
    private Category category;
    private Doctor doctor;
    private String diagnosis;
    private Status status;
    private List<Procedures> proceduresList = new LinkedList<>();

    public Treatment(){}

    public Treatment(int id){
        this.id = id;
    }

    public Treatment(Patient patient,Category category,Status status){
        this.patient=patient;
        this.category=category;
        this.status=status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", patient=" + patient +
                ", category=" + category +
                ", doctor=" + doctor +
                ", diagnosis='" + diagnosis + '\'' +
                ", status=" + status +
                ", proceduresList=" + proceduresList +
                '}';
    }
}
