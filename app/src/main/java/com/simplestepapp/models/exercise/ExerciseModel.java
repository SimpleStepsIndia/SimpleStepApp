package com.simplestepapp.models.exercise;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 2/19/2019.
 */

public class ExerciseModel extends ArrayList<UExercise> implements Serializable {

    private ArrayList<UExercise> userExercise;

    private String message;

    public ArrayList<UExercise> getUserExercise() {
        return userExercise;
    }

    public void setUserExercise(ArrayList<UExercise> userExercise) {
        this.userExercise = userExercise;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
