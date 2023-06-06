package com.example.operationschedularproject;

public class UndoAction {
    private final ActionType actionType;
    private final Appointment appointment;

    public UndoAction(ActionType actionType, Appointment appointment) {
        this.actionType = actionType;
        this.appointment = appointment;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}