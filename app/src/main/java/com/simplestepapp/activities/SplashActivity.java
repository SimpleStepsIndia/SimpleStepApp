package com.simplestepapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.simplestepapp.R;

/**
 * Created by Srinivas on 12/18/2018.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_TIME = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent_Sign=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent_Sign);
                finish();
            }
        }, SPLASH_TIME);
    }
}
