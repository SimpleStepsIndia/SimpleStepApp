package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.adapters.UserExerciseAdapter;
import com.simplestepapp.models.exercise.ExerciseModel;
import com.simplestepapp.models.exercise.UExercise;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;
import com.simplestepapp.utils.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FreStyleVideoPlayerActivity extends AppCompatActivity {
    int total_Sets = 3;
    int sets = 1;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mIntialStartTime, mStrEndTime, mStrGapTime, mStrRestpGapTime = "",
            mStrInitialStartTime, token;
    AppCompatTextView txt_Sets, txt_wrkOutTime, txt_TotalWrkTime, txt_WrkOut_Name, txt_ElpsdTime,txt_Elpsd_Time;
    AppCompatButton btStart, btStop;
    boolean totTimeStart = false;

    private int ms = 0, msTot = 0;
    private int seconds = 0, secondsTot = 0;
    private int minutes = 0, minutesTot = 0;
    private Timer timer, timerTot;
    private boolean running = false, runningTot = false;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    UExercise uExercise;
    public static List<UExercise> list_Exercises = new ArrayList<>();

    String userName = "", eMailId = "", str_UserID = "", userExerciseId = "";
    VideoView vdeoView;
    String filePath;
    CountDownTimer countDownTimer;
    AlertDialog alertDialog;
    ArrayList<String> list_UrlPaths;
    RelativeLayout lyt_Elpsd_Time;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frestylevideoplayer);
        initviews();

        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            str_UserID = user.get(SessionManager.KEY_USERID);
        }

        getUserExcercises();
        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        repsEntered = Integer.parseInt(mStrReps);

        updateTimerText();
        // updateTotalTimerText();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!runningTot) {
                    startTotTimer();
                }*/
                if (running) {
                    stopTimer();
                }
                sets = 1;
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
                minutes = 0;
                seconds = 0;
                ms = 0;
                updateTimerText();
                sets = 1;
                ms = 0;
                seconds = 0;
                minutes = 0;
                //startTimer();
                mStrEndTime = getDateTime();
                mStrGapTime = getDiffDuration(mStrEndTime, mStrStartTime);
                String uExr_Id = uExercise.getExerciseId().get_id();
                try {
                    userExerciseId = list_Exercises.get(playCount).get_id();
                    postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    userExerciseId = list_Exercises.get(0).get_id();
                    postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime);
                }
            }
        });
    }

    private void initviews() {
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);
        txt_Sets = findViewById(R.id.txt_Sets);
        txt_wrkOutTime = findViewById(R.id.txt_wrkOutTime);
        txt_TotalWrkTime = findViewById(R.id.txt_TotalWrkTime);
        txt_WrkOut_Name = findViewById(R.id.txt_WrkOut_Name);
        vdeoView = findViewById(R.id.vdeoView);
        lyt_Elpsd_Time=findViewById(R.id.lyt_Elpsd_Time);
        txt_Elpsd_Time=findViewById(R.id.txt_Elpsd_Time);
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
                        txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);

                        uExercise = list_Exercises.get(playCount);
                        txt_WrkOut_Name.setText(list_Exercises.get(playCount).getExerciseId().getName());
                        list_UrlPaths = new ArrayList<>();
                        list_UrlPaths = sessionManager.getVideoArrayList("FreStyleVideos");
                        if (null != list_UrlPaths) {
                            vdeoView.setVideoPath(list_UrlPaths.get(0));
                            vdeoView.start();
                            vdeoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    vdeoView.setVideoPath(list_UrlPaths.get(0));
                                    vdeoView.start();
                                }
                            });
                        } else {
                            downloadvideoFiles();
                        }

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

    private void downloadvideoFiles() {
        list_UrlPaths = new ArrayList<>();
        for (int i = 0; i < list_Exercises.size(); i++) {
            new DownloadFile().execute(list_Exercises.get(i).getExerciseId().getExerciseUrl());
        }
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
                    Log.d("PlayCount", "" + playCount);

                    if (playCount < list_Exercises.size()) {
                       // restTimeDialog(Integer.parseInt(list_Exercises.get(playCount).getRest()));
                        countDownTimerStart(Integer.parseInt(list_Exercises.get(playCount).getRest())*1000);
                        mStrSelectedVideo = list_UrlPaths.get(playCount);
                        txt_WrkOut_Name.setText(list_Exercises.get(playCount).getExerciseId().getName());
                        btStart.setVisibility(View.VISIBLE);
                        btStop.setVisibility(View.GONE);

                        vdeoView.setVideoPath(mStrSelectedVideo);
                        vdeoView.start();
                        vdeoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                vdeoView.setVideoPath(mStrSelectedVideo);
                                vdeoView.start();
                            }
                        });

                        if ((playCount + 1) % 2 == 0) {
                            setsCount++;
                            txt_Sets.setText("" + (setsCount) + "/" + total_Sets);
                            if (setsCount < Integer.parseInt(mStrSets)) {
                                playCount = playCount - 2;

                            } else {
                                setsCount = 0;
                            }
                        } else if (playCount == list_Exercises.size() - 1) {
                            setsCount++;
                            if (setsCount < Integer.parseInt(mStrSets)) {
                                playCount = playCount - 1;
                                txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);
                            } else {
                                setsCount = 0;
                                txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);
                            }
                        } else {
                            txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);
                        }

                    } else {
                        stopTotTimer();
                        Toaster.showInfoMessage("Workouts Completed !");
                        Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
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

    public void countTimer(final int time) {

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt_ElpsdTime.setText("Elapsed Time 00:" + (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "");
            }

            @Override
            public void onFinish() {
                txt_ElpsdTime.setText("Time's Up!");
                alertDialog.dismiss();
            }
        }.start();
    }

    public void countDownTimerStart(final int time) {
        lyt_Elpsd_Time.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt_Elpsd_Time.setText("" + (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "");
            }

            @Override
            public void onFinish() {
                txt_Elpsd_Time.setText("00");
                lyt_Elpsd_Time.setVisibility(View.GONE);
            }
        }.start();
    }

    private void restTimeDialog(final int restTime) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FreStyleVideoPlayerActivity.this);
        LayoutInflater inflater = LayoutInflater.from(FreStyleVideoPlayerActivity.this);
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.resttimedialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        txt_ElpsdTime = view.findViewById(R.id.txt_ElpsdTime);
        countTimer(restTime * 1000);
    }



    @SuppressLint("StaticFieldLeak")
    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(FreStyleVideoPlayerActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                final int lengthOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                fileName = System.currentTimeMillis() + ".mp4";
                folder = Environment.getExternalStorageDirectory() + File.separator + "SimpleSteps/";
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                filePath = folder + fileName;
                list_UrlPaths.add(filePath);
                OutputStream output = new FileOutputStream(folder + fileName);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    final long finalTotal = total;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishProgress("" + (int) ((finalTotal * 100) / lengthOfFile));
                        }
                    });
                    output.write(data, 0, count);

                }
                output.flush();
                output.close();
                input.close();
                if (list_Exercises.size() == list_UrlPaths.size()) {
                    sessionManager.saveVideoArrayList(list_UrlPaths, "FreStyleVideos");
                }
                return "Downloaded at: " + folder + fileName;


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return "Something went wrong";
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
            vdeoView.setVideoPath(list_UrlPaths.get(0));
            vdeoView.start();
            vdeoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    vdeoView.setVideoPath(list_UrlPaths.get(0));
                    vdeoView.start();
                }
            });
        }
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
        // txt_RestTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, ms));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
