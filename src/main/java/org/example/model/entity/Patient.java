package org.example.model.entity;

public class Patient {
    private int id;
    private Account account;
    private String name;
    private int age;

   public Patient(String name,int age){
       this.name=name;
       this.age=age;
   }

   public Patient(String name,int age, int id){
       this.name=name;
       this.age=age;
       this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
