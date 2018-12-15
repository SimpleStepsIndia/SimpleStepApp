package com.simplestepapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplestepapp.R;

import java.util.ArrayList;
import java.util.List;



public class CustomAdapter extends BaseAdapter {
    private ArrayList<String> timeSlots;
    private Context context;
    public List<Integer> selectedPositions;

    private static int selectedIndex=0;


    private LayoutInflater inflater;

    public CustomAdapter(Context context,ArrayList<String> timeSlots) {
        this.context=context;
        this.timeSlots=timeSlots;
        selectedPositions=new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return timeSlots.size();
    }

    @Override
    public Object getItem(int position) {
        return timeSlots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
            holder = new Holder();
            holder.txt_Timeslot =view.findViewById(R.id.txt_Timeslot);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txt_Timeslot.setText(timeSlots.get(position));

        if (selectedIndex!= -1 && position == selectedIndex) {
            holder.txt_Timeslot.setBackgroundColor(Color.BLUE);
            holder.txt_Timeslot.setTextColor(Color.WHITE);
        } else {
            holder.txt_Timeslot.setBackgroundColor(Color.WHITE);
            holder.txt_Timeslot.setTextColor(Color.BLUE);
        }
       // view.setLayoutParams(new ViewGroup.LayoutParams(135, 60));
        return view;
    }

    private class Holder {
        TextView txt_Timeslot;
    }
}
