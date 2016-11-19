package com.steveq.cashcontrol.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.steveq.cashcontrol.database.ReceiptDataBaseHelper;

public class SplashActivity extends Activity {

    ReceiptDataBaseHelper rdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rdbHelper = new ReceiptDataBaseHelper(this);
        rdbHelper.getReadableDatabase();
        rdbHelper.close();
        goLogging();
    }

    private void goLogging() {
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }
}
