package com.simplestepapp.adapters;

import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplestepapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.ViewHolder> {

    private List<String> data;
    private int selectedIndex = -1;


    public AgeAdapter(List<String> data) {
        this.data = data;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*Glide.with(holder.itemView.getContext())
                .load(data.get(position).getImage())
                .into(holder.image);*/
        holder.txt_Age.setText(data.get(position));

        if (selectedIndex != -1 && position == selectedIndex) {
            holder.txt_Age.setTextColor(Color.GREEN);
        }else{
            holder.txt_Age.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView txt_Age;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            txt_Age = itemView.findViewById(R.id.txt_Age);
        }
    }
}
