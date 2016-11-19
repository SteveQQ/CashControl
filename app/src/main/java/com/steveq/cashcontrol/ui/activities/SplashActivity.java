package com.steveq.cashcontrol.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goLogging();
    }

    private void goLogging() {
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }
}
