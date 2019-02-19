package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.adapters.ExerciseAdapter;
import com.simplestepapp.adapters.UserExerciseAdapter;
import com.simplestepapp.models.exercise.ExerciseModel;
import com.simplestepapp.models.exercise.UExercise;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Srinivas on 2/8/2019.
 */

public class FreeStyleWorkActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SessionManager sessionManager;

    AppCompatButton btn_Start;

    CheckBox cb_SlctAll;

    ListView lv_Exrcis;

    public List<UExercise> list_Exercises= new ArrayList<>();

    String userName = "", eMailId = "", token = "";

    UserExerciseAdapter exerciseAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frestlewrk);

        initviews();

        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
        }

        getUserExcercises();

        cb_SlctAll.setEnabled(false);
        cb_SlctAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(int i=0;i<list_Exercises.size();i++){
                        list_Exercises.get(i).setSelected(true);
                    }
                }else{
                    for(int i=0;i<list_Exercises.size();i++){
                        list_Exercises.get(i).setSelected(false);
                    }
                }

                if(exerciseAdapter==null){
                    exerciseAdapter =new UserExerciseAdapter(FreeStyleWorkActivity.this,list_Exercises);
                    lv_Exrcis.setAdapter(exerciseAdapter);
                }else{
                    exerciseAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initviews(){
        btn_Start=findViewById(R.id.btn_Start);
        cb_SlctAll=findViewById(R.id.cb_SlctAll);
        lv_Exrcis=findViewById(R.id.lv_Exrcis);
    }

    private void getUserExcercises() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("userid", "5c558f7543e313001720f162");
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest request_Excercise = new JsonObjectRequest(Request.Method.GET, AppConfig.getUser_Excercises, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("Excercise Re",""+response.toString());
                try {
                    list_Exercises=new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response.getString("userExercise"));
                    list_Exercises=new Gson().fromJson(String.valueOf(jsonArray),ExerciseModel.class);

                    for(int i=0;i<list_Exercises.size();i++){
                        list_Exercises.get(i).setSelected(false);
                    }

                    exerciseAdapter =new UserExerciseAdapter(FreeStyleWorkActivity.this,list_Exercises);
                    lv_Exrcis.setAdapter(exerciseAdapter);
                    cb_SlctAll.setEnabled(true);
                } catch (JSONException e) {
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
                headers.put("Authorization", "token " +"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJsb2NhbExvZ2luSWQiOiI1YzU1OGY3NTQzZTMxMzAwMTcyMGYxNjIiLCJwYXNzcG9ydFR5cGUiOiJsb2NhbCIsImVtYWlsSWQiOiJydWRyYXNoaXJpc2hhOUBnbWFpbC5jb20iLCJleHAiOjE1NTUzMTIzMTE4LCJpYXQiOjE1NTAxMjgzMTF9.30SMbAS6lZER5uTjD7cJeuQ7tyWhi4IwwcXpTiMM1pc");
                return headers;
            }
        };
        requestQueue.add(request_Excercise);
    }
}

