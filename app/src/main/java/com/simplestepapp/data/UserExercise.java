package com.simplestepapp.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suren Reddy on 30-Jul-17.
 */

public class UserExercise extends CommonModel {

    @SerializedName("exerciseid")
    private Integer exerciseid;

    @SerializedName("set")
    private Integer set;

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

    @SerializedName("gap")
    private String gap;

    @SerializedName("slno")
    private Integer slno;

    @SerializedName("exerciseslno")
    private Integer exerciseslno;

    @SerializedName("workouttime")
    private String workouttime;

    @SerializedName("masterid")
    private Integer masterid;

    @SerializedName("id")
    private Integer id;

    public Integer getExerciseid() {
        return exerciseid;
    }

    public void setExerciseid(Integer exerciseid) {
        this.exerciseid = exerciseid;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
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

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public Integer getExerciseslno() {
        return exerciseslno;
    }

    public void setExerciseslno(Integer exerciseslno) {
        this.exerciseslno = exerciseslno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterid() {
        return masterid;
    }

    public void setMasterid(Integer masterid) {
        this.masterid = masterid;
    }

    public String getWorkouttime() {
        return workouttime;
    }

    public void setWorkouttime(String workouttime) {
        this.workouttime = workouttime;
    }

}