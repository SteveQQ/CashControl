package com.steveq.cashcontrol.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.model.Catalog;

import java.util.ArrayList;

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

        new CatalogsDataSource(CatalogsActivity.this).createCatalog(new Catalog(-1, UserManager.mCurrentUser.getId(), 0, "test", 10, 10));
        new CatalogsDataSource(CatalogsActivity.this).createCatalog(new Catalog(-1, UserManager.mCurrentUser.getId(), 0, "test2", 20, 20));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Catalog> ca = new CatalogsDataSource(CatalogsActivity.this).readCatalogs();
                new CatalogsDataSource(CatalogsActivity.this).deleteCatalog(ca.get(1));
                new CatalogsDataSource(CatalogsActivity.this).updateCatalogSum(ca.get(0), 12.23);
                ArrayList<Catalog> ca2 = new CatalogsDataSource(CatalogsActivity.this).readCatalogs();
                for(Catalog c : ca2){
                    Log.d("CC", Double.toString(c.getSum()));
                }
            }
        });
    }
}
