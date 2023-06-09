package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;
import java.io.IOException;
import java.io.Serializable;

public class DataBase implements Serializable {
    private LinkedList<HealthProfessional> healthProfessionalDB = new LinkedList<>();

    /**
     * adding to data base
     * @param healthProfessional
     * @throws IOException
     */
    public void add(HealthProfessional healthProfessional) throws IOException {
        healthProfessionalDB.addFirst(healthProfessional);//adding the healthprofessional
        Saver.save("Database.ser", this.healthProfessionalDB);// saving the changes
    }

    /**
     * deleting from database
     * @param healthProfessional
     * @throws EmptyException
     * @throws IOException
     */
    public void delete(HealthProfessional healthProfessional) throws EmptyException, IOException {
        int index = healthProfessionalDB.find(healthProfessional); //finding the healthprofessional
        healthProfessionalDB.remove(index);//removing the healthprofessional
        Saver.save("Database.ser",this.healthProfessionalDB);//saving changes
    }

    /**
     * accessor
     * @return
     */
    public LinkedList<HealthProfessional> getHealthProfessionalDB() {
        return healthProfessionalDB;
    }

    /**
     * mutaotor
     * @param healthProfessionalDB
     */
    public void setHealthProfessionalDB(LinkedList<HealthProfessional> healthProfessionalDB) {
        this.healthProfessionalDB = healthProfessionalDB;
    }
}
