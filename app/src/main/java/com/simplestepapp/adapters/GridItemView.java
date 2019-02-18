package com.simplestepapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.simplestepapp.R;
import com.simplestepapp.activities.DailyRoutineActivity;


public class GridItemView extends FrameLayout {

    private AppCompatTextView textView;
    private int disable_Position=0;

    public GridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.list_item, this);
        textView = getRootView().findViewById(R.id.txt_Timeslot);
        disable_Position= DailyRoutineActivity.dis_Position;
    }

    public void display(String text, boolean isSelected,int position) {
        textView.setText(text.toUpperCase());
        if ( position<disable_Position){
            textView.setBackgroundColor(Color.GRAY);
            textView.setTextColor(Color.DKGRAY);
        }
        display(isSelected);
    }

    public void display(boolean isSelected) {
        textView.setBackgroundResource(isSelected ? R.color.selected_color : R.color.grey);
        textView.setTextColor(getResources().getColor(isSelected ? R.color.selected_color_white:R.color.selected_color));

    }
}