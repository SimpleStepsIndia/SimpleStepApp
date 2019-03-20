package com.simplestepapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.simplestepapp.R;
import com.simplestepapp.adapters.ConclusionAdapter;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.WhyOptions;
import com.simplestepapp.utils.MyGridView;
import com.sms.sma.UnityPlayerActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ConclusionActivity extends AppCompatActivity {

    MyGridView grid_view;

    GridView conclusin_Grid;
    ConclusionAdapter conclusionAdapter;

    ArrayList<String> timeSlots;
    final ArrayList<String> pre_TimeSlots = new ArrayList<>();

    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;
    ArrayList<Integer> repeatedTimes;

    int sPosition = -1;

    AppCompatButton submit;

    AlertDialog alertDialog;

    public static final Integer[] imagesarray = {R.drawable.wakeup_icon, R.drawable.brushicon, R.drawable.colon,
            R.drawable.drining_water, R.drawable.mental_fitness, R.drawable.physical_fitness, R.drawable.sun_shine};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarsetUp();
        setContentView(R.layout.frag_final);
        conclusin_Grid = findViewById(R.id.conclusin_Grid);
        submit = findViewById(R.id.btn_Submit);
        repeatedTimes = new ArrayList<>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Profile = new Intent(ConclusionActivity.this, ProfileActivity.class);
                startActivity(intent_Profile);
            }
        });

        timeSlots = new ArrayList<>();

        /*timeSlots.add("< 5:00");
        timeSlots.add("5:00");
        timeSlots.add("5:15");
        timeSlots.add("5:30");
        timeSlots.add("5:45");
        timeSlots.add("6:00");
        timeSlots.add("6:15");
        timeSlots.add("6:30");
        timeSlots.add("6:45");
        timeSlots.add("7:00");
        timeSlots.add("7:15");
        timeSlots.add("7:30");
        timeSlots.add("7:45");
        timeSlots.add("8:00");
        timeSlots.add("8:15");
        timeSlots.add("8:30");
        timeSlots.add("8:45");
        timeSlots.add("9:00");
        timeSlots.add("9:00 >");
        timeSlots.add("None");*/


        try {

            try {
                ArrayList<Integer> integerArrayList = new ArrayList<>();

                for (int i = 0; i < QuestionerActivity.qAnswerModelArrayList.size(); i++) {
                    integerArrayList.add(QuestionerActivity.qAnswerModelArrayList.get(i).getS_Position());
                    //repeatedTimes.add(QuestionerActivity.qAnswerModelArrayList.get(i).getS_Position());
                }

                Set<Integer> hashSet_RePos = new HashSet<>();

                int inc_Val = 0;
                for (Integer val : integerArrayList) {

                    if (!hashSet_RePos.add(val)) {
                        ++inc_Val;
                        timeSlots.add(val + inc_Val, timeSlots.get(val + inc_Val - 1));
                        repeatedTimes.add(repeatedTimes.size(), repeatedTimes.get(repeatedTimes.size() - 1) + 1);

                    } else {
                        repeatedTimes.add(val + inc_Val);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            conclusionAdapter = new ConclusionAdapter(getApplicationContext(), R.layout.conclusion_item, timeSlots, QuestionerActivity.qAnswerModelArrayList, repeatedTimes);
            conclusin_Grid.setAdapter(conclusionAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toolbarsetUp() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.lyt_header, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}