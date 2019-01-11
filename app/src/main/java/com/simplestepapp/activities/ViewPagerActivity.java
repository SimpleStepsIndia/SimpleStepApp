package com.simplestepapp.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.simplestepapp.R;
import com.simplestepapp.fragments.BrushingFragment;
import com.simplestepapp.fragments.ColonCleanFragment;
import com.simplestepapp.fragments.MentalFitFragment;
import com.simplestepapp.fragments.PhysicalFitFragment;
import com.simplestepapp.fragments.SunBothFragment;
import com.simplestepapp.fragments.WakeUpFragment;
import com.simplestepapp.fragments.WaterInTakeFragment;
import com.simplestepapp.models.AllQuestionsModel;
import com.simplestepapp.models.QAnswerModel;
import com.simplestepapp.models.Questioner;
import com.simplestepapp.utils.Constants;
import com.simplestepapp.utils.Toaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener {

    private static final int NUMBER_OF_PAGES = 7;
    @SuppressLint("StaticFieldLeak")
    public static ViewPager pager;
    public static ArrayList<String> timeSlots = new ArrayList<>();
    public static ArrayList<QAnswerModel> qAnswerModelArrayList = new ArrayList<>();
    public static List<Questioner> questionerArrayList;
    private ProgressDialog progressDialog;
    RequestQueue requestQueue;

    public static int dis_Position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        toolbarsetUp();
        progressDialog = new ProgressDialog(ViewPagerActivity.this);
        requestQueue = Volley.newRequestQueue(ViewPagerActivity.this);

        timeSlots = new ArrayList<>();
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

        pager = (ViewPager) findViewById(R.id.viewPager);
        //pager.beginFakeDrag();

        get_QuestionsAll();
    }

    public void get_QuestionsAll() {

        //  http://125.16.1.70:8191/echallan/officerDetails?userID=Maruthiios&pwd=8341646667

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //String url_Login = AppConfig.user_Login_URL + "?userID=" + userId + "&pwd=" + pwd;

        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest user_Login_Req = new StringRequest(Request.Method.GET, Constants.get_QuestionsAll, new Response.Listener<String>() {
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
                    Log.d("TExtForm", "" + questionerArrayList.get(1).getQuestion());
                    pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                    pager.addOnPageChangeListener(ViewPagerActivity.this);

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

        requestQueue.add(user_Login_Req);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        /**
         * Constructor
         *
         * @param fm
         */
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment based on the position.
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WakeUpFragment.newInstance("FirstFragment, Instance 1");
                case 1:
                    return BrushingFragment.newInstance("SecondFragment, Instance2");
                case 2:
                    return ColonCleanFragment.newInstance("ThirdFragment, Instance3");
                case 3:
                    return WaterInTakeFragment.newInstance("FirstFragment, Instance 1");
                case 4:
                    return PhysicalFitFragment.newInstance("SecondFragment, Instance2");
                case 5:
                    return MentalFitFragment.newInstance("ThirdFragment, Instance3");
                case 6:
                    return SunBothFragment.newInstance("ThirdFragment, Instance3");
                default:
                    return WakeUpFragment.newInstance("FirstFragment, Default");
            }
        }

        /**
         * Return the number of pages.
         *
         * @return
         */
        @Override
        public int getCount() {
            return NUMBER_OF_PAGES;
        }


    }

}
