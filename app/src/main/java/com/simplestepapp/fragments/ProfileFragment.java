package com.simplestepapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.simplestepapp.R;
import com.simplestepapp.activities.DailyRtneFreStyleWrktActivity;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.AgeAdapter;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.SessionManager;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment implements DiscreteScrollView.OnItemChangedListener{

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    AppCompatButton btn_PSubmit;
    public RadioGroup rBtnGrp_Surgens, rGrpGender, rG_WrkRtne, rG_AimTo, rgp_Profsion;
    public RadioButton rBtn_SYes, rBtn_SNo, rBtn_Male, rBtn_Female, rBtn_others;
    DiscreteScrollView age_picker;
    ArrayList<Integer> age_Lst;
    AgeAdapter ageAdapter;
    SessionManager sessionManager;
    LinearLayout lyt_SurYes;
    RulerValuePicker height_picker, weight_picker;
    AppCompatTextView txt_Name, txt_HtPicker, txt_WtPicker;
    String userName = "", eMailId = "", token = "", str_Gender = "", str_Surgery = "", str_WrktRtne = "", str_AimTo = "", str_Profsn = "", str_DOJ = "", str_DOB = "", str_ActId = "";
    int sltd_Wt, sltd_Ht, slctd_Age, slctd_Ht , slctd_Wt;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      /*  final View v = inflater.inflate(R.layout.profile_activity, container, false);
        initviews(v);
        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            txt_Name.setText(userName);
        }
        for (int i = 10; i <= 60; i++) {
            age_Lst.add(i);
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
                slctd_Ht = selectedValue;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_HtPicker.setText("" + selectedValue + " cms");
                slctd_Ht = selectedValue;
            }
        });

        txt_WtPicker.setText(30 + " kgs");
        weight_picker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                sltd_Wt = selectedValue;
                txt_WtPicker.setText("" + selectedValue + " kgs");
                slctd_Wt = selectedValue;
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                txt_WtPicker.setText("" + selectedValue + " kgs");
                slctd_Wt = selectedValue;
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
                RadioButton rb = v.findViewById(checkedId);
                str_WrktRtne = rb.getText().toString();
            }
        });

        rG_AimTo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rBtn = v.findViewById(checkedId);
                str_AimTo = rBtn.getText().toString();
            }
        });

        rgp_Profsion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton r_Btn = v.findViewById(checkedId);
                str_Profsn = r_Btn.getText().toString();
            }
        });

        btn_PSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ht_Squre=slctd_Ht * slctd_Ht;
                float bmi =(sltd_Wt / ht_Squre)*10000;
                Log.d("BMI",""+bmi);
                profileDataUpload(userName, slctd_Age, str_Gender, slctd_Ht, slctd_Wt, bmi, str_Surgery, str_WrktRtne, str_AimTo,
                        str_Profsn, str_DOJ, str_DOB, str_ActId);
            }
        });*/
        return null;
    }

    private void initviews(View v) {

        txt_Name = v.findViewById(R.id.txt_Name);
        age_picker = v.findViewById(R.id.age_picker);
        height_picker = v.findViewById(R.id.height_picker);
        weight_picker = v.findViewById(R.id.weight_picker);
        txt_HtPicker = v.findViewById(R.id.txt_HtPicker);
        txt_WtPicker = v.findViewById(R.id.txt_WtPicker);
        lyt_SurYes = v.findViewById(R.id.lyt_SurYes);
        rBtnGrp_Surgens = v.findViewById(R.id.rBtnGrp_Surgens);
        rBtn_SYes = v.findViewById(R.id.rBtn_SYes);
        rBtn_SNo = v.findViewById(R.id.rBtn_SNo);
        rGrpGender = v.findViewById(R.id.rGrpGender);
        rBtn_Male = v.findViewById(R.id.rBtn_Male);
        rBtn_Female = v.findViewById(R.id.rBtn_Female);
        rBtn_others = v.findViewById(R.id.rBtn_others);
        rG_WrkRtne = v.findViewById(R.id.rG_WrkRtne);
        rG_AimTo = v.findViewById(R.id.rG_AimTo);
        rgp_Profsion = v.findViewById(R.id.rgp_Profsion);
        btn_PSubmit = v.findViewById(R.id.btn_PSubmit);
        age_Lst = new ArrayList<>();
    }

    private void profileDataUpload(final String firstName, final int age, final String gender, final int height,
                                   final int weight, final float bmi, final String anysurgeries, final String WorkoutRoutine,
                                   final String Aimto, final String Profession, final String DOJ, final String DOB, final String activityLevel) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Submiting ...");
        progressDialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", "Kasani");
        params.put("age", String.valueOf(age));
        params.put("gender", gender);
        params.put("height", String.valueOf(height));
        params.put("weight", String.valueOf(weight));
        params.put("bmi", String.valueOf(bmi));
        params.put("anySurgeries", anysurgeries);
        params.put("workoutRoutine", WorkoutRoutine);
        params.put("aimTo", Aimto);
        params.put("profession", Profession);
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
                Intent intent_DlyRtne = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), DailyRtneFreStyleWrktActivity.class);
                startActivity(intent_DlyRtne);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent_DlyRtne = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), DailyRtneFreStyleWrktActivity.class);
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

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
    }

}
