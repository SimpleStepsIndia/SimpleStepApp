package com.simplestepapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.simplestepapp.R;
import com.simplestepapp.adapters.CustomAdapter;
import com.simplestepapp.adapters.CustomWakeupAdapter;
import com.simplestepapp.models.AllQuestionsModel;
import com.simplestepapp.models.AnswerOptions;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.Questioner;
import com.simplestepapp.models.WhyOptions;
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
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Srinivas on 1/17/2019.
 */

public class QuestionerActivity extends AppCompatActivity {

    public static List<Questioner> questionerArrayList;
    public static ArrayList<QAnswerModel> qAnswerModelArrayList = new ArrayList<>();
    ArrayList<String> timeSlots;
    ArrayList<AnswerOptions> answerOptions;
    ArrayList<WhyOptions> whyOptions;

    private ProgressDialog progressDialog;
    RequestQueue requestQueue;

    ScrollView scroll_View;

    MyGridView grid_view;

    CustomWakeupAdapter customWakeupAdapter;
    CustomAdapter customAdapter;

    LinearLayout lyt_list_Why, lyt_QtnOptns;

    AppCompatTextView txt_QtnHdng, txt_QtnCaptn, txt_QtnOptns, txt_Next;

    AppCompatImageView img_QtnHdng;

    RadioGroup rG_WakeUp, rGrp_WhyOptions;

    RadioButton rBtn_WOne, rBtn_WTwo, rBtn_WThre, rBtn_WFur, rBtn_op1, rBtn_op2, rBtn_op3;

    String s_WkUpTime = "", s_WkUpQtnOption = "", s_WkUpWhyOptn = "", colorName = "", userName = "", eMailId = "", token = "", jsonExData = "";

    int sPosition = -1, timeSlotMarks = 0, optionsMarks = 0, finalMarks = 0, nxt_Pos = 0;

    public static int dis_Position = 0;


    public Integer[] imgArray_Qtns = {R.drawable.wakeup_icon, R.drawable.brushicon, R.drawable.waterintake, R.drawable.colon,
            R.drawable.physical_fitness, R.drawable.mental_fitness, R.drawable.sun_shine, R.drawable.affermations, R.drawable.gratittude, R.drawable.to_do,
            R.drawable.mental_fitness, R.drawable.physical_fitness, R.drawable.sun_shine};

    Animation anim_SlideLeft, anim_FadeIn, anim_SlideRight, anim_Bounce;

    SessionManager sessionManager;

