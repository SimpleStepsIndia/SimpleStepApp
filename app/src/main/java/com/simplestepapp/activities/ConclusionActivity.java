package com.simplestepapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.GridView;

import com.simplestepapp.R;
import com.simplestepapp.adapters.ConclusionAdapter;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.WhyOptions;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;

public class ConclusionActivity extends Activity {

    MyGridView grid_view;

    GridView conclusin_Grid;
    ConclusionAdapter conclusionAdapter;

    ArrayList<String> timeSlots;
    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;



    int sPosition = -1;

    AppCompatButton submit;

    AlertDialog alertDialog;

    public static final Integer[] imagesarray = { R.drawable.wakeup_icon,
            R.drawable.brushicon, R.drawable.colon, R.drawable.drining_water,R.drawable.mental_fitness,R.drawable.physical_fitness,R.drawable.sun_shine };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_final);
        conclusin_Grid = findViewById(R.id.conclusin_Grid);
        submit=findViewById(R.id.btn_Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ConclusionActivity.this,ProfileActivity.class);
                startActivity(i);

            }
        });
        timeSlots = new ArrayList<>();
        timeSlots.add("< 5:00");
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
        timeSlots.add("None");
        try {
            conclusionAdapter = new ConclusionAdapter(getApplicationContext(), timeSlots, ViewPagerActivity.qAnswerModelArrayList);
            conclusin_Grid.setAdapter(conclusionAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }





    }






}