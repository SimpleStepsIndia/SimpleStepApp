package com.simplestepapp.models;

import java.io.Serializable;

/**
 * Created by Srinivas on 1/5/2019.
 */

public class AnswerOptions implements Serializable {
    private String description;

    private int sequence;

    private boolean isAnswer;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public int getSequence ()
    {
        return sequence;
    }

    public void setSequence (int sequence)
    {
        this.sequence = sequence;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", sequence = "+sequence+", isAnswer = "+isAnswer+"]";
    }
}
