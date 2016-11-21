package com.steveq.cashcontrol.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.steveq.cashcontrol.R;

public class CatalogsActivity extends AppCompatActivity {

    Toolbar catalogsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogs);

        catalogsToolbar = (Toolbar) findViewById(R.id.catalogsToolbar);
        setSupportActionBar(catalogsToolbar);

    }
}
