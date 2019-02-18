package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.simplestepapp.data.CommonModel;


/**
 * Created by Suren Reddy on 07-Apr-16.
 */
public class User extends CommonModel {

    @SerializedName("flag")
    @Expose
    private Boolean flag;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("interval")
    @Expose
    private String interval;

    @SerializedName("realm")
    @Expose
    private String realm;

    @SerializedName("credentials")
    @Expose
    private String credentials;

    @SerializedName("challenges")
    @Expose
    private String challenges;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("emailVerified")
    @Expose
    private Boolean emailVerified;

    @SerializedName("verificationToken")
    @Expose
    private String verificationToken;

    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;

    @SerializedName("groupId")
    @Expose
    private Integer groupId;

    @SerializedName("zoneId")
    @Expose
    private Integer zoneId;

    @SerializedName("salesAreaId")
    @Expose
    private Integer salesAreaId;

    @SerializedName("distributionAreaId")
    @Expose
    private Integer distributionAreaId;

    @SerializedName("distributionSubareaId")
    @Expose
    private Integer distributionSubareaId;

    @SerializedName("password")
    @Expose
    private String password;

    /**
     *
     * @return
     * The flag
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     *
     * @param flag
     * The flag
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The interval
     */
    public String getInterval() {
        return interval;
    }

    /**
     *
     * @param interval
     * The interval
     */
    public void setInterval(String interval) {
        this.interval = interval;
    }

    /**
     *
     * @return
     * The realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     *
     * @param realm
     * The realm
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     *
     * @return
     * The credentials
     */
    public String getCredentials() {
        return credentials;
    }

    /**
     *
     * @param credentials
     * The credentials
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     *
     * @return
     * The challenges
     */
    public String getChallenges() {
        return challenges;
    }

    /**
     *
     * @param challenges
     * The challenges
     */
    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The emailVerified
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     *
     * @param emailVerified
     * The emailVerified
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     *
     * @return
     * The verificationToken
     */
    public String getVerificationToken() {
        return verificationToken;
    }

    /**
     *
     * @param verificationToken
     * The verificationToken
     */
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    /**
     *
     * @return
     * The lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     *
     * @param lastUpdated
     * The lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
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

    /**
     *
     * @return
     * The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The modifiedBy
     */
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    /**
     *
     * @param modifiedBy
     * The modifiedBy
     */
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     *
     * @return
     * The groupId
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     *
     * @param groupId
     * The groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     *
     * @return
     * The zoneId
     */
    public Integer getZoneId() {
        return zoneId;
    }

    /**
     *
     * @param zoneId
     * The zoneId
     */
    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    /**
     *
     * @return
     * The salesAreaId
     */
    public Integer getSalesAreaId() {
        return salesAreaId;
    }

    /**
     *
     * @param salesAreaId
     * The salesAreaId
     */
    public void setSalesAreaId(Integer salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    /**
     *
     * @return
     * The distributionAreaId
     */
    public Integer getDistributionAreaId() {
        return distributionAreaId;
    }

    /**
     *
     * @param distributionAreaId
     * The distributionAreaId
     */
    public void setDistributionAreaId(Integer distributionAreaId) {
        this.distributionAreaId = distributionAreaId;
    }

    /**
     *
     * @return
     * The distributionSubareaId
     */
    public Integer getDistributionSubareaId() {
        return distributionSubareaId;
    }

    /**
     *
     * @param distributionSubareaId
     * The distributionSubareaId
     */
    public void setDistributionSubareaId(Integer distributionSubareaId) {
        this.distributionSubareaId = distributionSubareaId;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
