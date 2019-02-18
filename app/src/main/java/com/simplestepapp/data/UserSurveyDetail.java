
package com.simplestepapp.data;


import com.google.gson.annotations.SerializedName;


public class UserSurveyDetail {
    @SerializedName("usernameid")
    private String usernameid;
    @SerializedName("surveytype")
    private String surveytype;
    @SerializedName("username")
    private String username;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("employeename")
    private String employeename;
    @SerializedName("startingtime")
    private String startingtime;
    @SerializedName("endingtime")
    private String endingtime;
    @SerializedName("nameofmedicalofficer")
    private String nameofmedicalofficer;
    @SerializedName("datetimeofdatacollection")
    private String datetimeofdatacollection;
    @SerializedName("state")
    private String state;
    @SerializedName("district")
    private String district;
    @SerializedName("block")
    private String block;
    @SerializedName("ruralurbansemiurban")
    private String ruralurbansemiurban;
    @SerializedName("nameofthefacility")
    private String nameofthefacility;
    @SerializedName("typeoffacility")
    private String typeoffacility;
    @SerializedName("responsibleformaintanance")
    private String responsibleformaintanance;
    @SerializedName("registered")
    private Boolean registered;
    @SerializedName("facility")
    private String facility;
    @SerializedName("id")
    private Integer id;

    public String getUsernameid() {
        return usernameid;
    }

    public void setUsernameid(String usernameid) {
        this.usernameid = usernameid;
    }

    public String getSurveytype() {
        return surveytype;
    }

    public void setSurveytype(String surveytype) {
        this.surveytype = surveytype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getStartingtime() {
        return startingtime;
    }

    public void setStartingtime(String startingtime) {
        this.startingtime = startingtime;
    }

    public String getEndingtime() {
        return endingtime;
    }

    public void setEndingtime(String endingtime) {
        this.endingtime = endingtime;
    }

    public String getNameofmedicalofficer() {
        return nameofmedicalofficer;
    }

    public void setNameofmedicalofficer(String nameofmedicalofficer) {
        this.nameofmedicalofficer = nameofmedicalofficer;
    }

    public String getDatetimeofdatacollection() {
        return datetimeofdatacollection;
    }

    public void setDatetimeofdatacollection(String datetimeofdatacollection) {
        this.datetimeofdatacollection = datetimeofdatacollection;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getRuralurbansemiurban() {
        return ruralurbansemiurban;
    }

    public void setRuralurbansemiurban(String ruralurbansemiurban) {
        this.ruralurbansemiurban = ruralurbansemiurban;
    }

    public String getNameofthefacility() {
        return nameofthefacility;
    }

    public void setNameofthefacility(String nameofthefacility) {
        this.nameofthefacility = nameofthefacility;
    }

    public String getTypeoffacility() {
        return typeoffacility;
    }

    public void setTypeoffacility(String typeoffacility) {
        this.typeoffacility = typeoffacility;
    }

    public String getResponsibleformaintanance() {
        return responsibleformaintanance;
    }

    public void setResponsibleformaintanance(String responsibleformaintanance) {
        this.responsibleformaintanance = responsibleformaintanance;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
