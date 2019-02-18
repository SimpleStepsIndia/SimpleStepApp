package com.simplestepapp.data.offline;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suren Reddy on 22-Jan-17.
 */
public class DataForQuestion {
    @SerializedName("questionid")
    private Integer questionid;

    @SerializedName("option")
    private Integer option;

    @SerializedName("advantage")
    private String advantage;

    @SerializedName("disadvantage")
    private String disadvantage;

    @SerializedName("id")
    private Integer id;

    public Integer getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    public Integer getOption() {
        return option;
    }

    public void setOption(Integer option) {
        this.option = option;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getDisadvantage() {
        return disadvantage;
    }

    public void setDisadvantage(String disadvantage) {
        this.disadvantage = disadvantage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
