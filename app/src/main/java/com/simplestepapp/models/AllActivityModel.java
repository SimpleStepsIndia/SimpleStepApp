package com.simplestepapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Srinivas on 2/16/2019.
 */

public class AllActivityModel extends ArrayList<ActQuestioner> implements Serializable {
    private  ArrayList<ActQuestioner> ActQuestionerArrayList;

    public ArrayList<ActQuestioner> getActQuestionerArrayList() {
        return ActQuestionerArrayList;
    }

    public void setActQuestionerArrayList(ArrayList<ActQuestioner> ActQuestionerArrayList) {
        this.ActQuestionerArrayList = ActQuestionerArrayList;
    }
}

