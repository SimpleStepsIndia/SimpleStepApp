package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.simplestepapp.R;
import com.simplestepapp.utils.MKPlayer;
import com.simplestepapp.utils.Toaster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FreStyleVideoPlayerActivity extends AppCompatActivity {
    MKPlayer mkplayer;
    int total_Sets = 3;
    int sets = 1;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mIntialStartTime, mStrEndTime, mStrGapTime, mStrRestpGapTime,
            mStrInitialStartTime, token;
    AppCompatTextView txt_Sets;
    AppCompatButton btStart, btStop;
    ImageView app_video_lock;
    ArrayList<String> list_VideoUrls;
    View app_video_box;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frestylevideoplayer);
        initviews();
        txt_Sets = findViewById(R.id.txt_Sets);
        mkplayer = new MKPlayer(this);
        list_VideoUrls = new ArrayList<>();
        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        repsEntered = Integer.parseInt(mStrReps);
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+1.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+2.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+3.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+1.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+2.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+3.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+1.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+2.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+3.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+1.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+2.mp4");
        list_VideoUrls.add("https://s3.ap-south-1.amazonaws.com/simplesteps123/Edited+3.mp4");
        txt_Sets.setText("" + sets);
        mkplayer.play(list_VideoUrls.get(playCount));
        mkplayer.onComplete(new Runnable() {
            @Override
            public void run() {
                sets = sets + 1;
                txt_Sets.setText("" + sets );
                mkplayer.play(list_VideoUrls.get(playCount));
                Log.d("Sets", "" + sets);
            }
        });

        mkplayer.setPlayerCallbacks(new MKPlayer.playerCallbacks() {
            @Override
            public void onNextClick() {
                Toaster.showInfoMessage("No videos!");
            }

            @Override
            public void onPreviousClick() {
                Toaster.showInfoMessage("No videos!");
            }
        });

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStart.setVisibility(View.GONE);
                btStop.setVisibility(View.VISIBLE);
                mStrStartTime = getDateTime();
                if (playCount == 0 && setsCount == 0) {
                    mStrGapTime = "0";
                    mStrInitialStartTime = mStrStartTime;
                    mIntialStartTime = getDateTime();
                } else {
                    mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                    mStrRestpGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrEndTime = getDateTime();
                mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                playCount++;
                Log.d("WrktResElse", "PlayCount" + playCount);

                if (playCount < list_VideoUrls.size()) {

                    // mStrSelectedVideo = selectedExercises.get(playCount).getExerciseId().getVideoId();
                    mStrSelectedVideo = list_VideoUrls.get(playCount);
                    btStart.setVisibility(View.VISIBLE);
                    btStop.setVisibility(View.GONE);
                    // youTubePlayer.cueVideo(mStrSelectedVideo);
                    mkplayer.play(mStrSelectedVideo);
                    mkplayer.play(list_VideoUrls.get(playCount));
                    mkplayer.onComplete(new Runnable() {
                        @Override
                        public void run() {
                            sets=0;
                            sets = sets + 1;
                            txt_Sets.setText("" + sets );
                            mkplayer.play(list_VideoUrls.get(playCount));
                            Log.d("Sets", "" + sets);
                        }
                    });

                    if ((playCount + 1) % 2 == 0) {
                        setsCount++;
                        if (setsCount < Integer.parseInt(mStrSets)) {
                            playCount = playCount - 2;
                        } else {
                            setsCount = 0;
                        }
                    } else if (playCount == list_VideoUrls.size() - 1) {
                        setsCount++;
                        if (setsCount < Integer.parseInt(mStrSets)) {
                            playCount = playCount - 1;
                        } else {
                            setsCount = 0;
                        }
                    }
                } else {
                    Log.d("WrktResElse", "ElseBlock");
                }
            }
        });
    }

    private void initviews() {
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);
        app_video_lock = findViewById(R.id.app_video_lock);
        app_video_box = findViewById(R.id.app_video_box);
    }

    private void hideButtons() {
        app_video_lock.setVisibility(View.GONE);

    }


    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDiffDuration(String StartTime, String EndTime) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        myFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date dt1 = myFormat.parse(StartTime);
            Date dt2 = myFormat.parse(EndTime);
            long diff = dt2.getTime() - dt1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));

            return String.valueOf(diffSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
