package com.simplestepapp.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.simplestepapp.R;


public class GridItemView extends FrameLayout {

    private AppCompatTextView textView;

    public GridItemView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.list_item, this);
        textView = getRootView().findViewById(R.id.text);
    }

    public void display(String text, boolean isSelected) {
        textView.setText(text.toUpperCase());
        display(isSelected);
    }

    public void display(boolean isSelected) {
        textView.setBackgroundResource(isSelected ? R.color.selected_color : R.color.grey);
        textView.setTextColor(getResources().getColor(isSelected ? R.color.selected_color_white:R.color.selected_color));

    }
}