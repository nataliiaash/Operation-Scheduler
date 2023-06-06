package com.example.operationschedularproject;
import java.time.LocalTime;
import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.util.Date;

public class Diary {
    LinkedList<Appointment> appointments = new LinkedList<>();
    public void add(AppointmentType type, Date date, LocalTime start_time, LocalTime end_time){
        Appointment a = new Appointment(type, date, start_time, end_time );
        appointments.addLast(a);
    }
    public void delete(Appointment ap){
        try {
            appointments.remove(appointments.find(ap));
        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
    public void edit(Appointment ap, AppointmentType newType, Date newDate, LocalTime newStartTime, LocalTime newEndTime){
        try {
            appointments.remove(appointments.find(ap));
            Appointment newAppointment = new Appointment(newType, newDate, newStartTime, newEndTime);
            appointments.addLast(newAppointment);

        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
}
