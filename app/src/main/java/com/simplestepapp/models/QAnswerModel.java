package com.simplestepapp.models;

import java.io.Serializable;

public class QAnswerModel implements Serializable {

    private String questionId;
    private String timeSlotOption;
    private String answerOption;
    private String whyOption;
    private String colorCode;
    private int s_Position;

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTimeSlotOption() {
        return timeSlotOption;
    }

    public void setTimeSlotOption(String timeSlotOption) {
        this.timeSlotOption = timeSlotOption;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public String getWhyOption() {
        return whyOption;
    }

    public void setWhyOption(String whyOption) {
        this.whyOption = whyOption;
    }

    public int getS_Position() {
        return s_Position;
    }

    public void setS_Position(int s_Position) {
        this.s_Position = s_Position;
    }
}
