package com.steveq.cashcontrol.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;

public class LoggingActivity extends Activity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_activity);

        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mCreateButton = (Button) findViewById(R.id.createButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance().logIn(LoggingActivity.this, mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager.getInstance().createNewUser(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
