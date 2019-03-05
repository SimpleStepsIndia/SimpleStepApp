package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonObject;
import com.simplestepapp.R;
import com.simplestepapp.data.Exercise;
import com.simplestepapp.models.exercise.UExercise;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Srinivas on 2/19/2019.
 */

public class FreStlVideoPlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;

    YouTubePlayer youTubePlayer;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime,mIntialStartTime, mStrEndTime, mStrGapTime, mStrInitialStartTime, token;

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

    ArrayList<UExercise> selectedExercises;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    SessionManager sessionManager;

    UExercise uExercise;

    String userExerciseId;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_exercise);
        ButterKnife.bind(this);
        youTubeView = findViewById(R.id.youtube_view);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user_Info = sessionManager.getUserDetails();
            token = user_Info.get(SessionManager.KEY_TOKEN);
        }
        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        repsEntered = Integer.parseInt(mStrReps);

        tvExercises.setText(String.valueOf(mStrSelectedExercices.split(",").length));
        tvReps.setText(mStrReps);
        tvSets.setText(mStrSets);

        initialDataSet();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStart.setVisibility(View.GONE);
                btStop.setVisibility(View.VISIBLE);
                mStrStartTime = getDateTime();

                if (playCount == 0 && setsCount == 0) {
                    mStrGapTime = "0";
                    mStrInitialStartTime = mStrStartTime;
                    mIntialStartTime=getDateTime();
                } else {
                    mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrEndTime = getDateTime();
                mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                String uExr_Id = uExercise.get_id();
                userExerciseId = FreeStyleWorkActivity.list_Exercises.get(0).getUser();
                postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime);
            }
        });
    }

    private void initialDataSet() {
        selectedExercises = new ArrayList<UExercise>();
        for (int i = 0; i < FreeStyleWorkActivity.list_Exercises.size(); i++) {
            if (mStrSelectedExercices.contains(FreeStyleWorkActivity.list_Exercises.get(i).get_id())) {
                selectedExercises.add(FreeStyleWorkActivity.list_Exercises.get(i));
            }
        }
        mStrSelectedVideo = selectedExercises.get(playCount).getExerciseId().getVideoId();
        //selectedExercises.get(playCount).setSet(setsCount);
        uExercise = selectedExercises.get(playCount);
        tvName.setText(selectedExercises.get(playCount).getExerciseId().getName());
        youTubeView.initialize(AppConfig.YOUTUBE_API_KEY, FreStlVideoPlayActivity.this);
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
        params.put("restDurationInSec", String.valueOf(Math.abs(Integer.parseInt(getDiffDuration(mStrEndTime, mStrStartTime)))));
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

                    Log.d("Resp", "" + response.toString());

                    Log.d("WrktResElse", "PlayCount" + playCount);

                    if (playCount < selectedExercises.size()) {

                        mStrSelectedVideo = selectedExercises.get(playCount).getExerciseId().getVideoId();
                        uExercise = selectedExercises.get(playCount);
                        tvName.setText(selectedExercises.get(playCount).getExerciseId().getName());
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
                        Log.d("WrktResElse", "ElseBlock");
                        Intent intent = new Intent(getApplicationContext(), ExerciseResultActivity.class);
                        intent.putExtra("sets", mStrSets);
                        intent.putExtra("reps", mStrReps);
                        intent.putExtra("master_id", userExerciseId);
                        intent.putExtra("selected_videos", mStrSelectedExercices);
                        intent.putExtra("mStrStartTime", mStrStartTime);
                        intent.putExtra("mIntialStartTime", mIntialStartTime);
                        intent.putExtra("mStrEndTime", mStrEndTime);
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
                headers.put("Authorization", "token "+token);

                //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJsb2NhbExvZ2luSWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYXNoaXJpc2hhOUBnbWFpbC5jb20iLCJleHAiOjE1NTUzMTIzMTE4LCJpYXQiOjE1NTAxMjgzMTF9.30SMbAS6lZER5uTjD7cJeuQ7tyWhi4IwwcXpTiMM1pc");
                return headers;
            }
        };
        requestQueue.add(request_workoutInfo);
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
