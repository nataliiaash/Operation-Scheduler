package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.LinkedList;
import com.example.operationschedularproject.LinkedList.EmptyException;
import java.util.Date;
import java.io.Serializable;
import java.util.EmptyStackException;

public class HealthProfessional extends Person implements Serializable{
    private String profession, location, username, password;
    private boolean isAvailable;

    public Diary diary = new Diary();
    public TaskList taskList = new TaskList();
    public MachineBooker machineBooker = new MachineBooker();

    public HealthProfessional(){

    }

    /**
     * constructor
     * @param name
     * @param profession
     * @param username
     * @param password
     */

    public HealthProfessional(String name, String profession, String username, String password) {
        super(name);
        this.profession = profession;
        this.username = username;
        this.password = password;
    }

    /**
     * accessor
     * @return
     */
    public String getProfession() {
        return profession;
    }
    /**
     * mutator
     * @param password
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }
    /**
     * accessor
     * @return
     */
    public String getLocation() {
        return location;
    }
    /**
     * mutator
     * @param password
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * accessor
     * @return
     */
    public String getUsername() {
        return username;
    }
    /**
     * mutator
     * @param password
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * accessor
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * mutator
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}