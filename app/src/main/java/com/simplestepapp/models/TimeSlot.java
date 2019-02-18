package com.simplestepapp.models;

import java.io.Serializable;

/**
 * Created by Srinivas on 2/16/2019.
 */

public class TimeSlot implements Serializable {
    private String slot;

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
