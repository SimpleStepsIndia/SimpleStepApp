package com.simplestepapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 1/5/2019.
 */

public class Questioner implements Serializable {

    private ArrayList<AnswerOptions> answerOptions;

    private ArrayList<WhyOptions> whyOptions;

    private String _id;

    private String description;

    private String answerCaption;

    private String timeSlotCaption;

    private String question;

    private String whyCaption;

    private String isEnabled;

    private ArrayList<TimeSlots> timeSlots;


    public ArrayList<AnswerOptions> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(ArrayList<AnswerOptions> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public ArrayList<WhyOptions> getWhyOptions() {
        return whyOptions;
    }

    public void setWhyOptions(ArrayList<WhyOptions> whyOptions) {
        this.whyOptions = whyOptions;
    }

    public ArrayList<TimeSlots> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(ArrayList<TimeSlots> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getAnswerCaption ()
    {
        return answerCaption;
    }

    public void setAnswerCaption (String answerCaption)
    {
        this.answerCaption = answerCaption;
    }

    public String getTimeSlotCaption ()
    {
        return timeSlotCaption;
    }

    public void setTimeSlotCaption (String timeSlotCaption)
    {
        this.timeSlotCaption = timeSlotCaption;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getWhyCaption ()
    {
        return whyCaption;
    }

    public void setWhyCaption (String whyCaption)
    {
        this.whyCaption = whyCaption;
    }

    public String getIsEnabled ()
    {
        return isEnabled;
    }

    public void setIsEnabled (String isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [answerOptions = "+answerOptions+", whyOptions = "+whyOptions+", _id = "+_id+", description = "+description+", answerCaption = "+answerCaption+", timeSlotCaption = "+timeSlotCaption+", question = "+question+", whyCaption = "+whyCaption+", isEnabled = "+isEnabled+", timeSlots = "+timeSlots+"]";
    }
}
