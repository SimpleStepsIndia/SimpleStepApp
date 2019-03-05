package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.simplestepapp.R;
import com.simplestepapp.adapters.AgeAdapter;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    AppCompatButton btn_PSubmit;
    public RadioGroup rBtnGrp_Surgens, rGrpGender, rG_WrkRtne, rG_AimTo, rgp_Profsion;
    public RadioButton rBtn_SYes, rBtn_SNo, rBtn_Male, rBtn_Female, rBtn_others;
    DiscreteScrollView age_picker;
    ArrayList<String> age_Lst;
    AgeAdapter ageAdapter;
    SessionManager sessionManager;
    LinearLayout lyt_SurYes;
    RulerValuePicker height_picker, weight_picker;
    AppCompatTextView txt_Name, txt_HtPicker, txt_WtPicker;
    String userName = "", eMailId = "", token = "", slctd_Age = "", slctd_Ht = "", slctd_Wt = "", str_Gender = "", str_Surgery = "",
            str_WrktRtne = "", str_AimTo = "", str_Profsn = "", str_DOJ = "", str_DOB = "", str_ActId = "";
    int sltd_Wt, sltd_Ht;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initviews();
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            txt_Name.setText(userName);
        }
        for (int i = 10; i <= 60; i++) {
            age_Lst.add(String.valueOf(i));
        }

        age_picker.setSlideOnFling(true);
        age_picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        ageAdapter = new AgeAdapter(age_Lst);
        age_picker.setAdapter(ageAdapter);

        age_picker.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                ageAdapter.setSelectedIndex(adapterPosition);
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        ageAdapter.notifyDataSetChanged();
                    }
                };
                handler.post(r);
                slctd_Age = age_Lst.get(adapterPosition);
            }
        });

        txt_HtPicker.setText("" + 100 + " cms");
        height_picker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                sltd_Ht = selectedValue / 100;
                txt_HtPicker.setText("" + selectedValue + " cms");
                slctd_Ht = String.valueOf(selectedValue);
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_HtPicker.setText("" + selectedValue + " cms");
                slctd_Ht = String.valueOf(selectedValue);
            }
        });

        txt_WtPicker.setText(30 + " kgs");
        weight_picker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                sltd_Wt = selectedValue;
                txt_WtPicker.setText("" + selectedValue + " kgs");
                slctd_Wt = String.valueOf(selectedValue);
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_WtPicker.setText("" + selectedValue + " kgs");
                slctd_Wt = String.valueOf(selectedValue);
            }
        });

        rBtnGrp_Surgens.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rBtn_SYes:
                        str_Surgery = "Yes";
                        lyt_SurYes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rBtn_SNo:
                        str_Surgery = "No";
                        lyt_SurYes.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        rGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rBtn_Male:
                        str_Gender = "Male";
                        break;
                    case R.id.rBtn_Female:
                        str_Gender = "Female";
                        break;
                    case R.id.rBtn_others:
                        str_Gender = "Others";
                        break;
                }
            }
        });

        rG_WrkRtne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb = findViewById(checkedId);
                str_WrktRtne = rb.getText().toString();
            }
        });

        rG_AimTo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rBtn = findViewById(checkedId);
                str_AimTo = rBtn.getText().toString();
            }
        });

        rgp_Profsion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton r_Btn = findViewById(checkedId);
                str_Profsn = r_Btn.getText().toString();
            }
        });

        btn_PSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ht_Squre=sltd_Ht * sltd_Ht;
                float bmi = sltd_Wt / ht_Squre;
                profileDataUpload(userName, "Kasani", slctd_Age, str_Gender, slctd_Ht, slctd_Wt, bmi, str_Surgery, str_WrktRtne, str_AimTo,
                        str_Profsn, str_DOJ, str_DOB, str_ActId);
            }
        });

    }

    private void initviews() {
        txt_Name = findViewById(R.id.txt_Name);
        age_picker = findViewById(R.id.age_picker);
        height_picker = findViewById(R.id.height_picker);
        weight_picker = findViewById(R.id.weight_picker);
        txt_HtPicker = findViewById(R.id.txt_HtPicker);
        txt_WtPicker = findViewById(R.id.txt_WtPicker);
        lyt_SurYes = findViewById(R.id.lyt_SurYes);
        rBtnGrp_Surgens = findViewById(R.id.rBtnGrp_Surgens);
        rBtn_SYes = findViewById(R.id.rBtn_SYes);
        rBtn_SNo = findViewById(R.id.rBtn_SNo);
        rGrpGender = findViewById(R.id.rGrpGender);
        rBtn_Male = findViewById(R.id.rBtn_Male);
        rBtn_Female = findViewById(R.id.rBtn_Female);
        rBtn_others = findViewById(R.id.rBtn_others);
        rG_WrkRtne = findViewById(R.id.rG_WrkRtne);
        rG_AimTo = findViewById(R.id.rG_AimTo);
        rgp_Profsion = findViewById(R.id.rgp_Profsion);
        btn_PSubmit = findViewById(R.id.btn_PSubmit);
        age_Lst = new ArrayList<>();
    }

    private void profileDataUpload(final String firstName, final String lastName, final String age, final String gender, final String height,
                                   final String weight, final float bmi, final String anysurgeries, final String WorkoutRoutine,
                                   final String Aimto, final String Profession, final String DOJ, final String DOB, final String activityLevel) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Submiting ...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("age", age);
        params.put("gender", gender);
        params.put("height", height);
        params.put("weight", weight);
        params.put("bmi", String.valueOf(bmi));
        params.put("anysurgeries", anysurgeries);
        params.put("WorkoutRoutine", WorkoutRoutine);
        params.put("Aimto", Aimto);
        params.put("Profession", Profession);
        params.put("DOJ", DOJ);
        params.put("DOB", DOB);
        params.put("activityLevel", activityLevel);
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest request_Profile = new JsonObjectRequest(Request.Method.POST, AppConfig.post_ProfileInfo, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("Result", "" + response.toString());
                sessionManager.profile_SubSession();
                Intent intent_DlyRtne = new Intent(getApplicationContext(), DailyRtneFreStyleWrktActivity.class);
                startActivity(intent_DlyRtne);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent_DlyRtne = new Intent(getApplicationContext(), DailyRtneFreStyleWrktActivity.class);
                startActivity(intent_DlyRtne);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + token);
                return headers;
            }
        };
        requestQueue.add(request_Profile);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {

    }
}