package com.simplestepapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.DatePicker;

import com.simplestepapp.R;
import com.simplestepapp.utils.Toaster;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Srinivas on 2/14/2019.
 */

public class DailyRtneFreStyleWrktActivity extends AppCompatActivity {

    AppCompatButton btn_DlyRtne, btn_FreStylWrkt, btn_DlyRtne_Date, btn_FreStylWrkt_Date;

    public int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlyrtnewrkut);

        btn_DlyRtne = findViewById(R.id.btn_DlyRtne);
        btn_FreStylWrkt = findViewById(R.id.btn_FreStylWrkt);
        btn_DlyRtne_Date = findViewById(R.id.btn_DlyRtne_Date);
        btn_FreStylWrkt_Date = findViewById(R.id.btn_FreStylWrkt_Date);

        btn_DlyRtne_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DailyRtneFreStyleWrktActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String selctdDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                try {
                                    btn_DlyRtne_Date.setText(selctdDate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });


        btn_FreStylWrkt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DailyRtneFreStyleWrktActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String selctdDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                try {

                                    btn_FreStylWrkt_Date.setText(selctdDate);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });
        btn_DlyRtne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_DlyRtne_Date.getText().toString().equalsIgnoreCase("Select Date")) {
                    Toaster.showWarningMessage("Please Select Date !");
                } else {
                    Intent intent_D = new Intent(getApplicationContext(), DailyRoutineActivity.class);
                    intent_D.putExtra("Date", btn_DlyRtne_Date.getText().toString());
                    startActivity(intent_D);
                }
            }
        });

        btn_FreStylWrkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_F = new Intent(getApplicationContext(), FreeStyleWorkActivity.class);
                startActivity(intent_F);

            }
        });
    }
}
