package com.example.operationschedularproject;

public class UndoAction {
    private final ActionType actionType;
    private final Object object;

    public UndoAction(ActionType actionType, Object object) {
        this.actionType = actionType;
        this.object = object;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Object getObject() {
        return object;
    }
}