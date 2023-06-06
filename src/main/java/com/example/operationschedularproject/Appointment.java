package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.time.LocalTime;
import java.util.Date;

public class Appointment {

    public Date date;
    public LocalTime start_time;
    public LocalTime end_time;

    private AppointmentType treatmentType;

    private LinkedList<HealthProfessional> HealthProfessionalsInvolved;

    public Appointment(){

    }

    public Appointment(AppointmentType treatmentType, Date date, LocalTime start_time, LocalTime end_time) {
        this.date = date;
        this.treatmentType = treatmentType;
        this.start_time = start_time;
        this.end_time = end_time;
        this.HealthProfessionalsInvolved = new LinkedList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public AppointmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(AppointmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public void add(){
    }
    public void delete(){

    }
    public void edit(){

    }


}
