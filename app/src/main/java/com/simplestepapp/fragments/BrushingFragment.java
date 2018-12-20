package com.simplestepapp.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.simplestepapp.R;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;

/**
 * Created by Srinivas on 12/17/2018.
 */

public class BrushingFragment extends Fragment{

    MyGridView grid_view;

    ArrayList<String> timeSlots;

    CustomAdapter customAdapter;

    LinearLayout lyt_list_Why, lyt_RgGrp;

    AppCompatTextView txt_Next;

    RadioGroup rG_brush_Masage;
    RadioButton rBtn_BruYes,rBtn_BruNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_brushing, container, false);
        initviews(v);
        timeSlots = new ArrayList<>();
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
        customAdapter = new CustomAdapter(getActivity().getApplicationContext(), timeSlots);
        grid_view.setAdapter(customAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapter.setSelectedIndex(position);
                String s_Time=(String) parent.getItemAtPosition(position);
                lyt_RgGrp.setVisibility(View.VISIBLE);
            }
        });
        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerActivity.pager.setCurrentItem(2);
            }
        });


        rG_brush_Masage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rBtn_BruYes:
                        lyt_list_Why.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rBtn_BruNo:
                        lyt_list_Why.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
        return v;
    }


    private void initviews(View v) {
        grid_view=v.findViewById(R.id.grid_view);
        lyt_list_Why=v.findViewById(R.id.lyt_list_Why);
        lyt_RgGrp=v.findViewById(R.id.lyt_RgGrp);
        txt_Next=v.findViewById(R.id.txt_Next);
        rG_brush_Masage=v.findViewById(R.id.rG_brush_Masage);
        rBtn_BruYes=v.findViewById(R.id.rBtn_BruYes);
        rBtn_BruNo=v.findViewById(R.id.rBtn_BruNo);
    }
    private static BrushingFragment instance = null;


    public static BrushingFragment newInstance(String text){

        if(instance == null){
            // new instance
            instance = new BrushingFragment();

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
