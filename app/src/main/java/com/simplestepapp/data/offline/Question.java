
package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Question{

    @SerializedName("question")
    @Expose
    private String question;
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
    @SerializedName("gujrathi")
    @Expose
    private String gujrathi;
    @SerializedName("noofdropdown")
    @Expose
    private String noofdropdown;
    @SerializedName("answers")
    @Expose
    private String answers;
    @SerializedName("questionforoption")
    @Expose
    private String questionforoption;
    @SerializedName("questionid")
    @Expose
    private Integer questionid;
    @SerializedName("questionno")
    @Expose
    private Integer questionno;
    @SerializedName("skipto")
    @Expose
    private Integer skipto;
    @SerializedName("noofresponses")
    @Expose
    private Integer noofresponses;
    @SerializedName("range")
    @Expose
    private Integer range;
    @SerializedName("questionnum")
    @Expose
    private String questionnum;
    @SerializedName("questionnumreal")
    @Expose
    private String questionnumreal;
    @SerializedName("kpitopicid")
    @Expose
    private Integer kpitopicid;
    @SerializedName("kpiindicatorid")
    @Expose
    private Integer kpiindicatorid;
    @SerializedName("kpiflag")
    @Expose
    private Boolean kpiflag;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     *
     * @return
     *     The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     *     The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @return
     *     The surveytypeid
     */
    public Integer getSurveytypeid() {
        return surveytypeid;
    }

    /**
     *
     * @param surveytypeid
     *     The surveytypeid
     */
    public void setSurveytypeid(Integer surveytypeid) {
        this.surveytypeid = surveytypeid;
    }

    /**
     *
     * @return
     *     The surveysubcategoryid
     */
    public Integer getSurveysubcategoryid() {
        return surveysubcategoryid;
    }

    /**
     *
     * @param surveysubcategoryid
     *     The surveysubcategoryid
     */
    public void setSurveysubcategoryid(Integer surveysubcategoryid) {
        this.surveysubcategoryid = surveysubcategoryid;
    }

    /**
     *
     * @return
     *     The surveysscategoryid
     */
    public Integer getSurveysscategoryid() {
        return surveysscategoryid;
    }

    /**
     *
     * @param surveysscategoryid
     *     The surveysscategoryid
     */
    public void setSurveysscategoryid(Integer surveysscategoryid) {
        this.surveysscategoryid = surveysscategoryid;
    }

    /**
     *
     * @return
     *     The questiontype
     */
    public String getQuestiontype() {
        return questiontype;
    }

    /**
     *
     * @param questiontype
     *     The questiontype
     */
    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    /**
     *
     * @return
     *     The gujrathi
     */
    public String getGujrathi() {
        return gujrathi;
    }

    /**
     *
     * @param gujrathi
     *     The gujrathi
     */
    public void setGujrathi(String gujrathi) {
        this.gujrathi = gujrathi;
    }

    /**
     *
     * @return
     *     The noofdropdown
     */
    public String getNoofdropdown() {
        return noofdropdown;
    }

    /**
     *
     * @param noofdropdown
     *     The noofdropdown
     */
    public void setNoofdropdown(String noofdropdown) {
        this.noofdropdown = noofdropdown;
    }

    /**
     *
     * @return
     *     The answers
     */
    public String getAnswers() {
        return answers;
    }

    /**
     *
     * @param answers
     *     The answers
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }

    /**
     *
     * @return
     *     The questionforoption
     */
    public String getQuestionforoption() {
        return questionforoption;
    }

    /**
     *
     * @param questionforoption
     *     The questionforoption
     */
    public void setQuestionforoption(String questionforoption) {
        this.questionforoption = questionforoption;
    }

    /**
     *
     * @return
     *     The questionid
     */
    public Integer getQuestionid() {
        return questionid;
    }

    /**
     *
     * @param questionid
     *     The questionid
     */
    public void setQuestionid(Integer questionid) {
        this.questionid = questionid;
    }

    /**
     *
     * @return
     *     The questionno
     */
    public Integer getQuestionno() {
        return questionno;
    }

    /**
     *
     * @param questionno
     *     The questionno
     */
    public void setQuestionno(Integer questionno) {
        this.questionno = questionno;
    }

    /**
     *
     * @return
     *     The skipto
     */
    public Integer getSkipto() {
        return skipto;
    }

    /**
     *
     * @param skipto
     *     The skipto
     */
    public void setSkipto(Integer skipto) {
        this.skipto = skipto;
    }

    /**
     *
     * @return
     *     The noofresponses
     */
    public Integer getNoofresponses() {
        return noofresponses;
    }

    /**
     *
     * @param noofresponses
     *     The noofresponses
     */
    public void setNoofresponses(Integer noofresponses) {
        this.noofresponses = noofresponses;
    }

    /**
     *
     * @return
     *     The range
     */
    public Integer getRange() {
        return range;
    }

    /**
     *
     * @param range
     *     The range
     */
    public void setRange(Integer range) {
        this.range = range;
    }

    /**
     *
     * @return
     *     The questionnum
     */
    public String getQuestionnum() {
        return questionnum;
    }

    /**
     *
     * @param questionnum
     *     The questionnum
     */
    public void setQuestionnum(String questionnum) {
        this.questionnum = questionnum;
    }

    /**
     *
     * @return
     *     The questionnumreal
     */
    public String getQuestionnumreal() {
        return questionnumreal;
    }

    /**
     *
     * @param questionnumreal
     *     The questionnumreal
     */
    public void setQuestionnumreal(String questionnumreal) {
        this.questionnumreal = questionnumreal;
    }

    /**
     *
     * @return
     *     The kpitopicid
     */
    public Integer getKpitopicid() {
        return kpitopicid;
    }

    /**
     *
     * @param kpitopicid
     *     The kpitopicid
     */
    public void setKpitopicid(Integer kpitopicid) {
        this.kpitopicid = kpitopicid;
    }

    /**
     *
     * @return
     *     The kpiindicatorid
     */
    public Integer getKpiindicatorid() {
        return kpiindicatorid;
    }

    /**
     *
     * @param kpiindicatorid
     *     The kpiindicatorid
     */
    public void setKpiindicatorid(Integer kpiindicatorid) {
        this.kpiindicatorid = kpiindicatorid;
    }

    /**
     *
     * @return
     *     The kpiflag
     */
    public Boolean getKpiflag() {
        return kpiflag;
    }

    /**
     *
     * @param kpiflag
     *     The kpiflag
     */
    public void setKpiflag(Boolean kpiflag) {
        this.kpiflag = kpiflag;
    }

    /**
     *
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }


}
