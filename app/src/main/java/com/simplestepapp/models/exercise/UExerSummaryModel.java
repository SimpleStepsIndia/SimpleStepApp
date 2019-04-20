package com.simplestepapp.models.exercise;

import java.io.Serializable;

public class UExerSummaryModel implements Serializable {
    private String sets;
    private String reps;
    private String rest_Time;
    private String wkt_Time;
    private int exerCount;

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getRest_Time() {
        return rest_Time;
    }

    public void setRest_Time(String rest_Time) {
        this.rest_Time = rest_Time;
    }

    public String getWkt_Time() {
        return wkt_Time;
    }

    public void setWkt_Time(String wkt_Time) {
        this.wkt_Time = wkt_Time;
    }

    public int getExerCount() {
        return exerCount;
    }

    public void setExerCount(int exerCount) {
        this.exerCount = exerCount;
    }
}
