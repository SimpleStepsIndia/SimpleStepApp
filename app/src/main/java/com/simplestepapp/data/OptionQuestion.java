package com.simplestepapp.data;

/**
 * Created by SONY on 23-03-2016.
 */
public class OptionQuestion {

    private String questions;
    private String surveyId;
    private String surveySubId;
    private String surveySubSubId;
    private String questionType;
    private String answere;
    private String answeredQuestion;
    private String userAnswere;
    private String questionNo;

    private String strQuestionTypeId;
    private String NoOfDropdown;
    private String strUserSecondaryAnswere;

    private String strQuestionId;
    private String strKpiTopicId;
    private String strId;

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

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    private String strKpiIndicatorId;

    public String getStrQuestionId() {
        return strQuestionId;
    }

    public void setStrQuestionId(String strQuestionId) {
        this.strQuestionId = strQuestionId;
    }

    public String getStrMarksObtained() {
        return strMarksObtained;
    }

    public void setStrMarksObtained(String strMarksObtained) {
        this.strMarksObtained = strMarksObtained;
    }

    public String getStrUserSecondaryAnswere() {
        return strUserSecondaryAnswere;
    }

    public void setStrUserSecondaryAnswere(String strUserSecondaryAnswere) {
        this.strUserSecondaryAnswere = strUserSecondaryAnswere;
    }

    private String strMarksObtained;

    public String getKpiFlag() {
        return kpiFlag;
    }

    public void setKpiFlag(String kpiFlag) {
        this.kpiFlag = kpiFlag;
    }

    private String kpiFlag;

    public OptionQuestion(String strQuestion, String strSurveyId, String strSurveySubId, String strSurveySubSubID,
                          String strQuestionType, String strNoOfDropdown, String strAnswere, String strAnswereQuestion, String strUserAnswere, String strQuestionTypeId, String questionNo, String kpiFlag, String strUserSecondaryAnswere, String strMarksObtained, String strQuestionId, String strKpiTopicId, String strKpiIndicatorId, String strId) {
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

        this.strUserSecondaryAnswere = strUserSecondaryAnswere;
        this.strMarksObtained = strMarksObtained;

        this.strQuestionId = strQuestionId;
        this.strKpiTopicId = strKpiTopicId;
        this.strKpiIndicatorId = strKpiIndicatorId;
        this.strId = strId;
    }
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


    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
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




}
