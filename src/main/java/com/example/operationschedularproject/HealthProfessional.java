package com.example.operationschedularproject;
public class HealthProfessional extends Person{
    private String profession;

    private String location;

    private boolean isAvailable;

    public HealthProfessional(){

    }


    public HealthProfessional(String name, String location, String profession, boolean isAvailable) {
        super(name);
        this.location = location;
        this.profession = profession;
        this.isAvailable = isAvailable;
    }


    public void add(){

    }
    public void delete(){

    }
    public void edit(){

    }
    public void bookMachine(){

    }
}