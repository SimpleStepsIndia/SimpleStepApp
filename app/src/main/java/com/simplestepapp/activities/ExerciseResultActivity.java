package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExerciseResultActivity extends AppCompatActivity {

    RestClient mRestClient;
    String mStrReps, mStrSets, mStrMasterId, mStrSelectedExercices, mStrStartTime, mStrEndTime, str_wrkFeel, str_HwWrkFl, mIntialStartTime;
    int totalTime = 0, workoutTime = 0, restTime = 0;

    @Bind(R.id.tvExercise)
    TextView tvExercises;

    @Bind(R.id.tvSets)
    TextView tvSets;

    @Bind(R.id.tvReps)
    TextView tvReps;

    @Bind(R.id.tvTotalTime)
    TextView tvTotalTime;

    @Bind(R.id.tvWorkOutTime)
    TextView tvWorkoutTime;

    @Bind(R.id.tvRestTime)
    TextView tvRestTime;

    @Bind(R.id.listExercise)
    ListView list_exercises;

    @Bind(R.id.btProceed)
    Button btnProceed;

    @Bind(R.id.rg_HwYuFel)
    RadioGroup rg_HwYuFel;

    @Bind(R.id.rg_wrkFeel)
    RadioGroup rg_wrkFeel;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user_Info = sessionManager.getUserDetails();
            token = user_Info.get(SessionManager.KEY_TOKEN);
        }
        mRestClient = new RestClient();

        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");
        mStrStartTime = getIntent().getStringExtra("mStrStartTime");
        mStrEndTime = getIntent().getStringExtra("mStrEndTime");
        mIntialStartTime = getIntent().getStringExtra("mIntialStartTime");

        tvExercises.setText(String.valueOf(mStrSelectedExercices.split(",").length));
        tvReps.setText(mStrReps);
        tvSets.setText(mStrSets);

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

    private void postExrCseFeedback() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userExerciseId", mStrMasterId);
        params.put("startTime", mIntialStartTime);
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
                Intent intent = new Intent(getApplicationContext(), DailyRtneFreStyleWrktActivity.class);
                startActivity(intent);
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
                //  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJsb2NhbExvZ2luSWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYXNoaXJpc2hhOUBnbWFpbC5jb20iLCJleHAiOjE1NTUzMTIzMTE4LCJpYXQiOjE1NTAxMjgzMTF9.30SMbAS6lZER5uTjD7cJeuQ7tyWhi4IwwcXpTiMM1pc");
                return headers;
            }
        };
        requestQueue.add(request);
    }
}
