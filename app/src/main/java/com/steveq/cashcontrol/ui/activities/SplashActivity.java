package com.steveq.cashcontrol.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.steveq.cashcontrol.database.ReceiptDataBaseHelper;

public class SplashActivity extends Activity {

    ReceiptDataBaseHelper rdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new dbInitializationAsyncTask().execute();
    }

    private void goLogging() {
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }


    private class dbInitializationAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ReceiptDataBaseHelper rdbHelper = new ReceiptDataBaseHelper(SplashActivity.this);
            try {
                rdbHelper.getReadableDatabase();
                rdbHelper.close();
                return true;
            } catch(SQLiteException sqle){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(!aBoolean){
                goLogging();
                Toast.makeText(SplashActivity.this, "Cannot connect to database", Toast.LENGTH_LONG).show();
            }else{
                goLogging();
                Toast.makeText(SplashActivity.this, "database created", Toast.LENGTH_LONG).show();
            }

        }
    }
}
