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
import android.widget.LinearLayout;

import com.simplestepapp.R;
import com.simplestepapp.utils.MKPlayer;
import com.simplestepapp.utils.Toaster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class FreStyleVideoPlayerActivity extends AppCompatActivity {
    MKPlayer mkplayer;
    int total_Sets = 5;
    int sets = 1;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mIntialStartTime, mStrEndTime, mStrGapTime, mStrRestpGapTime,
            mStrInitialStartTime, token;
    AppCompatTextView txt_Sets, txt_wrkOutTime, txt_RestTime, txt_TotalWrkTime;
    AppCompatButton btStart, btStop;
    ImageView app_video_lock;
    ArrayList<String> list_VideoUrls;
    View app_video_box;
    boolean totTimeStart=false;

    private int ms = 0, msTot = 0;
    private int seconds = 0, secondsTot = 0;
    private int minutes = 0, minutesTot = 0;
    private Timer timer, timerTot;
    private boolean running = false, runningTot = false;
    LinearLayout lyt_Sets, lyt_WorkOutTime, lyt_RestTime;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frestylevideoplayer);
        initviews();

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
        txt_Sets.setText("" + sets + "/" + total_Sets);
        mkplayer.play(list_VideoUrls.get(playCount));
        mkplayer.onComplete(new Runnable() {
            @Override
            public void run() {
                mkplayer.play(list_VideoUrls.get(playCount));
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

        updateTimerText();
        updateTotalTimerText();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!runningTot){
                    startTotTimer();
                }
                if (running) {
                    stopTimer();
                }
                sets = 1;
                lyt_WorkOutTime.setVisibility(View.VISIBLE);
                lyt_RestTime.setVisibility(View.GONE);
                ms = 0;
                seconds = 0;
                minutes = 0;
                startTimer();
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
                stopTimer();
                sets = 1;
                lyt_WorkOutTime.setVisibility(View.GONE);
                lyt_RestTime.setVisibility(View.VISIBLE);
                ms = 0;
                seconds = 0;
                minutes = 0;
                startTimer();
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
                    //mkplayer.play(list_VideoUrls.get(playCount));
                    mkplayer.onComplete(new Runnable() {
                        @Override
                        public void run() {
                            mkplayer.play(mStrSelectedVideo);
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
                    stopTotTimer();
                    Toaster.showInfoMessage("Workouts Completed !");
                    Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initviews() {
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);
        app_video_lock = findViewById(R.id.app_video_lock);
        app_video_box = findViewById(R.id.app_video_box);
        txt_Sets = findViewById(R.id.txt_Sets);
        txt_wrkOutTime = findViewById(R.id.txt_wrkOutTime);
        txt_RestTime = findViewById(R.id.txt_RestTime);
        lyt_Sets = findViewById(R.id.lyt_Sets);
        lyt_WorkOutTime = findViewById(R.id.lyt_WorkOutTime);
        lyt_RestTime = findViewById(R.id.lyt_RestTime);
        txt_TotalWrkTime = findViewById(R.id.txt_TotalWrkTime);
        lyt_RestTime.setVisibility(View.GONE);
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

    private void stopTimer() {
        if (running) {
            running = false;
            timer.cancel();
        }
    }

    private void startTimer() {
        timer = new Timer();
        running = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runTimer();
            }
        }, 0, 100);
    }

    private void runTimer() {
        this.runOnUiThread(timerTick);
    }

    private void updateMs() {
        ms++;
        if (ms == 10) {
            updateSeconds();
        }
    }

    private void updateSeconds() {
        ms = 0;
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
    }

    private void updateTimerText() {
        txt_wrkOutTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, ms));
        txt_RestTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, ms));
    }

    private Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            updateTimerText();
            updateMs();
        }
    };

    private void stopTotTimer() {
        if (runningTot) {
            runningTot = false;
            timerTot.cancel();
        }
    }

    private void startTotTimer() {
        timerTot = new Timer();
        runningTot = true;
        timerTot.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runTotTimer();
            }
        }, 0, 100);
    }

    private void runTotTimer() {
        this.runOnUiThread(timerTotTick);
    }

    private void updateTotMs() {
        msTot++;
        if (msTot == 10) {
            updateTotSeconds();
        }
    }

    private void updateTotSeconds() {
        msTot = 0;
        secondsTot++;
        if (secondsTot == 60) {
            secondsTot = 0;
            minutesTot++;
        }
    }

    private Runnable timerTotTick = new Runnable() {
        @Override
        public void run() {
            updateTotalTimerText();
            updateTotMs();
        }
    };

    private void updateTotalTimerText() {
        txt_TotalWrkTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutesTot, secondsTot, msTot));
    }
}
