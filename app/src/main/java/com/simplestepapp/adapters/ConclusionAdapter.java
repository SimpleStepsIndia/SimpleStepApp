package com.simplestepapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class ConclusionAdapter extends ArrayAdapter {
    private ArrayList<String> timeSlots, colorCodes;
    private Context context;
    private int layoutResourceId;
    private ArrayList<Integer> selectedPositions;

    private static int selectedIndex = 0;
    String colorCode;

    ArrayList<QAnswerModel> qAnswerModels;
    ArrayList<Integer> repeatedSlots;


    public ConclusionAdapter(Context context, int resourceId, ArrayList<String> timeSlots, ArrayList<QAnswerModel> qAnswerModels,
                             ArrayList<Integer> repeatedSlots) {
        super(context, resourceId, qAnswerModels);
        this.context = context;
        this.layoutResourceId = resourceId;
        this.timeSlots = timeSlots;
        this.qAnswerModels = qAnswerModels;
        this.repeatedSlots = repeatedSlots;
        selectedPositions = new ArrayList<>();
        colorCodes = new ArrayList<>();

        for (int i = 0; i < qAnswerModels.size(); i++) {
            selectedPositions.add(repeatedSlots.get(i));
            colorCodes.add(qAnswerModels.get(i).getColorCode());
        }
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

    @NonNull
    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View row = null;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder();
            holder.card = row.findViewById(R.id.card);
            holder.img_Con = row.findViewById(R.id.img_Con);
            holder.text = row.findViewById(R.id.txt_ConTimeslot);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.text.setText(timeSlots.get(position));
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
                    holder.text.setTextColor(Color.WHITE);
                    holder.img_Con.setVisibility(View.VISIBLE);
                    holder.img_Con.setBackgroundResource(imagesarray[i]);
                    // break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return row;

    }

    private static class ViewHolder {
        public AppCompatTextView text;
        public AppCompatImageView img_Con;
        public CardView card;
    }

}