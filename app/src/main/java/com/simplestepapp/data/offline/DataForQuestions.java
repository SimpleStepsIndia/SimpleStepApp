package com.simplestepapp.data.offline;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suren Reddy on 22-Jan-17.
 */
public class DataForQuestions  implements Parcelable {

    private String id;
    private String questionid;
    private String option;
    private String advantage;
    private String disadvantage;

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
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

    public DataForQuestions(String strId, String strQuestionId, String strOption, String strAvantage,
                            String strDisadvantage) {
        this.id = strId;
        this.questionid = strQuestionId;
        this.option = strOption;
        this.advantage = strAvantage;
        this.disadvantage = strDisadvantage;


    }

    protected DataForQuestions(Parcel in) {
        id = in.readString();
        questionid = in.readString();
        option = in.readString();
        advantage = in.readString();
        disadvantage = in.readString();
    }

    public static final Creator<DataForQuestions> CREATOR = new Creator<DataForQuestions>() {
        @Override
        public DataForQuestions createFromParcel(Parcel in) {
            return new DataForQuestions(in);
        }

        @Override
        public DataForQuestions[] newArray(int size) {
            return new DataForQuestions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(questionid);
        dest.writeString(option);
        dest.writeString(advantage);
        dest.writeString(disadvantage);
    }
}
