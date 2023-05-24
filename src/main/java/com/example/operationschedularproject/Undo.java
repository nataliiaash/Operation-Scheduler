package com.example.operationschedularproject;

import java.util.Stack;

public class Undo {
    private Stack<Booking> bookingStack;
    private Booking currentBooking;

    public Undo() {
        bookingStack = new Stack<>();
        currentBooking = null;
    }

    public void createBooking(String patientName, String doctorName) {
        currentBooking = new Booking(patientName, doctorName);
        currentBooking.makeBooking();
        System.out.println("Booking created: " + patientName + " - " + doctorName);
    }

    public void confirmBooking() {
        if (currentBooking != null) {
            bookingStack.push(currentBooking);
            currentBooking = null;
            System.out.println("Booking confirmed.");
        }
    }

    public void cancelBooking() {
        if (currentBooking != null) {
            currentBooking.cancelBooking();
            currentBooking = null;
            System.out.println("Booking canceled.");
        } else if (!bookingStack.isEmpty()) {
            Booking lastBooking = bookingStack.pop();
            lastBooking.cancelBooking();
            System.out.println("Booking canceled: " + lastBooking.getPatientName() + " - " + lastBooking.getDoctorName());
        } else {
            System.out.println("No bookings to cancel.");
        }
    }

    private static class Booking {
        private String patientName;
        private String doctorName;


        public Booking(String patientName, String doctorName) {
            this.patientName = patientName;
            this.doctorName = doctorName;
        }

        public void makeBooking() {

        }

        public void cancelBooking() {

        }

        public String getPatientName() {
            return patientName;
        }

        public String getDoctorName() {
            return doctorName;
        }
    }

    public static void main(String[] args) {
        Undo program = new Undo();
        program.createBooking("John Doe", "Dr. Smith");
        program.confirmBooking();
//        program.createBooking("Jane Smith", "Dr. Johnson");
        program.cancelBooking();
//        program.cancelBooking();
    }
}
