package com.example.operationschedularproject;
public class Patient extends Person{

    private AppointmentType appointmentType;
    public Patient(){

    }

    public Patient(String name, AppointmentType appointmentType) {
        super(name);
        this.appointmentType = appointmentType;
    }
}

