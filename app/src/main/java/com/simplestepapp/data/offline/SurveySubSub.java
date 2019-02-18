
package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SurveySubSub {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surveytypeid")
    @Expose
    private Integer surveytypeid;
    @SerializedName("surveysubcategoryid")
    @Expose
    private Integer surveysubcategoryid;
    @SerializedName("gujrathi")
    @Expose
    private String gujrathi;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
