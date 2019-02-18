package com.simplestepapp.data;

/**
 * Created by Suren Reddy on 04-Apr-16.
 */
public class SubQuestionNumber {
    private String questions;
    private String surveyId;
    private String surveySubId;
    private String surveySubSubId;
    private String questionType;
    private String answere;
    private String secondaryAnswere;
    private String answeredQuestion;
    private String userAnswere;
    private String questionNo;


    public String getKpiFlag() {
        return kpiFlag;
    }

    public void setKpiFlag(String kpiFlag) {
        this.kpiFlag = kpiFlag;
    }

    private String kpiFlag;


    private String strQuestionTypeId;

    private String NoOfDropdown;

    public SubQuestionNumber(String strQuestion, String strSurveyId, String strSurveySubId, String strSurveySubSubID,
                             String strQuestionType, String strNoOfDropdown, String strAnswere, String strSecondaryAnswere, String strAnswereQuestion, String strUserAnswere, String strQuestionTypeId, String questionNo, String kpiFlag) {
        this.questions = strQuestion;
        this.surveyId = strSurveyId;
        this.surveySubId = strSurveySubId;
        this.surveySubSubId = strSurveySubSubID;
        this.strQuestionTypeId = strQuestionTypeId;
        this.questionType = strQuestionType;
        this.NoOfDropdown = strNoOfDropdown;
        this.answere = strAnswere;
        this.secondaryAnswere = strSecondaryAnswere;
        this.answeredQuestion = strAnswereQuestion;
        this.userAnswere = strUserAnswere;
        this.questionNo = questionNo;
        this.kpiFlag = kpiFlag;
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

    public String getSecondaryAnswere() {
        return secondaryAnswere;
    }

    public void setSecondaryAnswere(String secondaryAnswere) {
        this.secondaryAnswere = secondaryAnswere;
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
