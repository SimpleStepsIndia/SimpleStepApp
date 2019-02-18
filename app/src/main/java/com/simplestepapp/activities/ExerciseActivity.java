package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.simplestepapp.R;
import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.models.UserExerciseMaster;
import com.simplestepapp.network.Constants;
import com.simplestepapp.network.ErrorCodes;
import com.simplestepapp.network.LakmeCallBack;
import com.simplestepapp.network.MyCallBack;
import com.simplestepapp.network.RestClient;
import com.simplestepapp.utils.AppConfig;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExerciseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    RestClient mRestClient;
    String mStrReps, mStrSets,mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mStrEndTime, mStrGapTime, mStrInitialStartTime;
    Exercise mSelectedExercise;

    @Bind(R.id.tvExercise)
    TextView tvExercises;

    @Bind(R.id.tvSets)
    TextView tvSets;

    @Bind(R.id.tvReps)
    TextView tvReps;

    @Bind(R.id.tvName)
    TextView tvName;

    @Bind(R.id.btStart)
    Button btStart;

    @Bind(R.id.btStop)
    Button btStop;

    YouTubePlayer youTubePlayer;
    List<Exercise> selectedExercises;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        mRestClient = new RestClient();
        setData();

        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        repsEntered = Integer.parseInt(mStrReps);

        tvExercises.setText(String.valueOf(mStrSelectedExercices.split(",").length));
        tvReps.setText(mStrReps);
        tvSets.setText(mStrSets);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStart.setVisibility(View.GONE);
                btStop.setVisibility(View.VISIBLE);
                mStrStartTime = getDateTime();
                if (playCount == 0 && setsCount == 0) {
                    mStrGapTime = "0";
                    mStrInitialStartTime = mStrStartTime;
                } else {
                    mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                }
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrEndTime = getDateTime();
                int mEmployeeId = 0;
                //mEmployeeId = QuickUtils.prefs.getInt(Constants.USERID, mEmployeeId);
                UserExercise usrExr = new UserExercise();
                usrExr.setExerciseid(mSelectedExercise.getId());
                usrExr.setSet(mSelectedExercise.getSet()+1);
                usrExr.setReps(Integer.valueOf(mStrReps));
                usrExr.setUserid(Integer.valueOf(mEmployeeId));
                usrExr.setDate(getDateTime());
                usrExr.setStart(mStrStartTime);
                usrExr.setEnd(mStrEndTime);
                usrExr.setGap(mStrGapTime);
                usrExr.setSlno(mSelectedExercise.getSet()+1);
                usrExr.setExerciseslno(mSelectedExercise.getSlno());
                usrExr.setMasterid(Integer.parseInt(mStrMasterId));
                usrExr.setWorkouttime(getDiffDuration(mStrStartTime,mStrEndTime));

                final ProgressDialog pd = new ProgressDialog(ExerciseActivity.this);
                pd.setMessage("Please wait..");
                pd.setCancelable(false);
                pd.show();
                mRestClient.insertUserExercise(usrExr, new LakmeCallBack<UserExercise>() {
                    @Override
                    public void onFailure(String s, ErrorCodes errorCodes) {
                        Toast.makeText(ExerciseActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UserExercise userExercise) {
                        pd.dismiss();

                        playCount++;//1

                        if (playCount < selectedExercises.size()) {

                            mStrSelectedVideo = selectedExercises.get(playCount).getVideourl();
                            selectedExercises.get(playCount).setSet(setsCount);
                            mSelectedExercise = selectedExercises.get(playCount);
                            tvName.setText(selectedExercises.get(playCount).getSlno() + "." + selectedExercises.get(playCount).getName());
                            btStart.setVisibility(View.VISIBLE);
                            btStop.setVisibility(View.GONE);
                            youTubePlayer.cueVideo(mStrSelectedVideo);

                            if ((playCount + 1) % 2 == 0) {
                                setsCount++;
                                if (setsCount < Integer.parseInt(mStrSets)) {
                                    playCount = playCount - 2;
                                } else {
                                    setsCount = 0;
                                }
                            } else if (playCount == selectedExercises.size() - 1) {
                                setsCount++;
                                if (setsCount < Integer.parseInt(mStrSets)) {
                                    playCount = playCount - 1;
                                } else {
                                    setsCount = 0;
                                }
                            }
                        } else {
                            //Toast.makeText(ExerciseActivity.this, "exercises ended", Toast.LENGTH_LONG).show();
                            final ProgressDialog pd = new ProgressDialog(ExerciseActivity.this);
                            pd.setMessage("Please wait..");
                            pd.setCancelable(false);
                            pd.show();
                            UserExerciseMaster userExerciseMaster = new UserExerciseMaster();
                            userExerciseMaster.setStart(mStrInitialStartTime);
                            userExerciseMaster.setEnd(mStrEndTime);
                            userExerciseMaster.setTotaltime(getDiffDuration(mStrInitialStartTime,mStrEndTime));

                            mRestClient.updateUserExerciseMaster(mStrMasterId,userExerciseMaster, new LakmeCallBack<UserExerciseMaster>() {
                                @Override
                                public void onFailure(String s, ErrorCodes errorCodes) {
                                    Toast.makeText(ExerciseActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(UserExerciseMaster userExercise) {
                                    pd.dismiss();
                                    Intent intent = new Intent(ExerciseActivity.this, ExerciseResultActivity.class);
                                    intent.putExtra("sets", mStrSets);
                                    intent.putExtra("reps", mStrReps);
                                    intent.putExtra("master_id", mStrMasterId);
                                    intent.putExtra("selected_videos", mStrSelectedExercices);
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                });
            }
        });
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle(("Are you sure to exit?"))
                .setNegativeButton("Cancel", null) // dismisses by default
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //super.onBackPressed();
                        finish();
                    }
                })
                .create()
                .show();

    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer = player;
            player.cueVideo(mStrSelectedVideo); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(AppConfig.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


    private void setData() {
        final ProgressDialog pd = new ProgressDialog(ExerciseActivity.this);
        pd.setMessage("Please wait..");
        pd.setCancelable(false);
        pd.show();
        mRestClient.getExercises(new MyCallBack<List<Exercise>>() {
            @Override
            public void onFailure(String s, ErrorCodes errorCodes) {
                Toast.makeText(ExerciseActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List<Exercise> campaignRowDatas) {
                pd.dismiss();
                selectedExercises = new ArrayList<Exercise>();
                for (int i = 0; i < campaignRowDatas.size(); i++) {
                    if (mStrSelectedExercices.contains(String.valueOf(campaignRowDatas.get(i).getId()))) {
                        selectedExercises.add(campaignRowDatas.get(i));
                    }
                }
                mStrSelectedVideo = selectedExercises.get(playCount).getVideourl();
                selectedExercises.get(playCount).setSet(setsCount);
                mSelectedExercise = selectedExercises.get(playCount);
                tvName.setText(selectedExercises.get(playCount).getSlno() + "." + selectedExercises.get(playCount).getName());
                youTubeView.initialize(AppConfig.YOUTUBE_API_KEY, ExerciseActivity.this);
            }
        });
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
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