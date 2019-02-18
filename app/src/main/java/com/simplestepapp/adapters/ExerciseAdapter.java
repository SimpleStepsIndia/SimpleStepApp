package com.simplestepapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.simplestepapp.R;
import com.simplestepapp.data.Exercise;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public class ExerciseAdapter extends BaseAdapter {

    private Activity activity;
    List<Exercise> itemList;
    private static LayoutInflater inflater=null;
    Exercise dataForQuestion;

    public ExerciseAdapter(Activity a, List<Exercise> itemlist) {
        activity = a;
        itemList =itemlist;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return itemList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_exercise, null);

        TextView name = (TextView)vi.findViewById(R.id.tvName);
        ImageView thumbnail = (ImageView)vi.findViewById(R.id.ivThumbnail);
        CheckBox selectedExercise = (CheckBox) vi.findViewById(R.id.cbChecked);


        dataForQuestion = itemList.get(position);

        selectedExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    itemList.get(position).setIsSelected(true);
                }
            }
        });
        // Setting all values in listview
        if(dataForQuestion.getVideourl()!=null) {
            Picasso.with(activity).load(dataForQuestion.getThumbnail()).placeholder(android.R.drawable.ic_media_play).into(thumbnail);
            name.setText(String.valueOf(dataForQuestion.getSlno())+". "+dataForQuestion.getName());
            selectedExercise.setChecked(dataForQuestion.getIsSelected());
        }
        return vi;
    }
}
