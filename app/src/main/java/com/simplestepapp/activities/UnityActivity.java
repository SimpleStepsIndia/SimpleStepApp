package com.simplestepapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.sms.sma.UnityPlayerActivity;

public class UnityActivity extends UnityPlayerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent_Profile = new Intent(getApplicationContext(), BottomNavigationActivity.class);
            startActivity(intent_Profile);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onGameFinish(String json) {
        Log.d("JsonUnity", "" + json);
    }

    public void summaryExit(String sExit) {
        Log.d("JsonUnity", "" + sExit);
        Intent resultIntent = new Intent(this, ProfileActivity.class);
        startActivity(resultIntent);
    }

    public void OnWorkOutEntry(String physicalFitness) {
        Log.d("physicalFitness", "" + physicalFitness);
        Intent intent = new Intent(getApplicationContext(), FreStyleVideoPlayerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
