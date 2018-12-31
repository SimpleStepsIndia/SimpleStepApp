package com.simplestepapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplestepapp.R;
import com.simplestepapp.models.QAnswerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srinivas on 12/14/2018.
 */

public class ConclusionAdapter extends BaseAdapter {
    private ArrayList<String> timeSlots;
    private Context context;
    public ArrayList<Integer> selectedPositions;

    private static int selectedIndex=0;

    ArrayList<QAnswerModel> qAnswerModels;


    private LayoutInflater inflater;

    public ConclusionAdapter(Context context, ArrayList<String> timeSlots, ArrayList<QAnswerModel> qAnswerModels) {
        this.context=context;
        this.timeSlots=timeSlots;
        this.qAnswerModels=qAnswerModels;
        selectedPositions=new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i=0;i<qAnswerModels.size();i++){
            selectedPositions.add(qAnswerModels.get(i).getS_Position());
        }
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
            view = inflater.inflate(R.layout.conclusion_item, null);
            holder = new Holder();
            holder.txt_ConTimeslot =view.findViewById(R.id.txt_ConTimeslot);
            holder.card=view.findViewById(R.id.card);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txt_ConTimeslot.setText(timeSlots.get(position));
        try {


            for (int i=0;i<selectedPositions.size();i++){
                if (position==selectedPositions.get(i)){
                    holder.card.setBackgroundColor(Color.GREEN);
                    holder.txt_ConTimeslot.setTextColor(Color.WHITE);
                    break;
                }

            }
        }catch (Exception e){
            holder.txt_ConTimeslot.setBackgroundColor(Color.WHITE);
            holder.txt_ConTimeslot.setTextColor(Color.GREEN);
        }
        // view.setLayoutParams(new ViewGroup.LayoutParams(135, 60));
        return view;
    }

    private class Holder {
        TextView txt_ConTimeslot;
        CardView card;
    }
}