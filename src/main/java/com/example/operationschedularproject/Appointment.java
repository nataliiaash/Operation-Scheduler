package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.util.Date;

public class Appointment {

    public Date date;
    public Date start_time;
    public Date end_time;

    private AppointmentType treatmentType;
    //edited
    private LinkedList<HealthProfessional> listOfHealthProfessionalsInvolved;

    public Appointment(){

    }
    //added
    public Appointment(Date date, Date start_time, Date end_time, AppointmentType treatmentType) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.treatmentType = treatmentType;
        this.listOfHealthProfessionalsInvolved = new LinkedList<>();
    }
    //added
    public void add(HealthProfessional healthProfessional){
          this.listOfHealthProfessionalsInvolved.addFirst(healthProfessional);
    }
    public void delete(){

    }
    public void edit(){

    }
    //added
    public LinkedList<HealthProfessional> getListOfHealthProfessionalsInvolved() {
        return listOfHealthProfessionalsInvolved;
    }
}
