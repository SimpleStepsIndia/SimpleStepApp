package com.simplestepapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.adapters.GridItemView;
import com.simplestepapp.adapters.GridViewAdapter;
import com.simplestepapp.adapters.CustomWakeupAdapter;
import com.simplestepapp.models.ActQuestioner;
import com.simplestepapp.models.AllActivityModel;
import com.simplestepapp.models.AllQuestionsModel;
import com.simplestepapp.models.DlyRtneModel;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.Questioner;
import com.simplestepapp.models.TimeSlot;
import com.simplestepapp.utils.AppConfig;
import com.simplestepapp.utils.MyGridView;
import com.simplestepapp.utils.SessionManager;
import com.simplestepapp.utils.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Srinivas on 2/7/2019.
 */

public class DailyRoutineActivity extends AppCompatActivity {

    public static List<ActQuestioner> qstnrAryLstDlyRtne;
    public static ArrayList<QAnswerModel> qAnsMdlAryLstDlyRtne = new ArrayList<>();
    ArrayList<String> timeSlots;
    private ArrayList<TimeSlot> slctd_TimeSlts;

    private ProgressDialog progressDialog;
    RequestQueue requestQueue;

    ScrollView scroll_View;

    GridView grid_view;

    CustomWakeupAdapter customWakeupAdapter;
    GridViewAdapter GridViewAdapter;

    AppCompatTextView txt_QtnHdng, txt_Next;

    AppCompatImageView img_QtnHdng;

    SessionManager sessionManager;

    private Dialog dialog;

    public Integer[] imgArray_Qtns = {R.drawable.wakeup_icon, R.drawable.brushicon, R.drawable.colon, R.drawable.drining_water,
            R.drawable.mental_fitness, R.drawable.physical_fitness, R.drawable.sun_shine, R.drawable.sun_shine};


    String s_WkUpTime = "", userName = "", eMailId = "", token = "", str_Date = "";

    int sPosition = -1, nxt_Pos = 0;

