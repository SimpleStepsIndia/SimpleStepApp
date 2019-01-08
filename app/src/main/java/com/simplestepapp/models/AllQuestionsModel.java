package com.simplestepapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 1/5/2019.
 */

public class AllQuestionsModel extends ArrayList<Questioner> implements Serializable {
    private  ArrayList<Questioner> questionerArrayList;

    public ArrayList<Questioner> getQuestionerArrayList() {
        return questionerArrayList;
    }

    public void setQuestionerArrayList(ArrayList<Questioner> questionerArrayList) {
        this.questionerArrayList = questionerArrayList;
    }
}
