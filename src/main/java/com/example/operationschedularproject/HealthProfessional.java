package com.example.operationschedularproject;

import java.io.Serializable;

public class HealthProfessional extends Person implements Serializable{
    private String profession, location, username, password;
    private boolean isAvailable;

    public HealthProfessional(){

    }

    public HealthProfessional(String name, String profession, String username, String password) {
        super(name);
        this.profession = profession;
        this.username = username;
        this.password = password;
    }


    public void add(){
    }
    public void delete(){

    }
    public void edit(){

    }
    public void bookMachine(){

    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}