package com.simplestepapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.simplestepapp.R;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.models.AllQuestionsModel;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.Questioner;
import com.simplestepapp.models.WhyOptions;
import com.simplestepapp.utils.Constants;
import com.simplestepapp.utils.MyGridView;
import com.simplestepapp.utils.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WakeUpFragment extends Fragment {

    RequestQueue requestQueue;

    private static WakeUpFragment instance = null;

    MyGridView grid_view;

    ArrayList<String> timeSlots;
    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;

    CustomAdapter customAdapter;

    LinearLayout lyt_list_Why, lyt_QtnOptns;

    AppCompatTextView txt_QtnHdng, txt_QtnCaptn, txt_QtnOptns, txt_Next;

    RadioGroup rG_WakeUp, rGrp_WhyOptions;

    RadioButton rBtn_WOne, rBtn_WTwo, rBtn_WThre, rBtn_WFur, rBtn_op1, rBtn_op2, rBtn_op3;

    ProgressDialog progressDialog;

    String s_WkUpTime = "", s_WkUpQtnOption = "", s_WkUpWhyOptn="";

    int sPosition = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_wakeup, container, false);
        initviews(v);
        progressDialog = new ProgressDialog(getActivity());
        requestQueue = Volley.newRequestQueue(getActivity());
        timeSlots = new ArrayList<>();
        timeSlots.add("< 5:00");
        timeSlots.add("5:00");
        timeSlots.add("5:15");
        timeSlots.add("5:30");
        timeSlots.add("5:45");
        timeSlots.add("6:00");
        timeSlots.add("6:15");
        timeSlots.add("6:30");
        timeSlots.add("6:45");
        timeSlots.add("7:00");
        timeSlots.add("7:15");
        timeSlots.add("7:30");
        timeSlots.add("7:45");
        timeSlots.add("8:00");
        timeSlots.add("8:15");
        timeSlots.add("8:30");
        timeSlots.add("8:45");
        timeSlots.add("9:00");
        timeSlots.add("9:00 >");

        try {

            txt_QtnHdng.setText(ViewPagerActivity.questionerArrayList.get(0).getQuestion());
            txt_QtnCaptn.setText(ViewPagerActivity.questionerArrayList.get(0).getTimeSlotCaption());
            txt_QtnOptns.setText(ViewPagerActivity.questionerArrayList.get(0).getAnswerCaption());
            answerOptions = new ArrayList<>();
            answerOptions = ViewPagerActivity.questionerArrayList.get(0).getAnswerOptions();
            rBtn_WOne.setText(answerOptions.get(0).getDescription());
            rBtn_WTwo.setText(answerOptions.get(1).getDescription());
            rBtn_WThre.setText(answerOptions.get(2).getDescription());
            rBtn_WFur.setText(answerOptions.get(3).getDescription());
            whyOptions = new ArrayList<>();
            whyOptions = ViewPagerActivity.questionerArrayList.get(0).getWhyOptions();
            rBtn_op1.setText(whyOptions.get(0).getDescription());
            rBtn_op2.setText(whyOptions.get(1).getDescription());
            rBtn_op3.setText(whyOptions.get(2).getDescription());

        } catch (Exception e) {
            e.printStackTrace();
        }


        customAdapter = new CustomAdapter(getActivity().getApplicationContext(), timeSlots);
        grid_view.setAdapter(customAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customAdapter.setSelectedIndex(position);
                s_WkUpTime = (String) parent.getItemAtPosition(position);
                sPosition = position;
                lyt_QtnOptns.setVisibility(View.VISIBLE);
            }
        });

        rG_WakeUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                lyt_list_Why.setVisibility(View.VISIBLE);
                switch (checkedId) {
                    case R.id.rBtn_WOne:
                        s_WkUpQtnOption = rBtn_WOne.getText().toString();
                        break;
                    case R.id.rBtn_WTwo:
                        s_WkUpQtnOption = rBtn_WTwo.getText().toString();
                        break;
                    case R.id.rBtn_WThre:
                        s_WkUpQtnOption = rBtn_WThre.getText().toString();
                        break;
                    case R.id.rBtn_WFur:
                        s_WkUpQtnOption = rBtn_WFur.getText().toString();
                        break;
                    default:
                        s_WkUpQtnOption = "";
                        break;
                }
            }
        });

        rGrp_WhyOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rBtn_op1:
                        s_WkUpWhyOptn=rBtn_op1.getText().toString();
                        break;
                    case R.id.rBtn_op2:
                        s_WkUpWhyOptn=rBtn_op2.getText().toString();
                        break;
                    case R.id.rBtn_op3:
                        s_WkUpWhyOptn=rBtn_op3.getText().toString();
                        break;
                    default:
                        s_WkUpWhyOptn="";
                        break;
                }
            }
        });

        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPagerActivity.pager.setCurrentItem(1);
                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setSelectedTime(s_WkUpTime);
                qAnswerModel.setS_Position(sPosition);
                ViewPagerActivity.qAnswerModelArrayList.add(qAnswerModel);
                Log.d("Selected Items", "" + s_WkUpTime + "" + s_WkUpQtnOption + "" + s_WkUpWhyOptn);


            }
        });
        return v;
    }

    private void initviews(View v) {
        txt_QtnHdng = v.findViewById(R.id.txt_QtnHdng);
        txt_QtnCaptn = v.findViewById(R.id.txt_QtnCaptn);
        txt_QtnOptns = v.findViewById(R.id.txt_QtnOptns);
        rG_WakeUp = v.findViewById(R.id.rG_WakeUp);
        rGrp_WhyOptions = v.findViewById(R.id.rGrp_WhyOptions);
        rBtn_WOne = v.findViewById(R.id.rBtn_WOne);
        rBtn_WTwo = v.findViewById(R.id.rBtn_WTwo);
        rBtn_WThre = v.findViewById(R.id.rBtn_WThre);
        rBtn_WFur = v.findViewById(R.id.rBtn_WFur);
        rBtn_op1 = v.findViewById(R.id.rBtn_op1);
        rBtn_op2 = v.findViewById(R.id.rBtn_op2);
        rBtn_op3 = v.findViewById(R.id.rBtn_op3);
        grid_view = v.findViewById(R.id.grid_view);
        lyt_list_Why = v.findViewById(R.id.lyt_list_Why);
        lyt_QtnOptns = v.findViewById(R.id.lyt_QtnOptns);
        txt_Next = v.findViewById(R.id.txt_Next);
    }


    public static WakeUpFragment newInstance(String text) {

        if (instance == null) {
            instance = new WakeUpFragment();

            // sets data to bundle
            Bundle bundle = new Bundle();
            bundle.putString("msg", text);

            // set data to fragment
            instance.setArguments(bundle);

            return instance;
        } else {

            return instance;
        }

    }

}
