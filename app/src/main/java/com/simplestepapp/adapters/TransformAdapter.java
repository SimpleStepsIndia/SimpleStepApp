package com.simplestepapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simplestepapp.R;
import com.simplestepapp.activities.DailyRtneFreStyleWrktActivity;
import com.simplestepapp.activities.FreStyleVideoPlayerActivity;
import com.simplestepapp.activities.UnityActivity;

import java.util.ArrayList;
import java.util.Objects;

public class TransformAdapter extends BaseAdapter {

    private ArrayList<String> list_Transforms;
    private Context context;
    private LayoutInflater inflater;
    private Integer[] imgArray_TransForms = {R.drawable.vslzemrngrtne, R.drawable.trckmngrtne, R.drawable.pfrestylewrkt, R.drawable.peasyoga,
            R.drawable.affermations, R.drawable.gratittude, R.drawable.sunbath,R.drawable.waterintake, R.drawable.to_do,R.drawable.to_do};
    String token;

    public TransformAdapter(Context context, ArrayList<String> list_Transforms,String token) {
        this.context=context;
        this.list_Transforms=list_Transforms;
        this.token=token;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_Transforms.size();
    }

    @Override
    public Object getItem(int position) {
        return list_Transforms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       Holder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_frag_transforms, null);
            holder = new Holder();
            holder.txt_TransformText =view.findViewById(R.id.txt_TransForms);
            holder.img_transforms =view.findViewById(R.id.img_transforms);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txt_TransformText.setText(list_Transforms.get(position));
        holder.img_transforms.setImageResource(imgArray_TransForms[position]);
        holder.txt_TransformText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Positon", "" + position);
                if (position == 0) {
                    Intent intent = new Intent(context, UnityActivity.class);
                    intent.putExtra("ScreenKey","visualization");
                    intent.putExtra("AndroidValue", ""+token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(context, UnityActivity.class);
                    intent.putExtra("ScreenKey","visualizationMrt");
                    intent.putExtra("AndroidValue", ""+token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(context, FreStyleVideoPlayerActivity.class);
                    intent.putExtra("sets", "3");
                    intent.putExtra("reps", "5");
                    intent.putExtra("selected_videos", "461665");
                    intent.putExtra("master_id", "5545688655");
                    intent.putExtra("FreStyleFrom","Android");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);
                }
            }
        });

        return view;
    }
    private class Holder {
        TextView txt_TransformText;
        AppCompatImageView img_transforms;
    }

}
