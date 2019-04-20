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
import com.simplestepapp.models.exercise.UExerSummaryModel;
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
    int total_Sets = 2;
    int sets = 1;
    int playCount = 0, repsEntered = 0, setsCount = 0, repeatCount = 0, noOfsets = 1;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrSelectedVideo, mStrStartTime, mIntialStartTime, mStrEndTime, mStrGapTime, mStrRestpGapTime = "",
            token;
    public String mStrInitialStartTime, finalEndTime;
    AppCompatTextView txt_Sets, txt_Reps, txt_wrkOutTime, txt_TotalWrkTime, txt_WrkOut_Name, txt_ElpsdTime, txt_Elpsd_Time;
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
    int i = 0;
    public static ArrayList<UExerSummaryModel> list_uExerSummaryModel;
    public int totalRestTime = 0;
    public static String screnFrom="";

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
        list_uExerSummaryModel = new ArrayList<>();

        getUserExcercises();
        //   mStrSets = getIntent().getStringExtra("sets");
        //mStrMasterId = getIntent().getStringExtra("master_id");
        // mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        screnFrom=getIntent().getStringExtra("FreStyleFrom");

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

                try {
                    String uExr_Id = list_Exercises.get(playCount).getExerciseId().get_id();
                    userExerciseId = list_Exercises.get(playCount).get_id();
                    postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime, noOfsets);
                    UExerSummaryModel uExerSummaryModel = new UExerSummaryModel();
                    uExerSummaryModel.setSets(String.valueOf(noOfsets));
                    uExerSummaryModel.setWkt_Time(String.valueOf(Math.abs(Integer.parseInt(mStrGapTime))));
                    uExerSummaryModel.setRest_Time(!mStrRestpGapTime.equals("") ? mStrRestpGapTime : "0");
                    uExerSummaryModel.setExerCount(playCount);
                    list_uExerSummaryModel.add(uExerSummaryModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (playCount == -1) {
                        String uExr_Id = list_Exercises.get(1).getExerciseId().get_id();
                        userExerciseId = list_Exercises.get(1).get_id();
                        postWorkoutInfo(uExr_Id, userExerciseId, mStrStartTime, mStrEndTime, 1);
                    }

                }
            }
        });
    }

    private void initviews() {
        btStart = findViewById(R.id.btStart);
        btStop = findViewById(R.id.btStop);
        txt_Sets = findViewById(R.id.txt_Sets);
        txt_Reps = findViewById(R.id.txt_Reps);
        txt_wrkOutTime = findViewById(R.id.txt_wrkOutTime);
        txt_TotalWrkTime = findViewById(R.id.txt_TotalWrkTime);
        txt_WrkOut_Name = findViewById(R.id.txt_WrkOut_Name);
        vdeoView = findViewById(R.id.vdeoView);
        lyt_Elpsd_Time = findViewById(R.id.lyt_Elpsd_Time);
        txt_Elpsd_Time = findViewById(R.id.txt_Elpsd_Time);
    }

    private void getUserExcercises() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userid", str_UserID);
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
                        mStrSets = list_Exercises.get(playCount).getSets();
                        txt_Reps.setText(list_Exercises.get(playCount).getReps());
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
                            list_UrlPaths = new ArrayList<>();
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
                error.printStackTrace();


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + token);
                // eyJ1c2VySWQiOiI1Y2I1N2I0NTUyMWQ2ODAwMTc4ODg4NGUiLCJsb2NhbExvZ2luSWQiOiI1Y2I1N2I0NTUyMWQ2ODAwMTc4ODg4NGUiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJ0c3BjZ2hvbGljZXRhYkBnbWFpbC5jb20iLCJleHAiOjE1NjA1ODE0NDU3LCJpYXQiOjE1NTUzOTc0NDV9.ZrMQYP4YmrBszqQTc2SHJeGczJccPVDxZS1ZD0QQpko
                return headers;
            }
        };
        requestQueue.add(request_Excercise);
    }

    private void downloadvideoFiles() {
        for (int i = 0; i < list_Exercises.size(); i++) {
            new DownloadFile().execute(list_Exercises.get(i).getExerciseId().getExerciseUrl());
        }
    }

    private void postWorkoutInfo(final String uExrId, final String userExerciseId, final String startTime, final String endTime, final int nOfsets) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userExerciseId", userExerciseId);
        params.put("exerciseId", uExrId);
        params.put("setId", String.valueOf(nOfsets));
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
                        totalRestTime = totalRestTime + Integer.parseInt(list_Exercises.get(playCount).getRest());
                        countDownTimerStart(Integer.parseInt(list_Exercises.get(playCount).getRest()) * 1000);
                        /*totalRestTime = totalRestTime + 3;
                        countDownTimerStart(3* 1000);*/
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
                            noOfsets = setsCount;
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
                                noOfsets = setsCount + 1;
                            } else {
                                setsCount = 0;
                                txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);
                                noOfsets = setsCount + 1;
                            }
                        } else {
                            txt_Sets.setText("" + (setsCount + 1) + "/" + total_Sets);
                            noOfsets = setsCount + 1;
                        }
                        UExerSummaryModel uExerSummaryModel = new UExerSummaryModel();
                        uExerSummaryModel.setSets(String.valueOf(noOfsets));
                        uExerSummaryModel.setWkt_Time(String.valueOf(Math.abs(Integer.parseInt(mStrGapTime))));
                        uExerSummaryModel.setRest_Time(!mStrRestpGapTime.equals("") ? mStrRestpGapTime : "0");
                        uExerSummaryModel.setExerCount(playCount);
                        list_uExerSummaryModel.add(uExerSummaryModel);

                    } else {
                        stopTotTimer();
                        finalEndTime = getDateTime();

                        Toaster.showInfoMessage("Workouts Completed !");
                        Intent intent = new Intent(getApplicationContext(), ExerciseResultActivity.class);
                        intent.putExtra("mStrInitialStartTime", mStrInitialStartTime);
                        intent.putExtra("finalEndTime", finalEndTime);
                        intent.putExtra("totalWorkOutTime", getDiffDuration(finalEndTime,mStrInitialStartTime));
                        intent.putExtra("totalRestTime", String.valueOf(totalRestTime));
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
                headers.put("Authorization", "token " + token);

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
            //  this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setMessage("Getting your Personalized workouts ! ");
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
                 /*   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {*/
                  /*  publishProgress("" + (int) ((finalTotal * 100) / lengthOfFile));
                    Log.d("Progress", "Progress: " + (int) ((total * 100) / lengthOfFile));*/
                   /*     }
                    });*/
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

      /*  @Override
        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }*/


        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            //    i++;
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
         /*   if (i<list_Exercises.size()) {
                downloadvideoFiles(i);
            }*/
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
