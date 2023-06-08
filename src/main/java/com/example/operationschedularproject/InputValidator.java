package com.example.operationschedularproject;

import java.util.regex.Pattern;

public class InputValidator {
    private static String timeRegex = "^([01]?\\d|2[0-3]):([0-5]?\\d)$";

    public static boolean timeValidation(String time) {
        return Pattern.matches(timeRegex, time);
    }

    public static boolean isAddAppointmentValid(HealthProfessional healthProfessional, Appointment appointment) {
        for (int i = 0; i < healthProfessional.diary.appointments.size(); i++) {
            Appointment appointment1 = healthProfessional.diary.appointments.get(i).value;
            if (appointment1.getDate().equals(appointment.getDate())) {
                if (appointment1.getStartTime().equals(appointment.getStartTime()) || appointment1.getEndTime().equals(appointment.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isAddMachineValid(HealthProfessional healthProfessional, Machine machine) {
        for (int i = 0; i < healthProfessional.machineBooker.getMachineLinkedList().size(); i++) {
            Machine machine1 = healthProfessional.machineBooker.getMachineLinkedList().get(i).value;
            if (machine1.getDate().equals(machine.getDate())) {
                if (machine1.getStartTime().equals(machine.getStartTime()) || machine1.getEndTime().equals(machine.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }
}

