package com.simplestepapp.models;

import java.io.Serializable;

/**
 * Created by Srinivas on 1/5/2019.
 */

public class TimeSlots implements Serializable{

    private String sequence;

    private String slot;

    public String getSequence ()
    {
        return sequence;
    }

    public void setSequence (String sequence)
    {
        this.sequence = sequence;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sequence = "+sequence+", slot = "+slot+"]";
    }
}
