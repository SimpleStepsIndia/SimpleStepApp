package com.simplestepapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.simplestepapp.R;
import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.models.UserExerciseMaster;
import com.simplestepapp.network.ErrorCodes;
import com.simplestepapp.network.LakmeCallBack;
import com.simplestepapp.network.MyCallBack;
import com.simplestepapp.network.RestClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ExerciseResultActivity extends AppCompatActivity {

    RestClient mRestClient;
    String mStrReps, mStrSets,mStrMasterId, mStrSelectedExercices;
    int totalTime=0,workoutTime=0,restTime=0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);
        ButterKnife.bind(this);
        mRestClient = new RestClient();

        mStrReps = getIntent().getStringExtra("reps");
        mStrSets = getIntent().getStringExtra("sets");
        mStrMasterId = getIntent().getStringExtra("master_id");
        mStrSelectedExercices = getIntent().getStringExtra("selected_videos");

        tvExercises.setText(String.valueOf(mStrSelectedExercices.split(",").length));
        tvReps.setText(mStrReps);
        tvSets.setText(mStrSets);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(ExerciseResultActivity.this);
                pd.setMessage("Please wait..");
                pd.setCancelable(false);
                pd.show();
                UserExerciseMaster userExerciseMaster = new UserExerciseMaster();
                userExerciseMaster.setWorkouttime(String.valueOf(workoutTime));
                userExerciseMaster.setResttime(String.valueOf(restTime));
                userExerciseMaster.setTotaltime(String.valueOf(workoutTime));

                mRestClient.updateUserExerciseMaster(mStrMasterId,userExerciseMaster, new LakmeCallBack<UserExerciseMaster>() {
                    @Override
                    public void onFailure(String s, ErrorCodes errorCodes) {
                        Toast.makeText(ExerciseResultActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UserExerciseMaster userExercise) {
                        pd.dismiss();
                        Intent intent = new Intent(ExerciseResultActivity.this, ExerciseResultActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
            }
        });

        //setData();
    }

    /*private void setData() {
        final ProgressDialog pd = new ProgressDialog(ExerciseResultActivity.this);
        pd.setMessage("Please wait..");
        pd.setCancelable(false);
        pd.show();
        mRestClient.getExercises( new MyCallBack<List<Exercise>>() {
            @Override
            public void onFailure(String s, ErrorCodes errorCodes) {
                Toast.makeText(ExerciseResultActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(final List<Exercise> exerciseRowDatas) {

                mRestClient.getUserExercises(getIntent().getStringExtra("master_id"),new MyCallBack<List<UserExercise>>() {
                    @Override
                    public void onFailure(String s, ErrorCodes errorCodes) {
                        Toast.makeText(ExerciseResultActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(List<UserExercise> campaignRowDatas) {
                        pd.dismiss();
                        for(int i=0;i<campaignRowDatas.size();i++){
                            if(campaignRowDatas.get(i).getGap()!=null){
                                restTime=restTime+ Integer.parseInt(campaignRowDatas.get(i).getGap());
                                totalTime = totalTime+ Integer.parseInt(campaignRowDatas.get(i).getGap());
                            }
                            if(campaignRowDatas.get(i).getWorkouttime()!=null){
                                workoutTime = workoutTime+ Integer.parseInt(campaignRowDatas.get(i).getWorkouttime());
                                totalTime = totalTime+ Integer.parseInt(campaignRowDatas.get(i).getWorkouttime());
                            }
                        }

                        tvWorkoutTime.setText(String.valueOf((workoutTime%3600)/60)+" Mins "+ String.valueOf((workoutTime% 3600)%60)+" Secs.");
                        tvTotalTime.setText(String.valueOf((totalTime%3600)/60)+" Mins "+ String.valueOf((totalTime% 3600)%60)+" Secs.");
                        tvRestTime.setText(String.valueOf((restTime%3600)/60)+" Mins "+ String.valueOf((restTime% 3600)%60)+" Secs.");
                        ExerciseResultAdapter adapter=new ExerciseResultAdapter(ExerciseResultActivity.this, campaignRowDatas,exerciseRowDatas);
                        list_exercises.setAdapter(adapter);
                    }
                });
            }
        });
    }*/
}
