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
import com.simplestepapp.activities.UnityActivity;
import com.simplestepapp.adapters.TransformAdapter;
import com.simplestepapp.utils.MyGridView;

import java.util.ArrayList;
import java.util.Objects;

public class TransformFragment extends Fragment {
    GridView grid_Transform;
    ArrayList<String> list_Transforms;
    TransformAdapter transformAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_transforms, container, false);
        initviews(v);
        list_Transforms = new ArrayList<>();
        list_Transforms.add("Visualize Morning Routine");
        list_Transforms.add("Track your morning routine");
        list_Transforms.add("Personalized freestyle workout");
        list_Transforms.add("Personalized easy yoga");
        list_Transforms.add("Affirmations");
        list_Transforms.add("Grattitude");
        list_Transforms.add("SunBath");
        list_Transforms.add("Water Intake");
        list_Transforms.add("To - Do");
        transformAdapter = new TransformAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), list_Transforms);
        grid_Transform.setAdapter(transformAdapter);

        grid_Transform.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Positon", "" + position);
                Intent intent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), UnityActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void initviews(View v) {
        grid_Transform = v.findViewById(R.id.grid_Transform);
    }
}
