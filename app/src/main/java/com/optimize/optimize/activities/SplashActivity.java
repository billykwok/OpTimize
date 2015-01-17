package com.optimize.optimize.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.optimize.android.BaseActivity;
import com.optimize.optimize.R;
import com.parse.ParseUser;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ParseUser.getCurrentUser() == null) {
                    startActivity(LoginActivity.class);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }

}
