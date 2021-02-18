package org.example.model.entity;

public class Procedures {
    private int id;
    private Treatment treatment;
    private Patient patient;
    private Doctor doctor;
    private String name;
    private Status status;
    private Types type;

    public  Procedures(){}

    public Procedures(String name){
        this.name = name;
    }

    public Procedures(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }


}