    public static int dis_Position = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_routine);
        initviews();
        toolbarsetUp();
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);

        str_Date = getIntent().getStringExtra("Date");
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
        }
        get_QuestionsAll();

        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeSlot timeSlot = new TimeSlot();
                slctd_TimeSlts = new ArrayList<>();
                nxt_Pos = 1;
                timeSlot.setSlot(s_WkUpTime);
                slctd_TimeSlts.add(timeSlot);
                dlyRtneAnsSubmit("", qstnrAryLstDlyRtne.get(0).get_id(), slctd_TimeSlts, nxt_Pos);

            }
        });

    }

    public void toolbarsetUp() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.lyt_header, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void initviews() {

        txt_QtnHdng = findViewById(R.id.txt_QtnHdng);
        grid_view = findViewById(R.id.grid_view);
        txt_Next = findViewById(R.id.txt_Next);
        scroll_View = findViewById(R.id.scroll_View);

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
    }

    private void initviews(Dialog dialog) {
        img_QtnHdng = dialog.findViewById(R.id.img_QtnHdng);
        txt_QtnHdng = dialog.findViewById(R.id.txt_QtnHdng);
        grid_view = dialog.findViewById(R.id.grid_view);
        txt_Next = dialog.findViewById(R.id.txt_Next);
        scroll_View = dialog.findViewById(R.id.scroll_View);

    }

    public void get_QuestionsAll() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest user_Questn_Req = new StringRequest(Request.Method.GET, AppConfig.get_ActivityAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    qstnrAryLstDlyRtne = new ArrayList<>();
                    Log.d("LiveData", "" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("activity"));
                    qstnrAryLstDlyRtne = new Gson().fromJson(String.valueOf(jsonArray), AllActivityModel.class);

                    txt_QtnHdng.setText(qstnrAryLstDlyRtne.get(0).getQuestion());

                    customWakeupAdapter = new CustomWakeupAdapter(DailyRoutineActivity.this, timeSlots);
                    grid_view.setAdapter(customWakeupAdapter);
                    grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            customWakeupAdapter.setSelectedIndex(position);
                            s_WkUpTime = (String) parent.getItemAtPosition(position);
                            sPosition = position;
                            dis_Position = position;
                            customWakeupAdapter.notifyDataSetChanged();
                            txt_Next.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("error in response", "Error: " + error.getMessage());
                progressDialog.dismiss();
                Toaster.longToast("Please check the Network And Try again!");

            }
        });

        requestQueue.add(user_Questn_Req);
    }

    private void dialog_Brushing(final int postn) {
        dialog = new Dialog(DailyRoutineActivity.this, android.R.style.DeviceDefault_Light_ButtonBar);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.dlg_dlyrtne);
        initviews(dialog);
        try {
            img_QtnHdng.setBackgroundResource(imgArray_Qtns[postn]);
            txt_QtnHdng.setText(qstnrAryLstDlyRtne.get(postn).getQuestion());
            GridViewAdapter = new GridViewAdapter(timeSlots, DailyRoutineActivity.this);
            grid_view.setAdapter(GridViewAdapter);
            slctd_TimeSlts = new ArrayList<>();
            grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int selectedIndex = GridViewAdapter.selectedPositions.indexOf(position);
                    TimeSlot timeSlot = new TimeSlot();
                    if (selectedIndex > -1) {
                        GridViewAdapter.selectedPositions.remove(selectedIndex);
                        ((GridItemView) view).display(false);
                        timeSlot.setSlot((String) parent.getItemAtPosition(position));
                        slctd_TimeSlts.remove(timeSlot);
                    } else {
                        GridViewAdapter.selectedPositions.add(position);
                        ((GridItemView) view).display(true);
                        timeSlot.setSlot((String) parent.getItemAtPosition(position));
                        slctd_TimeSlts.add(timeSlot);

                    }
                    txt_Next.setVisibility(View.VISIBLE);

                }
            });


            txt_Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QAnswerModel qAnswerModel = new QAnswerModel();
                    qAnswerModel.setTimeSlotOption(s_WkUpTime);
                    qAnswerModel.setS_Position(sPosition);
                    qAnswerModel.setQuestionId(qstnrAryLstDlyRtne.get(nxt_Pos).get_id());
                    String a_Id = qstnrAryLstDlyRtne.get(nxt_Pos).get_id();
                    qAnsMdlAryLstDlyRtne.add(qAnswerModel);
                    ++nxt_Pos;
                    if (nxt_Pos <= 7) {
                        dlyRtneAnsSubmit("", a_Id, slctd_TimeSlts, nxt_Pos);
                    }
                    /*if (nxt_Pos <= 6) {
                        dialog_Brushing(nxt_Pos);
                    } else {
                        dialog.dismiss();
                        Intent intent_FreStylWrk = new Intent(DailyRoutineActivity.this, FreeStyleWorkActivity.class);
                        startActivity(intent_FreStylWrk);
                    }*/

                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dlyRtneAnsSubmit(final String date, final String activityId, final ArrayList<TimeSlot> timeSlotOption, final int nxt_Pos) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Submiting ...");
        progressDialog.show();
        DlyRtneModel rtneModel = new DlyRtneModel();
        rtneModel.setDate(str_Date);
        rtneModel.setActivityId(activityId);
        rtneModel.setTimeSlotOption(timeSlotOption);
        Gson gson = new Gson();
        String str_Json = gson.toJson(rtneModel);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str_Json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request_DlyRtneAns = new JsonObjectRequest(Request.Method.POST, AppConfig.post_DlyRtneInfo, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Res_Result", "" + response.toString());
                        if (nxt_Pos <= 6) {
                            dialog_Brushing(nxt_Pos);
                        } else {
                            Intent intent_FreStylWrk = new Intent(DailyRoutineActivity.this, DailyRtneFreStyleWrktActivity.class);
                            startActivity(intent_FreStylWrk);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (nxt_Pos <= 6) {
                    dialog_Brushing(nxt_Pos);
                } else {
                    Intent intent_FreStylWrk = new Intent(DailyRoutineActivity.this, DailyRtneFreStyleWrktActivity.class);
                    startActivity(intent_FreStylWrk);
                }

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

        requestQueue.add(request_DlyRtneAns);
    }

}
