package com.simplestepapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.simplestepapp.R;
import com.simplestepapp.utils.SessionManager;

/**
 * Created by Srinivas on 12/18/2018.
 */

public class SplashActivity extends AppCompatActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        int SPLASH_TIME = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.isLoggedIn()) {
                    Intent intent_Sign = new Intent(getApplicationContext(), QuestionerActivity.class);
                    startActivity(intent_Sign);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME);
    }
}
