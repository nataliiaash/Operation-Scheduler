package com.example.operationschedularproject;
import java.time.LocalTime;
import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;
import com.example.operationschedularproject.LinkedList.Node;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Diary {
    LinkedList<Appointment> appointments = new LinkedList<>();
    public void add(AppointmentType type, LocalDate date, LocalTime start_time, LocalTime end_time){
        Appointment a = new Appointment(type, date, start_time, end_time );
        int i=0;
        while (i<appointments.size()){
            Appointment temp = appointments.get(i).value;
            if (temp.getDate().equals(date)){
                if (!(temp.getStart_time().isAfter(start_time) && temp.getEnd_time().isAfter(end_time)
                        || temp.getStart_time().isBefore(start_time) && temp.getEnd_time().isBefore(end_time))){
                    System.out.println("Doctor is not available at this time");
                    return;
                }
            }
            i++;
        }
        appointments.addLast(a);
    }
    public void delete(AppointmentType type, LocalDate date, LocalTime start_time){
        int i=0;
        while(i<appointments.size()){
            Appointment temp = appointments.get(i).value;
            if (temp.getTreatmentType().equals(type) && temp.getDate().equals(date) && temp.getStart_time().equals(start_time)){
                try {
                    appointments.remove(i);
                    System.out.println("Appointment is successfully deleted.");
                }
                catch (EmptyException e){
                    System.out.println("There is no such appointment.");
                }
                return;
            }
            i++;
        }
        System.out.println("There is no such appointment.");
    }
    public void edit(Appointment ap, AppointmentType newType, LocalDate newDate, LocalTime newStartTime, LocalTime newEndTime){
        try {
            appointments.remove(appointments.find(ap));
            Appointment newAppointment = new Appointment(newType, newDate, newStartTime, newEndTime);
            appointments.addLast(newAppointment);

        }
        catch (EmptyException e){
            System.out.println(e.getMessage());
        }

    }
    public void viewAllAppointments(){
        appointments.printList();
    }

    public static void main(String[] args) {
        Diary diary = new Diary();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        diary.add(AppointmentType.BloodTest, LocalDate.parse("02.05.2023", dateTimeFormatter), LocalTime.parse("10:00"), LocalTime.parse("12:00"));
        diary.add(AppointmentType.BloodTest, LocalDate.parse("02.06.2023", dateTimeFormatter), LocalTime.parse("13:00"), LocalTime.parse("14:00"));
        diary.delete(AppointmentType.BloodTest, LocalDate.parse("02.05.2023", dateTimeFormatter), LocalTime.parse("13:00"));
        System.out.println(diary.appointments.size());
        diary.viewAllAppointments();
    }
}
