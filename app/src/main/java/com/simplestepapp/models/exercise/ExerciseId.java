package com.simplestepapp.models.exercise;

import java.io.Serializable;

/**
 * Created by Srinivas on 2/19/2019.
 */

public class ExerciseId implements Serializable {

    private String exerciseUrl;

    private String videoId;

    private String name;

    private String _id;

    public String getExerciseUrl() {
        return exerciseUrl;
    }

    public void setExerciseUrl(String exerciseUrl) {
        this.exerciseUrl = exerciseUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
