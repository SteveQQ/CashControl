package com.steveq.cashcontrol.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.steveq.cashcontrol.R;

public class ReceiptsActivity extends AppCompatActivity {

    private Toolbar receiptsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogs);
        setToolbarView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setToolbarView(){
        receiptsToolbar = (Toolbar) findViewById(R.id.receiptsToolbar);
        setSupportActionBar(receiptsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
