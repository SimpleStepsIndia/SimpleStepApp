package com.simplestepapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.activities.BottomNavigationActivity;
import com.simplestepapp.models.exercise.ExerciseModel;
import com.simplestepapp.models.exercise.UExercise;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.MKPlayer;
import com.simplestepapp.utils.SessionManager;
import com.simplestepapp.utils.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class FreeStyleVideoPlayerFragment extends Fragment {
    MKPlayer mkplayer;
    int total_Sets = 3;
    int sets = 1;
    int playCount = 0, repsEntered = 0, setsCount = 1, repeatCount = 0;
    String mStrReps, mStrSets="3", mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mIntialStartTime, mStrEndTime, mStrGapTime, mStrRestpGapTime="",
            mStrInitialStartTime, token;
    AppCompatTextView txt_Sets, txt_wrkOutTime, txt_RestTime, txt_TotalWrkTime, txt_WrkOut_Name;
    AppCompatButton btStart, btStop;
    ImageView app_video_lock;
    View app_video_box;
    boolean totTimeStart = false;

    private int ms = 0, msTot = 0;
    private int seconds = 0, secondsTot = 0;
    private int minutes = 0, minutesTot = 0;
    private Timer timer, timerTot;
    private boolean running = false, runningTot = false;
    LinearLayout lyt_Sets, lyt_WorkOutTime, lyt_RestTime;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    UExercise uExercise;
    public static List<UExercise> list_Exercises = new ArrayList<>();

    String userName = "", eMailId = "", str_UserID = "", userExerciseId = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.profile_activity, container, false);
        initviews(v);
        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            str_UserID = user.get(SessionManager.KEY_USERID);
        }

        getUserExcercises();
        mkplayer = new MKPlayer(getActivity());


        updateTimerText();
        updateTotalTimerText();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!runningTot) {
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
                String uExr_Id = uExercise.getExerciseId().get_id();
                try {
                    userExerciseId = list_Exercises.get(playCount).get_id();
                    postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime);
                }catch (Exception e){
                    e.printStackTrace();
                    userExerciseId = list_Exercises.get(0).get_id();
                    postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime);
                }
            }
        });
        return v;
    }

    private void initviews(View v) {
        btStart = v.findViewById(R.id.btStart);
        btStop = v.findViewById(R.id.btStop);
        app_video_lock = v.findViewById(R.id.app_video_lock);
        app_video_box = v.findViewById(R.id.app_video_box);
        txt_Sets = v.findViewById(R.id.txt_Sets);
        txt_wrkOutTime = v.findViewById(R.id.txt_wrkOutTime);
        txt_RestTime = v.findViewById(R.id.txt_RestTime);
        lyt_Sets = v.findViewById(R.id.lyt_Sets);
        lyt_WorkOutTime = v.findViewById(R.id.lyt_WorkOutTime);
        lyt_RestTime = v.findViewById(R.id.lyt_RestTime);
        txt_TotalWrkTime = v.findViewById(R.id.txt_TotalWrkTime);
        txt_WrkOut_Name=v.findViewById(R.id.txt_WrkOut_Name);
        lyt_RestTime.setVisibility(View.GONE);
    }

    private void getUserExcercises() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userid", "5c5e69c9bf1959001707f312");
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest request_Excercise = new JsonObjectRequest(Request.Method.GET, AppConfig.getUser_Excercises, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("Excercise Re", "" + response.toString());
                try {
                    list_Exercises = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.getString("userExercise"));
                    list_Exercises = new Gson().fromJson(String.valueOf(jsonArray), ExerciseModel.class);
                    if (list_Exercises.size() > 0) {
                        txt_Sets.setText("" + setsCount + "/" + total_Sets);
                        uExercise = list_Exercises.get(playCount);
                        mkplayer.play(list_Exercises.get(playCount).getExerciseId().getExerciseUrl());
                        txt_WrkOut_Name.setText(list_Exercises.get(playCount).getExerciseId().getName());
                        mkplayer.onComplete(new Runnable() {
                            @Override
                            public void run() {
                                mkplayer.play(list_Exercises.get(playCount).getExerciseId().getExerciseUrl());
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

                    } else {
                        Toaster.showInfoMessage("No Workouts !");
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzVlNjljOWJmMTk1OTAwMTcwN2YzMTIiLCJsb2NhbExvZ2luSWQiOiI1YzVlNjljOWJmMTk1OTAwMTcwN2YzMTIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYWRheWFuYW5kOEBnbWFpbC5jb20iLCJleHAiOjE1NTg3NjY4OTMzLCJpYXQiOjE1NTM1ODI4OTN9.R6txDzisJZYORX3obDDGxIiTHWfrOwC3oq-MBgN9tdI");
                return headers;
            }
        };
        requestQueue.add(request_Excercise);
    }

    private void postWorkoutInfo(final String uExrId, final String userExerciseId, final String startTime, final String endTime) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userExerciseId", userExerciseId);
        params.put("exerciseId", uExrId);
        params.put("setId", mStrSets);
        params.put("durationInSec", String.valueOf(Math.abs(Integer.parseInt(mStrGapTime))));
        params.put("restDurationInSec", !mStrRestpGapTime.equals("") ? mStrRestpGapTime : "0");
        params.put("createdDate", getDateTime());

        JSONObject jsonObject = new JSONObject(params);
        JSONObject object_post = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            object_post.put("workouts", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request_workoutInfo = new JsonObjectRequest(Request.Method.POST, AppConfig.postUser_Workout_Info, object_post, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {

                    playCount++;
                    Log.d("WrktResElse", "PlayCount" + playCount);

                    if (playCount < list_Exercises.size()) {

                        mStrSelectedVideo = list_Exercises.get(playCount).getExerciseId().getExerciseUrl();
                        txt_WrkOut_Name.setText(list_Exercises.get(playCount).getExerciseId().getName());
                        btStart.setVisibility(View.VISIBLE);
                        btStop.setVisibility(View.GONE);
                        mkplayer.play(mStrSelectedVideo);
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
                                txt_Sets.setText("" + setsCount + "/" + total_Sets);
                            } else {
                                setsCount = 0;
                            }
                        } else if (playCount == list_Exercises.size() - 1) {
                            setsCount++;
                            if (setsCount < Integer.parseInt(mStrSets)) {
                                playCount = playCount - 1;
                                txt_Sets.setText("" + setsCount + "/" + total_Sets);
                            } else {
                                setsCount = 0;
                            }
                        }

                    } else {
                        stopTotTimer();
                        Toaster.showInfoMessage("Workouts Completed !");
                        mkplayer.stop();
                        Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), BottomNavigationActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzVlNjljOWJmMTk1OTAwMTcwN2YzMTIiLCJsb2NhbExvZ2luSWQiOiI1YzVlNjljOWJmMTk1OTAwMTcwN2YzMTIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYWRheWFuYW5kOEBnbWFpbC5jb20iLCJleHAiOjE1NTg3NjY4OTMzLCJpYXQiOjE1NTM1ODI4OTN9.R6txDzisJZYORX3obDDGxIiTHWfrOwC3oq-MBgN9tdI");

                //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJsb2NhbExvZ2luSWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYXNoaXJpc2hhOUBnbWFpbC5jb20iLCJleHAiOjE1NTUzMTIzMTE4LCJpYXQiOjE1NTAxMjgzMTF9.30SMbAS6lZER5uTjD7cJeuQ7tyWhi4IwwcXpTiMM1pc");
                return headers;
            }
        };
        requestQueue.add(request_workoutInfo);
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
        Objects.requireNonNull(this.getActivity()).runOnUiThread(timerTick);
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
        Objects.requireNonNull(getActivity()).runOnUiThread(timerTotTick);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mkplayer.stop();
    }
}
