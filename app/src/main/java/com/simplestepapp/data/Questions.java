package com.simplestepapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SONY on 23-03-2016.
 */
public class Questions implements Parcelable {

    private String question;
    private String surveytypeid;
    private String surveysubcategoryid;
    private String surveysscategoryid;
    private String questiontype;
    private String answers;
    private String answeredQuestion;
    private String userAnswere;
    private String questionno;
    private String strQuestionTypeId;
    private String NoOfDropdown;
    private String userSecondaryAnswer;
    private String id;
    private String strQuestionId;
    private String strKpiTopicId;
    private String strKpiIndicatorId;
    private String marksObtained;
    private String skipTo;
    private String skipped;

    public String getStrQuestionId() {
        return strQuestionId;
    }

    public void setStrQuestionId(String strQuestionId) {
        this.strQuestionId = strQuestionId;
    }

    public String getStrKpiTopicId() {
        return strKpiTopicId;
    }

    public void setStrKpiTopicId(String strKpiTopicId) {
        this.strKpiTopicId = strKpiTopicId;
    }

    public String getStrKpiIndicatorId() {
        return strKpiIndicatorId;
    }

    public void setStrKpiIndicatorId(String strKpiIndicatorId) {
        this.strKpiIndicatorId = strKpiIndicatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String Id) {
        this.id = Id;
    }



    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getKpiFlag() {
        return kpiFlag;
    }

    public void setKpiFlag(String kpiFlag) {
        this.kpiFlag = kpiFlag;
    }

    private String kpiFlag;

    public Questions(String strQuestion, String strSurveyId, String strSurveySubId, String strSurveySubSubID,
                     String strQuestionType, String strNoOfDropdown, String strAnswere,
                     String strAnswereQuestion, String strUserAnswere,
                     String strQuestionTypeId, String questionNo, String kpiFlag, String strSecondaryAnswere, String strMarkksObtained,
                     String strQuestionId, String strKpiTopicId, String strKpiIndicatorId, String Id, String SkipTo, String Skipped) {
        this.question = strQuestion;
        this.surveytypeid = strSurveyId;
        this.surveysubcategoryid = strSurveySubId;
        this.surveysscategoryid = strSurveySubSubID;
        this.strQuestionTypeId = strQuestionTypeId;
        this.questiontype = strQuestionType;
        this.NoOfDropdown = strNoOfDropdown;
        this.answers = strAnswere;
        this.answeredQuestion = strAnswereQuestion;
        this.userAnswere = strUserAnswere;
        this.questionno = questionNo;
        this.kpiFlag = kpiFlag;
        this.userSecondaryAnswer = userSecondaryAnswer;
        this.marksObtained = marksObtained;
        this.id = Id;
        this.skipTo = SkipTo;
        this.skipped = Skipped;

        this.strQuestionId = strQuestionId;
        this.strKpiTopicId = strKpiTopicId;
        this.strKpiIndicatorId = strKpiIndicatorId;


    }

    protected Questions(Parcel in) {
        question = in.readString();
        surveytypeid = in.readString();
        surveysubcategoryid = in.readString();
        surveysscategoryid = in.readString();
        questiontype = in.readString();
        answers = in.readString();
        answeredQuestion = in.readString();
        userAnswere = in.readString();
        questionno = in.readString();
        strQuestionTypeId = in.readString();
        NoOfDropdown = in.readString();
        kpiFlag = in.readString();
        userSecondaryAnswer = in.readString();
        marksObtained = in.readString();

        strKpiTopicId = in.readString();
        strKpiIndicatorId = in.readString();
        strQuestionId = in.readString();
        id = in.readString();
        skipTo = in.readString();
        skipped = in.readString();
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public String getNoOfDropdown() {
        return NoOfDropdown;
    }

    public void setNoOfDropdown(String noOfDropdown) {
        NoOfDropdown = noOfDropdown;
    }

    public String getStrQuestionTypeId() {
        return strQuestionTypeId;
    }

    public void setStrQuestionTypeId(String strQuestionTypeId) {
        this.strQuestionTypeId = strQuestionTypeId;
    }


    public String getQuestionno() {
        return questionno;
    }

    public void setQuestionno(String questionno) {
        this.questionno = questionno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSurveytypeid() {
        return surveytypeid;
    }

    public void setSurveytypeid(String surveytypeid) {
        this.surveytypeid = surveytypeid;
    }

    public String getSurveysubcategoryid() {
        return surveysubcategoryid;
    }

    public void setSurveysubcategoryid(String surveysubcategoryid) {
        this.surveysubcategoryid = surveysubcategoryid;
    }

    public String getSurveysscategoryid() {
        return surveysscategoryid;
    }

    public void setSurveysscategoryid(String surveysscategoryid) {
        this.surveysscategoryid = surveysscategoryid;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(String answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }

    public String getUserAnswere() {
        return userAnswere;
    }

    public void setUserAnswere(String userAnswere) {
        this.userAnswere = userAnswere;
    }



    public String getUserSecondaryAnswere() {
        return userSecondaryAnswer;
    }

    public void setUserSecondaryAnswere(String userAnswere) {
        this.userSecondaryAnswer = userSecondaryAnswer;
    }

    public String getSkipTo() {
        return skipTo;
    }

    public void setSkipTo(String skipTo) {
        this.skipTo = skipTo;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(surveytypeid);
        dest.writeString(surveysubcategoryid);
        dest.writeString(surveysscategoryid);
        dest.writeString(questiontype);
        dest.writeString(answers);
        dest.writeString(answeredQuestion);
        dest.writeString(userAnswere);
        dest.writeString(questionno);
        dest.writeString(strQuestionTypeId);
        dest.writeString(NoOfDropdown);
        dest.writeString(kpiFlag);
        dest.writeString(userSecondaryAnswer);
        dest.writeString(marksObtained);
        dest.writeString(id);
        dest.writeString(skipTo);
        dest.writeString(skipped);

        dest.writeString(strKpiTopicId);
        dest.writeString(strKpiIndicatorId);
        dest.writeString(strQuestionId);
    }
}
