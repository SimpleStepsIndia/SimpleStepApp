package com.simplestepapp.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.simplestepapp.activities.DailyRoutineActivity;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> strings;
    public List<Integer> selectedPositions;
    private int disable_Position=0;

    public GridViewAdapter(ArrayList<String> strings, Activity activity) {
        this.strings = strings;
        this.activity = activity;
        selectedPositions = new ArrayList<>();
        disable_Position= DailyRoutineActivity.dis_Position;

    }

    @Override
    public boolean isEnabled(int position) {

        return (disable_Position <= position);
    }


    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItemView customView = (convertView == null) ? new GridItemView(activity) : (GridItemView) convertView;
        customView.display(strings.get(position), selectedPositions.contains(position),position);
        return customView;
    }
}
