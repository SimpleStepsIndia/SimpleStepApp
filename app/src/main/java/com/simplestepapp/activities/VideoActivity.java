package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.simplestepapp.R;
import com.simplestepapp.adapters.ExerciseAdapter;
import com.simplestepapp.data.Exercise;
import com.simplestepapp.models.UserExerciseMaster;
import com.simplestepapp.network.Constants;
import com.simplestepapp.network.ErrorCodes;
import com.simplestepapp.network.LakmeCallBack;
import com.simplestepapp.network.MyCallBack;
import com.simplestepapp.network.RestClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;


public class VideoActivity extends AppCompatActivity {

    @Bind(R.id.listExercise)
    ListView list_exercises;

    @Bind(R.id.tvExercise)
    TextView tvExercises;

    @Bind(R.id.etSets)
    EditText etSets;

    @Bind(R.id.etReps)
    EditText etReps;

    @Bind(R.id.btStart)
    Button btStart;

    @Bind(R.id.cbSelectAll)
    CheckBox cbSelectAll;

    RestClient mRestClient;
    List<Exercise> mList;

    ExerciseAdapter adapter;
    String selected_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mRestClient = new RestClient();
        setData();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etSets.getText().toString())){
                    Toast.makeText(VideoActivity.this,"Enter valid Sets", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(etReps.getText().toString())){
                    Toast.makeText(VideoActivity.this,"Enter valid Reps", Toast.LENGTH_LONG).show();
                    return;
                }
                selected_videos = "";
                for (int i=0;i<mList.size();i++){
                    if(mList.get(i).getIsSelected()){
                        if(selected_videos.equalsIgnoreCase("")) {
                            selected_videos = selected_videos + mList.get(i).getId();
                        }else {
                            selected_videos = selected_videos +"," + mList.get(i).getId();
                        }
                    }
                }
                if(selected_videos.equalsIgnoreCase("")){
                    Toast.makeText(VideoActivity.this,"Select Atleast One Exercise", Toast.LENGTH_LONG).show();
                    return;
                }

                int mEmployeeId = 0;
              //  mEmployeeId = QuickUtils.prefs.getInt(Constants.USERID, mEmployeeId);
                UserExerciseMaster usrExr = new UserExerciseMaster();
                usrExr.setExercises(selected_videos);
                usrExr.setSets(Integer.parseInt(etSets.getText().toString()));
                usrExr.setReps(Integer.parseInt(etReps.getText().toString()));
                usrExr.setDate(getDateTime());
                usrExr.setUserid(mEmployeeId);

                final ProgressDialog pd = new ProgressDialog(VideoActivity.this);
                pd.setMessage("Please wait..");
                pd.setCancelable(false);
                pd.show();
                mRestClient.insertUserExerciseMaster(usrExr, new LakmeCallBack<UserExerciseMaster>() {
                    @Override
                    public void onFailure(String s, ErrorCodes errorCodes) {
                        Toast.makeText(VideoActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UserExerciseMaster userExercise) {
                        pd.dismiss();
                        Intent intent = new Intent(VideoActivity.this,ExerciseActivity.class);
                        intent.putExtra("sets",etSets.getText().toString());
                        intent.putExtra("reps",etReps.getText().toString());
                        intent.putExtra("selected_videos",selected_videos);
                        intent.putExtra("master_id", String.valueOf(userExercise.getId()));
                        startActivity(intent);

                    }
                });
            }
        });

        cbSelectAll.setEnabled(false);
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(int i=0;i<mList.size();i++){
                        mList.get(i).setIsSelected(true);
                    }
                }else{
                    for(int i=0;i<mList.size();i++){
                        mList.get(i).setIsSelected(false);
                    }
                }

                if(adapter==null){
                    adapter=new ExerciseAdapter(VideoActivity.this, mList);
                    list_exercises.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle(("Are you sure to exit?"))
                .setNegativeButton("Cancel", null) // dismisses by default
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //super.onBackPressed();
                        finish();
                    }
                })
                .create()
                .show();

    }

    private void setData() {
        final ProgressDialog pd = new ProgressDialog(VideoActivity.this);
        pd.setMessage("Please wait..");
        pd.setCancelable(false);
        pd.show();
        mRestClient.getExercises( new MyCallBack<List<Exercise>>() {
            @Override
            public void onFailure(String s, ErrorCodes errorCodes) {
                Toast.makeText(VideoActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List<Exercise> campaignRowDatas) {
                mList = campaignRowDatas;
                for(int i=0;i<mList.size();i++){
                    mList.get(i).setIsSelected(false);
                }
                adapter=new ExerciseAdapter(VideoActivity.this, campaignRowDatas);
                list_exercises.setAdapter(adapter);
                tvExercises.setText(String.valueOf(campaignRowDatas.size()));
                cbSelectAll.setEnabled(true);
                pd.dismiss();
            }
        });
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        return dateFormat.format(date);
    }
}
