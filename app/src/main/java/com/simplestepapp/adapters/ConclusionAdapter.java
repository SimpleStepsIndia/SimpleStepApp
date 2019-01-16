package com.simplestepapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplestepapp.R;
import com.simplestepapp.models.QAnswerModel;

import java.util.ArrayList;
import java.util.List;

import static com.simplestepapp.activities.ConclusionActivity.imagesarray;

/**
 * Created by Srinivas on 12/14/2018.
 */

public class ConclusionAdapter extends BaseAdapter {
    private ArrayList<String> timeSlots, colorCodes;
    private Context context;
    public ArrayList<Integer> selectedPositions;

    private static int selectedIndex = 0;
    String colorCode;

    ArrayList<QAnswerModel> qAnswerModels;


    private LayoutInflater inflater;

    public ConclusionAdapter(Context context, ArrayList<String> timeSlots, ArrayList<QAnswerModel> qAnswerModels) {
        this.context = context;
        this.timeSlots = timeSlots;
        this.qAnswerModels = qAnswerModels;
        selectedPositions = new ArrayList<>();
        colorCodes = new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < qAnswerModels.size(); i++) {
            selectedPositions.add(qAnswerModels.get(i).getS_Position());
            colorCodes.add(qAnswerModels.get(i).getColorCode());

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


        View v;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.conclusion_item, parent, false);
        } else {
            v = (View) convertView;
        }
        TextView text = v.findViewById(R.id.txt_ConTimeslot);
        text.setText(timeSlots.get(position));
        CardView card = v.findViewById(R.id.card);
        AppCompatImageView img_Con = v.findViewById(R.id.img_Con);

        try {


            for (int i = 0; i < selectedPositions.size(); i++) {
                if (position == selectedPositions.get(i)) {
                    colorCode = colorCodes.get(i);

                    if ("G".equals(colorCode)) {
                        card.setBackgroundColor(Color.GREEN);
                    } else if ("B".equals(colorCode)) {
                        card.setBackgroundColor(Color.BLUE);
                    } else if ("O".equals(colorCode)) {
                        card.setBackgroundColor(Color.YELLOW);
                    } else if ("R".equals(colorCode)) {
                        card.setBackgroundColor(Color.RED);
                    }
                    text.setTextColor(Color.WHITE);
                    img_Con.setBackgroundResource(imagesarray[i]);
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;


        /*Holder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.conclusion_item, null);
            holder = new Holder(view);
            holder.txt_ConTimeslot = view.findViewById(R.id.txt_ConTimeslot);
            holder.card = view.findViewById(R.id.card);
            holder.img_Con = view.findViewById(R.id.img_Con);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txt_ConTimeslot.setText(timeSlots.get(position));
        try {


            for (int i = 0; i < selectedPositions.size(); i++) {
                if (position == selectedPositions.get(i)) {
                    colorCode = colorCodes.get(i);

                    if ("G".equals(colorCode)) {
                        holder.card.setBackgroundColor(Color.GREEN);
                    } else if ("B".equals(colorCode)) {
                        holder.card.setBackgroundColor(Color.BLUE);
                    } else if ("O".equals(colorCode)) {
                        holder.card.setBackgroundColor(Color.YELLOW);
                    } else if ("R".equals(colorCode)) {
                        holder.card.setBackgroundColor(Color.RED);
                    }
                    holder.txt_ConTimeslot.setTextColor(Color.WHITE);
                    holder.img_Con.setBackgroundResource(imagesarray[i]);
                    break;
                }

            }
        } catch (Exception e) {
            holder.txt_ConTimeslot.setBackgroundColor(Color.WHITE);
            holder.txt_ConTimeslot.setTextColor(Color.GREEN);
        }
        // view.setLayoutParams(new ViewGroup.LayoutParams(135, 60));
        return view;*/
    }

    private class Holder extends RecyclerView.ViewHolder {
        TextView txt_ConTimeslot;
        CardView card;
        AppCompatImageView img_Con;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}