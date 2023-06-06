package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.time.LocalTime;
import java.time.LocalDate;

public class Appointment {

    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;

    private AppointmentType treatmentType;

    private LinkedList<HealthProfessional> HealthProfessionalsInvolved;

    public Appointment(){

    }

    public Appointment(AppointmentType treatmentType, LocalDate date, LocalTime start_time, LocalTime end_time) {
        this.date = date;
        this.treatmentType = treatmentType;
        this.start_time = start_time;
        this.end_time = end_time;
        this.HealthProfessionalsInvolved = new LinkedList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    @Override
    public String toString() {
        return "Appointment{" +
                "date=" + date +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", treatmentType=" + treatmentType +
                ", HealthProfessionalsInvolved=" + HealthProfessionalsInvolved +
                '}';
    }
}
