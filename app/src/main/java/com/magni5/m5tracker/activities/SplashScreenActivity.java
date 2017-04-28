package com.magni5.m5tracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.magni5.m5tracker.R;
import com.magni5.m5tracker.utils.Constants;
import com.magni5.m5tracker.utils.Utility;

/**
 * Created by Manikanta on 4/25/2017.
 */

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Utility.setTranslateStatusBar(SplashScreenActivity.this);
        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashScreenActivity.this, Constants.LOGIN_SESSION_ID))) {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        mSplashHandler.postDelayed(action, Constants.SPLASH_TIME_OUT);
    }
}