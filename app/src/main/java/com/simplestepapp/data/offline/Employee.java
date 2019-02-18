package com.simplestepapp.data.offline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Suren Reddy on 07-Apr-16.
 */
public class Employee {
    @SerializedName("empId")
    @Expose
    private String empId;
    @SerializedName("salesCode")
    @Expose
    private String salesCode;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zip")
    @Expose
    private Integer zip;
    @SerializedName("mobile")
    @Expose
    private Long mobile;
    @SerializedName("im")
    @Expose
    private String im;
    @SerializedName("surveycount")
    @Expose
    private Integer surveycount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("groupId")
    @Expose
    private Integer groupId;
    @SerializedName("salesManagerId")
    @Expose
    private Integer salesManagerId;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("stateId")
    @Expose
    private Integer stateId;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("imId")
    @Expose
    private Integer imId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;

    @SerializedName("registered")
    @Expose
    private boolean registered;

    /**
     *
     * @return
     * The empId
     */
    public String getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     * The empId
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     * The salesCode
     */
    public String getSalesCode() {
        return salesCode;
    }

    /**
     *
     * @param salesCode
     * The salesCode
     */
    public void setSalesCode(String salesCode) {
        this.salesCode = salesCode;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The zip
     */
    public Integer getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(Integer zip) {
        this.zip = zip;
    }

    /**
     *
     * @return
     * The mobile
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The im
     */
    public String getIm() {
        return im;
    }

    /**
     *
     * @param im
     * The im
     */
    public void setIm(String im) {
        this.im = im;
    }

    /**
     *
     * @return
     * The surveycount
     */
    public Integer getSurveycount() {
        return surveycount;
    }

    /**
     *
     * @param surveycount
     * The surveycount
     */
    public void setSurveycount(Integer surveycount) {
        this.surveycount = surveycount;
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
     * The salesManagerId
     */
    public Integer getSalesManagerId() {
        return salesManagerId;
    }

    /**
     *
     * @param salesManagerId
     * The salesManagerId
     */
    public void setSalesManagerId(Integer salesManagerId) {
        this.salesManagerId = salesManagerId;
    }

    /**
     *
     * @return
     * The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The cityId
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The stateId
     */
    public Integer getStateId() {
        return stateId;
    }

    /**
     *
     * @param stateId
     * The stateId
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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
     * The imId
     */
    public Integer getImId() {
        return imId;
    }

    /**
     *
     * @param imId
     * The imId
     */
    public void setImId(Integer imId) {
        this.imId = imId;
    }

    /**
     *
     * @return
     * The departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     *
     * @param departmentId
     * The departmentId
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }


    public boolean getRegistered() {
        return registered;
    }

    /**
     *
     * @param registered
     * The departmentId
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
