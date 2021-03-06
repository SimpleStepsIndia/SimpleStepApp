package com.simplestepapp.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.activities.ConclusionActivity;
import com.simplestepapp.activities.HomeActivity;
import com.simplestepapp.activities.PersonalInfoActivity;
import com.simplestepapp.activities.ViewPagerActivity;
import com.simplestepapp.adapters.ConclusionAdapter;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.WhyOptions;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Srinivas on 12/20/2018.
 */

public class SunBothFragment extends Fragment {

    MyGridView grid_view;
    ScrollView scroll_View;
    GridView conclusin_Grid;
    ConclusionAdapter conclusionAdapter;

    ArrayList<String> timeSlots;
    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;

    CustomAdapter customAdapter;

    LinearLayout lyt_list_Why, lyt_QtnOptns;

    AppCompatTextView txt_QtnHdng, txt_QtnCaptn, txt_QtnOptns, txt_Next;

    RadioGroup rG_WakeUp, rGrp_WhyOptions;

    RadioButton rBtn_WOne, rBtn_WTwo, rBtn_WThre, rBtn_WFur, rBtn_op1, rBtn_op2, rBtn_op3;

    String s_SBTime = "", s_SBQtnOption = "", s_SBWhyOptn = "",colorName="";;

    int sPosition = -1;

    AppCompatButton btn_Submit;

    AlertDialog alertDialog;

    private ProgressDialog progressDialog;

    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_sunbath, container, false);
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
        timeSlots.add("None");
        try {

            txt_QtnHdng.setText(ViewPagerActivity.questionerArrayList.get(6).getQuestion());
            txt_QtnCaptn.setText(ViewPagerActivity.questionerArrayList.get(6).getDescription());
            txt_QtnOptns.setText(ViewPagerActivity.questionerArrayList.get(6).getAnswerCaption());
            answerOptions = new ArrayList<>();
            answerOptions = ViewPagerActivity.questionerArrayList.get(6).getAnswerOptions();
            rBtn_WOne.setText(answerOptions.get(0).getDescription());
            rBtn_WTwo.setText(answerOptions.get(1).getDescription());
            rBtn_WThre.setText(answerOptions.get(2).getDescription());
            rBtn_WFur.setText(answerOptions.get(3).getDescription());
            whyOptions = new ArrayList<>();
            whyOptions = ViewPagerActivity.questionerArrayList.get(6).getWhyOptions();
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
                s_SBTime = (String) parent.getItemAtPosition(position);
                sPosition = position;
                lyt_QtnOptns.setVisibility(View.VISIBLE);
            }
        });

        rG_WakeUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                lyt_list_Why.setVisibility(View.VISIBLE);
                scroll_View.fullScroll(ScrollView.FOCUS_DOWN);
                switch (checkedId) {
                    case R.id.rBtn_WOne:
                        s_SBQtnOption = rBtn_WOne.getText().toString();
                        colorName="G";
                        break;
                    case R.id.rBtn_WTwo:
                        s_SBQtnOption = rBtn_WTwo.getText().toString();
                        colorName="B";
                        break;
                    case R.id.rBtn_WThre:
                        s_SBQtnOption = rBtn_WThre.getText().toString();
                        colorName="O";
                        break;
                    case R.id.rBtn_WFur:
                        s_SBQtnOption = rBtn_WFur.getText().toString();
                        colorName="R";
                        break;
                    default:
                        s_SBQtnOption = "";
                        break;
                }
            }
        });

        rGrp_WhyOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                txt_Next.setVisibility(View.VISIBLE);
                switch (checkedId) {
                    case R.id.rBtn_op1:
                        s_SBWhyOptn = rBtn_op1.getText().toString();
                        break;
                    case R.id.rBtn_op2:
                        s_SBWhyOptn = rBtn_op2.getText().toString();
                        break;
                    case R.id.rBtn_op3:
                        s_SBWhyOptn = rBtn_op3.getText().toString();
                        break;
                    default:
                        s_SBWhyOptn = "";
                        break;
                }
            }
        });
        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setTimeSlotOption(s_SBTime);
                qAnswerModel.setAnswerOption(s_SBQtnOption);
                qAnswerModel.setWhyOption(s_SBWhyOptn);
                qAnswerModel.setS_Position(sPosition);
                qAnswerModel.setColorCode(colorName);
                qAnswerModel.setQuestionId(ViewPagerActivity.questionerArrayList.get(6).get_id());
                ViewPagerActivity.qAnswerModelArrayList.add(qAnswerModel);
              //  String jsonOb = new Gson().toJson(ViewPagerActivity.qAnswerModelArrayList);
             //   Log.d("JsonObject", "" + jsonOb);

                Intent intent=new Intent(getActivity(), ConclusionActivity.class);
                startActivity(intent);
                //conclusion_Dialog();
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
        scroll_View = v.findViewById(R.id.scroll_View);
    }

    private void conclusion_Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.conclusion_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        conclusin_Grid = view.findViewById(R.id.conclusin_Grid);
       /* conclusionAdapter = new ConclusionAdapter(getActivity().getApplicationContext(), R.layout.conclusion_item,timeSlots, ViewPagerActivity.qAnswerModelArrayList);
        conclusin_Grid.setAdapter(conclusionAdapter);*/
        btn_Submit = view.findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void postQuestionInfo() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest request_postQtnsIfo = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("json", "");
                return params;
            }
        };
        requestQueue.add(request_postQtnsIfo);
    }

    private static SunBothFragment instance = null;

    public static SunBothFragment newInstance(String text) {

        if (instance == null) {
            // new instance
            instance = new SunBothFragment();

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