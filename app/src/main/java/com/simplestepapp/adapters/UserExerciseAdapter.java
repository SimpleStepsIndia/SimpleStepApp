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
import com.simplestepapp.models.exercise.UExercise;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Srinivas on 2/19/2019.
 */

public class UserExerciseAdapter extends BaseAdapter {

    public Activity activity;
    List<UExercise> itemList;
    private static LayoutInflater inflater = null;

    UExercise uExercise;

    public UserExerciseAdapter(Activity activity, List<UExercise> itemList) {
        this.activity = activity;
        this.itemList = itemList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_exercise, null);

        TextView name = vi.findViewById(R.id.tvName);
        ImageView thumbnail = vi.findViewById(R.id.ivThumbnail);
        CheckBox selectedExercise = vi.findViewById(R.id.cbChecked);

        uExercise = itemList.get(position);

        selectedExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemList.get(position).setSelected(true);
                }
            }
        });

        if (uExercise.getExerciseId().getExerciseUrl() != null) {
            Picasso.with(activity).load(getYoutubeThumbnailUrlFromVideoUrl(uExercise.getExerciseId().getExerciseUrl())).placeholder(android.R.drawable.ic_media_play).into(thumbnail);
            name.setText(uExercise.getExerciseId().getName());
            selectedExercise.setChecked(uExercise.getSelected());
        }
        return vi;
    }


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        return "http://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
    }

    private static String getYoutubeVideoIdFromUrl(String inUrl) {
        inUrl = inUrl.replace("&feature=youtu.be", "");
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
