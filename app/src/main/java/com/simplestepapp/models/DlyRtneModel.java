package com.simplestepapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 2/15/2019.
 */

public class DlyRtneModel implements Serializable {

    private String date;

    private String activityId;

    private ArrayList<TimeSlot> timeSlotOption;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public ArrayList<TimeSlot> getTimeSlotOption() {
        return timeSlotOption;
    }

    public void setTimeSlotOption(ArrayList<TimeSlot> timeSlotOption) {
        this.timeSlotOption = timeSlotOption;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", activityId = " + activityId + ", timeSlotOption = " + timeSlotOption + "]";
    }
}

