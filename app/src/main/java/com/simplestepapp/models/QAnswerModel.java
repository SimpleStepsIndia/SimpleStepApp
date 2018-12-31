package com.simplestepapp.models;

import java.io.Serializable;

public class QAnswerModel implements Serializable {

    private String selectedTime;
    private String why_Option;
    private String no_OfTimes_Optn;
    private int s_Position;

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getWhy_Option() {
        return why_Option;
    }

    public void setWhy_Option(String why_Option) {
        this.why_Option = why_Option;
    }

    public String getNo_OfTimes_Optn() {
        return no_OfTimes_Optn;
    }

    public void setNo_OfTimes_Optn(String no_OfTimes_Optn) {
        this.no_OfTimes_Optn = no_OfTimes_Optn;
    }

    public int getS_Position() {
        return s_Position;
    }

    public void setS_Position(int s_Position) {
        this.s_Position = s_Position;
    }
}
