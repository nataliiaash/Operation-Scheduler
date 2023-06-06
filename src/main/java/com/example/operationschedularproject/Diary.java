package com.example.operationschedularproject;
import java.time.LocalTime;
import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.util.Date;

public class Diary {
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
    public void edit(Appointment appointment){
        try {
            appointments.remove(appointments.find(appointment));
            appointments.addLast(appointment);
        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
}
