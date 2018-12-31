package com.simplestepapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.simplestepapp.R;
import com.simplestepapp.activities.HomeActivity;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.ConclusionAdapter;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;

/**
 * Created by Srinivas on 12/20/2018.
 */

public class SunBothFragment extends Fragment {

    MyGridView grid_view;

    GridView conclusin_Grid;

    ArrayList<String> timeSlots;

    CustomAdapter customAdapter;

    ConclusionAdapter conclusionAdapter;

    LinearLayout lyt_list_Why;

    AppCompatTextView txt_Next;

    AppCompatButton btn_Submit;

    AlertDialog alertDialog;

    String s_Time = "";
    int sPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_sunbath, container, false);
        initviews(v);
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
        customAdapter = new CustomAdapter(getActivity().getApplicationContext(), timeSlots);
        grid_view.setAdapter(customAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapter.setSelectedIndex(position);
                s_Time = (String) parent.getItemAtPosition(position);
                sPosition = position;
                lyt_list_Why.setVisibility(View.VISIBLE);
            }
        });
        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent_Home=new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                startActivity(intent_Home);*/

                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setSelectedTime(s_Time);
                qAnswerModel.setS_Position(sPosition);
                ViewPagerActivity.qAnswerModelArrayList.add(qAnswerModel);
                conclusion_Dialog();
            }
        });
        return v;
    }


    private void initviews(View v) {
        grid_view = v.findViewById(R.id.grid_view);
        lyt_list_Why = v.findViewById(R.id.lyt_list_Why);
        txt_Next = v.findViewById(R.id.txt_Next);
    }

    private void conclusion_Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.conclusion_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        conclusin_Grid = view.findViewById(R.id.conclusin_Grid);
        conclusionAdapter = new ConclusionAdapter(getActivity().getApplicationContext(), timeSlots, ViewPagerActivity.qAnswerModelArrayList);
        conclusin_Grid.setAdapter(conclusionAdapter);
        btn_Submit = alertDialog.findViewById(R.id.btn_Submit);
        alertDialog.show();
    }

    private static SunBothFragment instance = null;

    public static SunBothFragment newInstance(String text) {

        if (instance == null) {
            // new instance
            instance = new SunBothFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {

            return instance;
        }

    }

}