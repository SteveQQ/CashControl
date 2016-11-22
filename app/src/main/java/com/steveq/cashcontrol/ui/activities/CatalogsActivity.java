package com.steveq.cashcontrol.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.model.Catalog;

public class CatalogsActivity extends AppCompatActivity {

    Toolbar catalogsToolbar;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogs);

        create = (Button) findViewById(R.id.create);

        catalogsToolbar = (Toolbar) findViewById(R.id.catalogsToolbar);
        setSupportActionBar(catalogsToolbar);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CatalogsDataSource(CatalogsActivity.this).createCatalog(new Catalog(-1, "test", 10, 10));
                new CatalogsDataSource(CatalogsActivity.this).createCatalog(new Catalog(-1, "test2", 20, 20));
            }
        });
    }
}
