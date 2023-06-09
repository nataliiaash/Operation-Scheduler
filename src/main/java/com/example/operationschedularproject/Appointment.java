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

    /**
     * constructor for appointment
     * @param patient
     * @param treatmentType
     * @param date
     * @param start_time
     * @param end_time
     */

    public Appointment(String patient, String treatmentType, String date, String start_time, String end_time) {
        this.patient = patient;
        this.date = date;
        this.treatmentType = treatmentType;
        this.startTime = start_time;
        this.endTime = end_time;
        this.HealthProfessionalsInvolved = new LinkedList<>();
    }

    /**
     * accessor
     * @return
     */
    public String getDate() {
        return date;
    }
    /**
     *mutator
     * @return
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     *mutator
     * @return
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     *mutator
     * @return
     */
    public void setStartTime(String start_time) {
        this.startTime = start_time;
    }
    /**
     *accessor
     * @return
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     *mutator
     * @return
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /**
     *accessor
     * @return
     */
    public String getTreatmentType() {
        return treatmentType;
    }
    /**
     *mutator
     * @return
     */
    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

}