    private Dialog dialog;
    int slctdTimeSlotCount=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_wakeup);
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
        get_QuestionsAll();
        slctdTimeSlotCount=0;

        rG_WakeUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                lyt_list_Why.setVisibility(View.VISIBLE);
                scroll_View.fullScroll(ScrollView.FOCUS_DOWN);
                switch (checkedId) {
                    case R.id.rBtn_WOne:
                        s_WkUpQtnOption = rBtn_WOne.getText().toString();
                        optionsMarks = 0;
                        break;
                    case R.id.rBtn_WTwo:
                        s_WkUpQtnOption = rBtn_WTwo.getText().toString();
                        optionsMarks = 25;
                        break;
                    case R.id.rBtn_WThre:
                        s_WkUpQtnOption = rBtn_WThre.getText().toString();
                        optionsMarks = 50;
                        break;
                    case R.id.rBtn_WFur:
                        s_WkUpQtnOption = rBtn_WFur.getText().toString();
                        optionsMarks = 75;
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
                txt_Next.setVisibility(View.VISIBLE);
                scroll_View.fullScroll(ScrollView.FOCUS_DOWN);
                switch (checkedId) {

                    case R.id.rBtn_op1:
                        s_WkUpWhyOptn = rBtn_op1.getText().toString();
                        break;
                    case R.id.rBtn_op2:
                        s_WkUpWhyOptn = rBtn_op2.getText().toString();
                        break;
                    case R.id.rBtn_op3:
                        s_WkUpWhyOptn = rBtn_op3.getText().toString();
                        break;
                    default:
                        s_WkUpWhyOptn = "";
                        break;
                }
            }
        });

        txt_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalMarks = timeSlotMarks - optionsMarks;
                if (finalMarks == 100) {
                    colorName = "G";
                } else if (finalMarks == 75) {
                    colorName = "O";
                } else if (finalMarks == 50) {
                    colorName = "O";
                } else {
                    colorName = "R";
                }
                QAnswerModel qAnswerModel = new QAnswerModel();
                qAnswerModel.setTimeSlotOption(s_WkUpTime);
                qAnswerModel.setAnswerOption(s_WkUpQtnOption);
                qAnswerModel.setWhyOption(s_WkUpWhyOptn);
                qAnswerModel.setS_Position(sPosition);
                qAnswerModel.setColorCode(colorName);
                qAnswerModel.setQtnName(questionerArrayList.get(0).getQuestion());
                qAnswerModel.setQuestionId(questionerArrayList.get(0).get_id());
                qAnswerModelArrayList = new ArrayList<>();
                qAnswerModelArrayList.add(qAnswerModel);
                nxt_Pos = 1;
                answer_Submission(questionerArrayList.get(0).get_id(), s_WkUpTime, s_WkUpQtnOption, s_WkUpWhyOptn, nxt_Pos);
                timeSlots.add("None");


            }
        });
    }

    private void dialog_Brushing(final int postn) {
        dialog = new Dialog(QuestionerActivity.this, android.R.style.DeviceDefault_Light_ButtonBar);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.frag_brushing);
        initviews(dialog);
        try {
            img_QtnHdng.setBackgroundResource(imgArray_Qtns[postn]);
            txt_QtnHdng.setText(questionerArrayList.get(postn).getQuestion());
            txt_QtnCaptn.setText(questionerArrayList.get(postn).getDescription());
            txt_QtnOptns.setText(questionerArrayList.get(postn).getAnswerCaption());
            answerOptions = new ArrayList<>();
            answerOptions = questionerArrayList.get(postn).getAnswerOptions();
            if (answerOptions.size() > 2) {
                rBtn_WThre.setVisibility(View.VISIBLE);
                rBtn_WFur.setVisibility(View.VISIBLE);
                rBtn_WOne.setText(answerOptions.get(0).getDescription());
                rBtn_WTwo.setText(answerOptions.get(1).getDescription());
                rBtn_WThre.setText(!answerOptions.get(2).getDescription().equals("") ? answerOptions.get(2).getDescription() : "");
                rBtn_WFur.setText(!(answerOptions.get(3).getDescription() == null) ? answerOptions.get(3).getDescription() : "");
            } else {
                rBtn_WOne.setText(answerOptions.get(0).getDescription());
                rBtn_WTwo.setText(answerOptions.get(1).getDescription());
                rBtn_WThre.setVisibility(View.GONE);
                rBtn_WFur.setVisibility(View.GONE);
            }
            whyOptions = new ArrayList<>();
            whyOptions = questionerArrayList.get(postn).getWhyOptions();
            rBtn_op1.setText(whyOptions.get(0).getDescription());
            rBtn_op2.setText(whyOptions.get(1).getDescription());
            rBtn_op3.setText(whyOptions.get(2).getDescription());
            customAdapter = new CustomAdapter(QuestionerActivity.this, timeSlots);
            grid_view.setAdapter(customAdapter);

            grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    customAdapter.setSelectedIndex(position);
                    if (sPosition==position){
                        slctdTimeSlotCount++;
                    }else{
                        slctdTimeSlotCount--;
                    }
                    if (slctdTimeSlotCount<4) {
                        s_WkUpTime = (String) parent.getItemAtPosition(position);
                        sPosition = position;
                        customAdapter.notifyDataSetChanged();
                        lyt_QtnOptns.setVisibility(View.VISIBLE);
                        lyt_list_Why.startAnimation(anim_SlideRight);
                        if (position == timeSlots.size() - 1) {
                            rBtn_WFur.setChecked(true);
                        }
                    }else{
                        Toaster.showWarningMessage("Already four Timeslots selected ! ");
                        lyt_QtnOptns.setVisibility(View.GONE);
                    }
                }
            });

            rG_WakeUp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    scroll_View.fullScroll(ScrollView.FOCUS_DOWN);
                    lyt_list_Why.setVisibility(View.VISIBLE);
                    lyt_list_Why.startAnimation(anim_SlideLeft);
                    switch (checkedId) {
                        case R.id.rBtn_WOne:
                            s_WkUpQtnOption = rBtn_WOne.getText().toString();
                            colorName = "G";
                            break;
                        case R.id.rBtn_WTwo:
                            s_WkUpQtnOption = rBtn_WTwo.getText().toString();
                            colorName = "O";
                            break;
                        case R.id.rBtn_WThre:
                            s_WkUpQtnOption = rBtn_WThre.getText().toString();
                            colorName = "O";
                            break;
                        case R.id.rBtn_WFur:
                            s_WkUpQtnOption = rBtn_WFur.getText().toString();
                            colorName = "R";
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
                    txt_Next.setVisibility(View.VISIBLE);
                    switch (checkedId) {
                        case R.id.rBtn_op1:
                            s_WkUpWhyOptn = rBtn_op1.getText().toString();
                            break;
                        case R.id.rBtn_op2:
                            s_WkUpWhyOptn = rBtn_op2.getText().toString();
                            break;
                        case R.id.rBtn_op3:
                            s_WkUpWhyOptn = rBtn_op3.getText().toString();
                            break;
                        default:
                            s_WkUpWhyOptn = "";
                            break;
                    }
                }
            });
            txt_Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QAnswerModel qAnswerModel = new QAnswerModel();
                    qAnswerModel.setTimeSlotOption(s_WkUpTime);
                    qAnswerModel.setAnswerOption(s_WkUpQtnOption);
                    qAnswerModel.setWhyOption(s_WkUpWhyOptn);
                    qAnswerModel.setS_Position(sPosition);
                    qAnswerModel.setColorCode(colorName);
                    qAnswerModel.setQuestionId(questionerArrayList.get(nxt_Pos).get_id());
                    qAnswerModel.setQtnName(questionerArrayList.get(nxt_Pos).getQuestion()
                    );
                    qAnswerModelArrayList.add(qAnswerModel);
                    ++nxt_Pos;
                    if (nxt_Pos <= questionerArrayList.size()) {
                        //dialog_Brushing(nxt_Pos);
                        answer_Submission(questionerArrayList.get(nxt_Pos - 1).get_id(), s_WkUpTime, s_WkUpQtnOption, s_WkUpWhyOptn, nxt_Pos);
                    }

                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initviews() {
        anim_SlideLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_left);
        anim_SlideRight = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right);
        anim_Bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);

        txt_QtnHdng = findViewById(R.id.txt_QtnHdng);
        txt_QtnCaptn = findViewById(R.id.txt_QtnCaptn);
        txt_QtnOptns = findViewById(R.id.txt_QtnOptns);
        img_QtnHdng = findViewById(R.id.img_QtnHdng);
        rG_WakeUp = findViewById(R.id.rG_WakeUp);
        rGrp_WhyOptions = findViewById(R.id.rGrp_WhyOptions);
        rBtn_WOne = findViewById(R.id.rBtn_WOne);
        rBtn_WTwo = findViewById(R.id.rBtn_WTwo);
        rBtn_WThre = findViewById(R.id.rBtn_WThre);
        rBtn_WFur = findViewById(R.id.rBtn_WFur);
        rBtn_op1 = findViewById(R.id.rBtn_op1);
        rBtn_op2 = findViewById(R.id.rBtn_op2);
        rBtn_op3 = findViewById(R.id.rBtn_op3);
        grid_view = findViewById(R.id.grid_view);
        lyt_list_Why = findViewById(R.id.lyt_list_Why);
        lyt_QtnOptns = findViewById(R.id.lyt_QtnOptns);
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
        timeSlots.add("> 9:00");

    }

    private void initviews(Dialog dialog) {

        txt_QtnHdng = dialog.findViewById(R.id.txt_QtnHdng);
        txt_QtnCaptn = dialog.findViewById(R.id.txt_QtnCaptn);
        txt_QtnOptns = dialog.findViewById(R.id.txt_QtnOptns);
        img_QtnHdng = dialog.findViewById(R.id.img_QtnHdng);
        rG_WakeUp = dialog.findViewById(R.id.rG_WakeUp);
        rGrp_WhyOptions = dialog.findViewById(R.id.rGrp_WhyOptions);
        rBtn_WOne = dialog.findViewById(R.id.rBtn_WOne);
        rBtn_WTwo = dialog.findViewById(R.id.rBtn_WTwo);
        rBtn_WThre = dialog.findViewById(R.id.rBtn_WThre);
        rBtn_WFur = dialog.findViewById(R.id.rBtn_WFur);
        rBtn_op1 = dialog.findViewById(R.id.rBtn_op1);
        rBtn_op2 = dialog.findViewById(R.id.rBtn_op2);
        rBtn_op3 = dialog.findViewById(R.id.rBtn_op3);
        grid_view = dialog.findViewById(R.id.grid_view);
        lyt_list_Why = dialog.findViewById(R.id.lyt_list_Why);
        lyt_QtnOptns = dialog.findViewById(R.id.lyt_QtnOptns);
        txt_Next = dialog.findViewById(R.id.txt_Next);
        scroll_View = dialog.findViewById(R.id.scroll_View);
    }

    public void get_QuestionsAll() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest user_Login_Req = new StringRequest(Request.Method.GET, AppConfig.get_QuestionsAll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    questionerArrayList = new ArrayList<>();
                    AllQuestionsModel allQuestionsModel = new AllQuestionsModel();
                    Log.d("LiveData", "" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("questioner"));
                    questionerArrayList = new Gson().fromJson(String.valueOf(jsonArray), AllQuestionsModel.class);
                    txt_QtnHdng.setText(questionerArrayList.get(0).getQuestion());
                    txt_QtnCaptn.setText(questionerArrayList.get(0).getTimeSlotCaption());
                    txt_QtnOptns.setText(questionerArrayList.get(0).getAnswerCaption());
                    answerOptions = new ArrayList<>();
                    answerOptions = questionerArrayList.get(0).getAnswerOptions();
                    rBtn_WOne.setText(answerOptions.get(0).getDescription());
                    rBtn_WTwo.setText(answerOptions.get(1).getDescription());
                    rBtn_WThre.setText(answerOptions.get(2).getDescription());
                    rBtn_WFur.setText(answerOptions.get(3).getDescription());
                    whyOptions = new ArrayList<>();
                    whyOptions = questionerArrayList.get(0).getWhyOptions();
                    rBtn_op1.setText(whyOptions.get(0).getDescription());
                    rBtn_op2.setText(whyOptions.get(1).getDescription());
                    rBtn_op3.setText(whyOptions.get(2).getDescription());

                    customWakeupAdapter = new CustomWakeupAdapter(QuestionerActivity.this, timeSlots);
                    grid_view.setAdapter(customWakeupAdapter);
                    grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            customWakeupAdapter.setSelectedIndex(position);
                            s_WkUpTime = (String) parent.getItemAtPosition(position);
                            sPosition = position;
                            dis_Position = position;
                            customWakeupAdapter.notifyDataSetChanged();
                            if (sPosition <= 5) {
                                timeSlotMarks = 100;
                            } else if (sPosition <= 9) {
                                timeSlotMarks = 75;
                            } else if (sPosition <= 13) {
                                timeSlotMarks = 50;
                            } else {
                                timeSlotMarks = 25;
                            }

                            lyt_QtnOptns.setVisibility(View.VISIBLE);
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + token);
                return headers;
            }
        };


        requestQueue.add(user_Login_Req);
    }

    public void answer_Submission(final String questionId, final String timeSlotOption, final String answerOption,
                                  final String whyOption, final int next_Pos) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog.setMessage("Submiting ...");
        progressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("questionId", questionId);
        params.put("timeSlotOption", timeSlotOption);
        params.put("answerOption", answerOption);
        params.put("whyOption", whyOption);
        JSONObject jsonObject = new JSONObject(params);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, AppConfig.post_QuestionsInfo, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.d("Responce", "" + response.toString());
                    if (null != response.toString()) {
                        String status = response.getString("message");
                        if ("SUCCESS".equals(status)) {

                            if (nxt_Pos <= questionerArrayList.size() - 1) {
                                dialog_Brushing(nxt_Pos);
                            } else {
                                sessionManager.questn_SubSession();
                                Gson gson = new Gson();
                                String listString = gson.toJson(
                                        qAnswerModelArrayList,
                                        new TypeToken<ArrayList<QAnswerModel>>() {
                                        }.getType());
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(listString);
                                    jsonExData = jsonArray.toString();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    jsonExData = "No Data";
                                }
                                Intent intent = new Intent(getApplicationContext(), UnityActivity.class);
                                intent.putExtra("ScreenKey", "SummaryForQuestions");
                                intent.putExtra("JsonArrayValue", "" + jsonExData);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            Toaster.showWarningMessage("" + status);
                            if (nxt_Pos <= questionerArrayList.size() - 1) {
                                dialog_Brushing(nxt_Pos);
                            } else {
                                dialog.dismiss();
                                sessionManager.questn_SubSession();
                                Gson gson = new Gson();
                                String listString = gson.toJson(
                                        qAnswerModelArrayList,
                                        new TypeToken<ArrayList<QAnswerModel>>() {
                                        }.getType());
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(listString);
                                    jsonExData = jsonArray.toString();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    jsonExData = "No Data";
                                }
                                Log.d("JsonArrayValue", "" + Objects.requireNonNull(jsonArray).toString());
                                Intent intent = new Intent(getApplicationContext(), UnityActivity.class);
                                intent.putExtra("ScreenKey", "SummaryForQuestions");
                                intent.putExtra("JsonArrayValue", "" + jsonExData);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                              /*  Intent intent = new Intent(getApplicationContext(), ConclusionActivity.class);
                                startActivity(intent);*/
                            }
                        }
                    } else {
                        Toaster.showErrorMessage("Answer submission Failed !");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("error in response", "Error: " + error.getMessage());
                if (nxt_Pos <= questionerArrayList.size() - 1) {
                    dialog_Brushing(nxt_Pos);
                } else {
                    sessionManager.questn_SubSession();
                    Gson gson = new Gson();
                    String listString = gson.toJson(
                            qAnswerModelArrayList,
                            new TypeToken<ArrayList<QAnswerModel>>() {
                            }.getType());
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(listString);
                        jsonExData = jsonArray.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        jsonExData = "No Data";
                    }
                    Log.d("JsonArrayValue", "" + Objects.requireNonNull(jsonArray).toString());
                    Intent intent = new Intent(getApplicationContext(), UnityActivity.class);
                    intent.putExtra("ScreenKey", "SummaryForQuestions");
                    intent.putExtra("JsonArrayValue", "" + jsonExData);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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

        requestQueue.add(req);
    }

    @Override
    public void onBackPressed() {
        dialog.dismiss();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        startActivity(a);
    }
}
