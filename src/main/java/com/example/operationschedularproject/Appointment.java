package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.Serializable;

public class Appointment implements Serializable {

    public String date;
    public String patient;
    public String startTime;
    public String endTime;
    private String treatmentType;

    private LinkedList<HealthProfessional> HealthProfessionalsInvolved;

    public Appointment(){

    }

    public Appointment(String patient, String treatmentType, String date, String start_time, String end_time) {
        this.patient = patient;
        this.date = date;
        this.treatmentType = treatmentType;
        this.startTime = start_time;
        this.endTime = end_time;
        this.HealthProfessionalsInvolved = new LinkedList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String start_time) {
        this.startTime = start_time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

}
