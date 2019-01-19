package com.simplestepapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplestepapp.R;
import com.simplestepapp.models.QAnswerModel;

import java.util.ArrayList;

import static com.simplestepapp.activities.ConclusionActivity.imagesarray;

/**
 * Created by Srinivas on 1/19/2019.
 */

public class ConRecyclerAdapter extends RecyclerView.Adapter<ConRecyclerAdapter.MyHolder> {
    private ArrayList<String> timeSlots, colorCodes;
    private Context context;
    private int layoutResourceId;
    private ArrayList<Integer> selectedPositions;
    private static int selectedIndex = 0;
    String colorCode;
    ArrayList<QAnswerModel> qAnswerModels;

    private MyHolder viewHolder;

    public ConRecyclerAdapter(Context context, ArrayList<String> timeSlots, ArrayList<QAnswerModel> qAnswerModels) {
        this.timeSlots = timeSlots;
        this.context = context;
        this.qAnswerModels = qAnswerModels;
        selectedPositions = new ArrayList<>();
        colorCodes = new ArrayList<>();
        for (int i = 0; i < qAnswerModels.size(); i++) {
            selectedPositions.add(qAnswerModels.get(i).getS_Position());
            colorCodes.add(qAnswerModels.get(i).getColorCode());

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conclusion_item, parent, false);
        viewHolder = new MyHolder(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.setIsRecyclable(false);

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
                    holder.img_Con.setVisibility(View.VISIBLE);
                    holder.img_Con.setBackgroundResource(imagesarray[i]);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        try {
            return timeSlots.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        AppCompatTextView txt_ConTimeslot;
        AppCompatImageView img_Con;
        CardView card;

        MyHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.txt_ConTimeslot = itemView.findViewById(R.id.txt_ConTimeslot);
            this.img_Con = itemView.findViewById(R.id.img_Con);
            this.card = itemView.findViewById(R.id.card);

        }
    }
}
