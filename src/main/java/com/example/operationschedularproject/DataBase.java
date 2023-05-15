package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.IOException;
import java.io.Serializable;

public class DataBase implements Serializable {
    private LinkedList<HealthProfessional> healthProfessionalDB = new LinkedList<>();
    private Saver saver = new Saver();
    public void add(HealthProfessional healthProfessional) throws IOException {
        healthProfessionalDB.addFirst(healthProfessional);
        saver.save("Database.ser",healthProfessionalDB);
    }
    public void delete(HealthProfessional healthProfessional) throws EmptyException, IOException {
        int index = healthProfessionalDB.find(healthProfessional);
        healthProfessionalDB.remove(index);
        saver.save("Database.ser",healthProfessionalDB);
    }

    public LinkedList<HealthProfessional> getHealthProfessionalDB() {
        return healthProfessionalDB;
    }

    public void setHealthProfessionalDB(LinkedList<HealthProfessional> healthProfessionalDB) {
        this.healthProfessionalDB = healthProfessionalDB;
    }
}
