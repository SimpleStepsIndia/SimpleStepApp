package com.simplestepapp.models.exercise;

import java.io.Serializable;

/**
 * Created by Srinivas on 2/19/2019.
 */

public class UExercise implements Serializable {

    private String createdAt;

    private String rest;

    private String sequence;

    private String reps;

    private ExerciseId exerciseId;

    private String sets;

    private String _id;

    private String user;

    private String updatedAt;

    private Boolean isSelected;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public ExerciseId getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(ExerciseId exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
