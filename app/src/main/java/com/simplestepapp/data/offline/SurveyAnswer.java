package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suren Reddy on 07-Apr-16.
 */
public class SurveyAnswer {
    @SerializedName("questionid")
    @Expose
    private Integer questionid;
    @SerializedName("surveytypeid")
    @Expose
    private Integer surveytypeid;
    @SerializedName("surveysubcategoryid")
    @Expose
    private Integer surveysubcategoryid;
    @SerializedName("surveysscategoryid")
    @Expose
    private Integer surveysscategoryid;
    @SerializedName("questiontype")
    @Expose
    private String questiontype;
    @SerializedName("answers")
    @Expose
    private String answers;
    @SerializedName("secondaryanswers")
    @Expose
    private String secondaryanswers;
    @SerializedName("completeflag")
    @Expose
    private String completeflag;
    @SerializedName("usernameid")
    @Expose
    private String usernameid;
    @SerializedName("kpitopicid")
    @Expose
    private Integer kpitopicid;
    @SerializedName("kpiindicatorid")
    @Expose
    private Integer kpiindicatorid;
    @SerializedName("kpiflag")
    @Expose
    private Boolean kpiflag;
    @SerializedName("marksobtained")
    @Expose
    private Double marksobtained;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     *
     * @return
     * The questionid
     */
    public Integer getQuestionid() {
        return questionid;
    }

    /**
     *
     * @param questionid
     * The questionid
     */
    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    /**
     *
     * @return
     * The surveytypeid
     */
    public Integer getSurveytypeid() {
        return surveytypeid;
    }

    /**
     *
     * @param surveytypeid
     * The surveytypeid
     */
    public void setSurveytypeid(Integer surveytypeid) {
        this.surveytypeid = surveytypeid;
    }

    /**
     *
     * @return
     * The surveysubcategoryid
     */
    public Integer getSurveysubcategoryid() {
        return surveysubcategoryid;
    }

    /**
     *
     * @param surveysubcategoryid
     * The surveysubcategoryid
     */
    public void setSurveysubcategoryid(Integer surveysubcategoryid) {
        this.surveysubcategoryid = surveysubcategoryid;
    }

    /**
     *
     * @return
     * The surveysscategoryid
     */
    public Integer getSurveysscategoryid() {
        return surveysscategoryid;
    }

    /**
     *
     * @param surveysscategoryid
     * The surveysscategoryid
     */
    public void setSurveysscategoryid(Integer surveysscategoryid) {
        this.surveysscategoryid = surveysscategoryid;
    }

    /**
     *
     * @return
     * The questiontype
     */
    public String getQuestiontype() {
        return questiontype;
    }

    /**
     *
     * @param questiontype
     * The questiontype
     */
    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    /**
     *
     * @return
     * The answers
     */
    public String getAnswers() {
        return answers;
    }

    /**
     *
     * @param answers
     * The answers
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }

    /**
     *
     * @return
     * The secondaryanswers
     */
    public String getSecondaryanswers() {
        return secondaryanswers;
    }

    /**
     *
     * @param secondaryanswers
     * The secondaryanswers
     */
    public void setSecondaryanswers(String secondaryanswers) {
        this.secondaryanswers = secondaryanswers;
    }

    /**
     *
     * @return
     * The completeflag
     */
    public String getCompleteflag() {
        return completeflag;
    }

    /**
     *
     * @param completeflag
     * The completeflag
     */
    public void setCompleteflag(String completeflag) {
        this.completeflag = completeflag;
    }

    /**
     *
     * @return
     * The usernameid
     */
    public String getUsernameid() {
        return usernameid;
    }

    /**
     *
     * @param usernameid
     * The usernameid
     */
    public void setUsernameid(String usernameid) {
        this.usernameid = usernameid;
    }

    /**
     *
     * @return
     * The kpitopicid
     */
    public Integer getKpitopicid() {
        return kpitopicid;
    }

    /**
     *
     * @param kpitopicid
     * The kpitopicid
     */
    public void setKpitopicid(Integer kpitopicid) {
        this.kpitopicid = kpitopicid;
    }

    /**
     *
     * @return
     * The kpiindicatorid
     */
    public Integer getKpiindicatorid() {
        return kpiindicatorid;
    }

    /**
     *
     * @param kpiindicatorid
     * The kpiindicatorid
     */
    public void setKpiindicatorid(Integer kpiindicatorid) {
        this.kpiindicatorid = kpiindicatorid;
    }

    /**
     *
     * @return
     * The kpiflag
     */
    public Boolean getKpiflag() {
        return kpiflag;
    }

    /**
     *
     * @param kpiflag
     * The kpiflag
     */
    public void setKpiflag(Boolean kpiflag) {
        this.kpiflag = kpiflag;
    }

    /**
     *
     * @return
     * The marksobtained
     */
    public Double getMarksobtained() {
        return marksobtained;
    }

    /**
     *
     * @param marksobtained
     * The marksobtained
     */
    public void setMarksobtained(Double marksobtained) {
        this.marksobtained = marksobtained;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
