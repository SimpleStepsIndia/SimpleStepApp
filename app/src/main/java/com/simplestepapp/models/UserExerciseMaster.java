package com.simplestepapp.models;

import com.google.gson.annotations.SerializedName;
import com.simplestepapp.data.CommonModel;


/**
 * Created by Suren Reddy on 02-Aug-17.
 */

public class UserExerciseMaster extends CommonModel {

    @SerializedName("exercises")
    private String exercises;

    @SerializedName("sets")
    private Integer sets;

    @SerializedName("reps")
    private Integer reps;

    @SerializedName("userid")
    private Integer userid;

    @SerializedName("date")
    private String date;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;

    @SerializedName("bmi")
    private String bmi;

    @SerializedName("anysurgery")
    private Boolean anysurgery;

    @SerializedName("surgerytype")
    private String surgerytype;

    @SerializedName("surgeryname")
    private String surgeryname;

    @SerializedName("workouttype")
    private String workouttype;

    @SerializedName("workoutpurpose")
    private String workoutpurpose;

    @SerializedName("workoutfeedback")
    private String workoutfeedback;

    @SerializedName("totaltime")
    private String totaltime;

    @SerializedName("workouttime")
    private String workouttime;

    @SerializedName("resttime")
    private String resttime;

    @SerializedName("id")
    private Integer id;

    public String getExercises() {
        return exercises;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public Boolean getAnysurgery() {
        return anysurgery;
    }

    public void setAnysurgery(Boolean anysurgery) {
        this.anysurgery = anysurgery;
    }

    public String getSurgerytype() {
        return surgerytype;
    }

    public void setSurgerytype(String surgerytype) {
        this.surgerytype = surgerytype;
    }

    public String getSurgeryname() {
        return surgeryname;
    }

    public void setSurgeryname(String surgeryname) {
        this.surgeryname = surgeryname;
    }

    public String getWorkouttype() {
        return workouttype;
    }

    public void setWorkouttype(String workouttype) {
        this.workouttype = workouttype;
    }

    public String getWorkoutpurpose() {
        return workoutpurpose;
    }

    public void setWorkoutpurpose(String workoutpurpose) {
        this.workoutpurpose = workoutpurpose;
    }

    public String getWorkoutfeedback() {
        return workoutfeedback;
    }

    public void setWorkoutfeedback(String workoutfeedback) {
        this.workoutfeedback = workoutfeedback;
    }

    public String getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }

    public String getWorkouttime() {
        return workouttime;
    }

    public void setWorkouttime(String workouttime) {
        this.workouttime = workouttime;
    }

    public String getResttime() {
        return resttime;
    }

    public void setResttime(String resttime) {
        this.resttime = resttime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
