package com.simplestepapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.simplestepapp.R;
import com.simplestepapp.activities.DailyRtneFreStyleWrktActivity;
import com.simplestepapp.activities.FreStyleVideoPlayerActivity;
import com.simplestepapp.activities.UnityActivity;
import com.simplestepapp.adapters.TransformAdapter;
import com.simplestepapp.utils.MyGridView;
import com.simplestepapp.utils.SessionManager;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TransformFragment extends Fragment {
    GridView grid_Transform;
    ArrayList<String> list_Transforms;
    TransformAdapter transformAdapter;
    SessionManager sessionManager;
    String s_WkUpTime = "", s_WkUpQtnOption = "", s_WkUpWhyOptn = "", colorName = "", userName = "", eMailId = "", token = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_transforms, container, false);
        initviews(v);
        sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            userName = user.get(SessionManager.KEY_NAME);
            eMailId = user.get(SessionManager.KEY_EMAIL);
            token = user.get(SessionManager.KEY_TOKEN);
            Log.d("Token",""+token);
        }
        list_Transforms = new ArrayList<>();
        list_Transforms.add("Visualize Morning Routine");
        list_Transforms.add("Track your morning routine");
        list_Transforms.add("Personalized freestyle workout");
        list_Transforms.add("Personalized easy yoga");
        list_Transforms.add("Affirmations");
    /*    list_Transforms.add("Grattitude");
        list_Transforms.add("SunBath");
        list_Transforms.add("Water Intake");
        list_Transforms.add("To - Do");*/
        transformAdapter = new TransformAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), list_Transforms,token);
        grid_Transform.setAdapter(transformAdapter);

        grid_Transform.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Positonfra", "" + position);
                /*Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), UnityActivity.class);
                startActivity(intent);*/

                if (position == 0) {
                    Intent intent = new Intent(getActivity(), UnityActivity.class);
                    intent.putExtra("ScreenKey","visualization");
                    intent.putExtra("AndroidValue", ""+token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), UnityActivity.class);
                    intent.putExtra("ScreenKey","visualizationMrt");
                    intent.putExtra("AndroidValue", ""+token);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), FreStyleVideoPlayerActivity.class);
                    intent.putExtra("sets", "3");
                    intent.putExtra("reps", "5");
                    intent.putExtra("selected_videos", "461665");
                    intent.putExtra("master_id", "5545688655");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    private void initviews(View v) {
        grid_Transform = v.findViewById(R.id.grid_Transform);
    }
}
