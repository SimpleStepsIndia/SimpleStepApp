package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.simplestepapp.R;
import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.models.UserExerciseMaster;
import com.simplestepapp.network.ErrorCodes;
import com.simplestepapp.network.LakmeCallBack;
import com.simplestepapp.network.MyCallBack;
import com.simplestepapp.network.RestClient;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExerciseResultActivity extends AppCompatActivity {


    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrStartTime, mStrEndTime, str_wrkFeel, str_HwWrkFl, mIntialStartTime, mStrrestTime, mStrtotalTime;
    int totalTime = 0, workoutTime = 0, restTime = 0;

    AppCompatTextView tvTotalTime, tvWorkoutTime, tvRestTime;
    AppCompatButton btnProceed;
    RadioGroup rg_HwYuFel, rg_wrkFeel;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String token;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);
        initviews();
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user_Info = sessionManager.getUserDetails();
            token = user_Info.get(SessionManager.KEY_TOKEN);
            mStrMasterId = user_Info.get(SessionManager.KEY_USERID);
        }
        try {
            mStrStartTime = getIntent().getStringExtra("mStrInitialStartTime");
            mStrEndTime = getIntent().getStringExtra("finalEndTime");
            mStrrestTime = getIntent().getStringExtra("totalRestTime");
            mStrtotalTime = getDiffDuration(mStrStartTime, mStrEndTime);
            workoutTime = Math.abs(Integer.parseInt(mStrtotalTime) - Integer.parseInt(mStrrestTime));
            Log.d("ExResult", "mStrStartTime " + mStrStartTime + "mStrEndTime " + mStrEndTime + "mStrrestTime " + mStrrestTime);
            int totTime = Integer.parseInt(mStrtotalTime);
            String toTime = totTime / 60 + " Mins " + totTime % 60 + " Sec";
            int totRTime = Integer.parseInt(mStrrestTime);
            String toRTime = totRTime / 60 + " Mins " + totRTime % 60 + " Sec";

            tvTotalTime.setText("" + toTime);
            tvWorkoutTime.setText("" + String.valueOf(workoutTime / 60 + " Mins " + workoutTime % 60 + " Sec"));
            tvRestTime.setText("" + toRTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postExrCseFeedback();
            }
        });

        rg_HwYuFel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                str_HwWrkFl = radioButton.getText().toString();
            }
        });

        rg_wrkFeel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                str_wrkFeel = radioButton.getText().toString();
            }
        });

    }

    private void initviews() {
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvWorkoutTime = findViewById(R.id.tvWorkoutTime);
        tvRestTime = findViewById(R.id.tvRestTime);
        btnProceed = findViewById(R.id.btnProceed);
        rg_HwYuFel = findViewById(R.id.rg_HwYuFel);
        rg_wrkFeel = findViewById(R.id.rg_wrkFeel);
    }

    private void postExrCseFeedback() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userExerciseId", mStrMasterId);
        params.put("startTime", mStrStartTime);
        params.put("endTime", mStrEndTime);
        params.put("feedback1", "How you sweat during workout");
        params.put("feedbackResp1", str_HwWrkFl);
        params.put("feedback2", "I feel workout is");
        params.put("feedbackResp2", str_wrkFeel);
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, AppConfig.postUser_Excercises_Info, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("EResultInfo", "" + response.toString());
                if (FreStyleVideoPlayerActivity.screnFrom.equalsIgnoreCase("Unity")){
                    Intent intent = new Intent(getApplicationContext(), UnityActivity.class);
                    intent.putExtra("ScreenKey","visualizationMrt");
                    intent.putExtra("AndroidValue", ""+token);
                    intent.putExtra("GetMinutes", "45");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Intent intent = new Intent(getApplicationContext(), BottomNavigationActivity.class);
                startActivity(intent);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + token);
                //  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJsb2NhbExvZ2luSWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYXNoaXJpc2hhOUBnbWFpbC5jb20iLCJleHAiOjE1NTUzMTIzMTE4LCJpYXQiOjE1NTAxMjgzMTF9.30SMbAS6lZER5uTjD7cJeuQ7tyWhi4IwwcXpTiMM1pc");
                return headers;
            }
        };
        requestQueue.add(request);
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
