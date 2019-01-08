package com.simplestepapp.models;

import java.io.Serializable;

/**
 * Created by Srinivas on 1/5/2019.
 */

public class WhyOptions implements Serializable{
    private String description;

    private int sequence;

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

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", sequence = "+sequence+"]";
    }
}
