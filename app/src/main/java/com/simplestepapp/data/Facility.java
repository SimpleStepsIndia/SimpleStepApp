package com.simplestepapp.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Facility implements Parcelable {

    private int image;
    private String name;
    private String surveyId;
    private String surveySubId;
    private String surveySubSubId;

    public Facility(String name, int image, String surveyId, String surveySubId, String surveySubSubId) {
        this.name = name;
        this.image = image;
        this.surveyId = surveyId;
        this.surveySubId = surveySubId;
        this.surveySubSubId = surveySubSubId;
    }

    public String getSurveySubId() {
        return surveySubId;
    }
    public String getSurveySubSubId() {
        return surveySubSubId;
    }

    public void setSurveySubSubId(String surveySubSubId) {
        this.surveySubSubId = surveySubSubId;
    }
    public void setSurveySubId(String surveySubId) {
        this.surveySubId = surveySubId;
    }

    public String getsurveyId() {
        return surveyId;
    }

    public void setsurveyId(String id) {
        this.surveyId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public Facility(Parcel in) {
        image = in.readInt();
        name = in.readString();
        surveyId = in.readString();
        surveySubId = in.readString();
        surveySubSubId = in.readString();
    }

    public static final Creator<Facility> CREATOR = new Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel in) {
            return new Facility(in);
        }

        @Override
        public Facility[] newArray(int size) {
            return new Facility[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(name);
        dest.writeString(surveyId);
        dest.writeString(surveySubId);
        dest.writeString(surveySubSubId);
    }
}
