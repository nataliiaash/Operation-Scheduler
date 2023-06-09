package com.example.operationschedularproject;

import java.io.Serializable;

public class Machine implements Serializable {
    private String name;
    private String date;
    private String startTime;
    private String endTime;

    public Machine() {
    }

    /**
     * constructor
     * @param name
     * @param date
     * @param startTime
     * @param endTime
     */
    public Machine(String name, String date, String startTime, String endTime) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * accessor
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * mutator
     * @param endTime
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * accessor
     * @return
     */
    public String getDate() {
        return date;
    }
    /**
     * mutator
     * @param endTime
     */
    public void setDate(String date) {
        this.date = date;
    }
    /**
     * accessor
     * @return
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * mutator
     * @param endTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /**
     * accessor
     * @return
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * mutator
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
