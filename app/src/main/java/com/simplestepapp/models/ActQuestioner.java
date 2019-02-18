package com.simplestepapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 2/16/2019.
 */

public class ActQuestioner implements Serializable {

    private String _id;

    private String timeSlotCaption;

    private String activity;

    private String isEnabled;

    private ArrayList<TimeSlots> timeSlots;


    public ArrayList<TimeSlots> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<TimeSlots> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public String getTimeSlotCaption() {
        return timeSlotCaption;
    }

    public void setTimeSlotCaption(String timeSlotCaption) {
        this.timeSlotCaption = timeSlotCaption;
    }

    public String getQuestion() {
        return activity;
    }

    public void setQuestion(String activity) {
        this.activity = activity;
    }


    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return "ClassPojo _id = " + _id + ", timeSlotCaption = " + timeSlotCaption + ", question = " + activity + ",  isEnabled = " + isEnabled + ", timeSlots = " + timeSlots + "]";
    }
}
