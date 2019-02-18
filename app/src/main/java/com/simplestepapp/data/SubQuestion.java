package com.simplestepapp.data;

/**
 * Created by SONY on 23-03-2016.
 */
public class SubQuestion {

    private String questions;
    private String surveyId;
    private String surveySubId;
    private String surveySubSubId;
    private String questionType;
    private String answere;
    private String answeredQuestion;
    private String userAnswere;
    private String questionNo;
    private String userSecondaryAnswere;
    private String marksObtained;
    private String kpiFlag;
    private String strQuestionTypeId;
    private String NoOfDropdown;

    private String strQuestionId;
    private String strKpiTopicId;
    private String Id;

    public String getStrKpiIndicatorId() {
        return strKpiIndicatorId;
    }

    public void setStrKpiIndicatorId(String strKpiIndicatorId) {
        this.strKpiIndicatorId = strKpiIndicatorId;
    }

    public String getStrKpiTopicId() {
        return strKpiTopicId;
    }

    public void setStrKpiTopicId(String strKpiTopicId) {
        this.strKpiTopicId = strKpiTopicId;
    }

    public String getStrQuestionId() {
        return strQuestionId;
    }

    public void setStrQuestionId(String strQuestionId) {
        this.strQuestionId = strQuestionId;
    }

    private String strKpiIndicatorId;

    public SubQuestion(String strQuestion, String strSurveyId, String strSurveySubId, String strSurveySubSubID,
                       String strQuestionType, String strNoOfDropdown, String strAnswere, String strAnswereQuestion, String strUserAnswere, String strQuestionTypeId, String questionNo, String kpiFlag, String userSecondaryAnswere, String strMarksObtained, String strQuestionId, String strKpiTopicId, String strKpiIndicatorId, String Id) {
        this.questions = strQuestion;
        this.surveyId = strSurveyId;
        this.surveySubId = strSurveySubId;
        this.surveySubSubId = strSurveySubSubID;
        this.strQuestionTypeId = strQuestionTypeId;
        this.questionType = strQuestionType;
        this.NoOfDropdown = strNoOfDropdown;
        this.answere = strAnswere;
        this.answeredQuestion = strAnswereQuestion;
        this.userAnswere = strUserAnswere;
        this.questionNo = questionNo;
        this.kpiFlag = kpiFlag;
        this.userSecondaryAnswere = userSecondaryAnswere;
        this.marksObtained = marksObtained;

        this.strQuestionId = strQuestionId;
        this.strKpiTopicId = strKpiTopicId;
        this.strKpiIndicatorId = strKpiIndicatorId;
        this.Id = Id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveySubId() {
        return surveySubId;
    }

    public void setSurveySubId(String surveySubId) {
        this.surveySubId = surveySubId;
    }

    public String getSurveySubSubId() {
        return surveySubSubId;
    }

    public void setSurveySubSubId(String surveySubSubId) {
        this.surveySubSubId = surveySubSubId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswere() {
        return answere;
    }

    public void setAnswere(String answere) {
        this.answere = answere;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getSecondaryAnswere() {
        return userSecondaryAnswere;
    }

    public void setSecondaryAnswere(String userSecondaryAnswere) {
        this.userSecondaryAnswere = userSecondaryAnswere;
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

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
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

    public String getStrQuestionTypeId() {
        return strQuestionTypeId;
    }

    public void setStrQuestionTypeId(String strQuestionTypeId) {
        this.strQuestionTypeId = strQuestionTypeId;
    }

    public String getNoOfDropdown() {
        return NoOfDropdown;
    }

    public void setNoOfDropdown(String noOfDropdown) {
        NoOfDropdown = noOfDropdown;
    }
}
