package com.example.operationschedularproject;
import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.Serializable;

public class Diary implements Serializable {
    LinkedList<Appointment> appointments = new LinkedList<>();
    public void add(Appointment appointment){
        appointments.addLast(appointment);
    }
    public void delete(Appointment appointment){
        try {
            appointments.remove(appointments.find(appointment));
        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
    public void edit(Appointment newAppointment, Appointment prevAppointment){
        try {
            appointments.remove(appointments.find(prevAppointment));
            appointments.addLast(newAppointment);
        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
}
