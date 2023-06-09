package com.example.operationschedularproject;

public class UndoAction {
    private final ActionType actionType;
    private final Object object;

    /**
     * constructor
     * @param actionType
     * @param object
     */
    public UndoAction(ActionType actionType, Object object) {
        this.actionType = actionType;
        this.object = object;
    }

    /**
     * accessor
     * @return
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * mutator
     * @return
     */
    public Object getObject() {
        return object;
    }
}