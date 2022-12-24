package com.ltmt5.fpoly_friend_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            if (App.sharePref.isFirstTimeLaunch()) {
                startActivity(new Intent(this, OnBoard1Activity.class));
            } else {
                startActivity(new Intent(this, LogInActivity.class));
            }
            finish();
        }, 1500);
    }
}