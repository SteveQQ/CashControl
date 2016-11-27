package com.steveq.cashcontrol.ui.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.CatalogsAdapter;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.ui.fragments.CreateCatalogDialogFragment;

public class CatalogsActivity extends AppCompatActivity implements DialogInterface.OnDismissListener{

    private Toolbar catalogsToolbar;
    private RecyclerView recyclerView;
    private CatalogsAdapter mAdapter;
    private CreateCatalogDialogFragment mCatalogDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogs);
        setToolbarView();
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.catalogsRecycler);
        mAdapter = new CatalogsAdapter(CatalogsDataSource.getInstance().readCatalogs());

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalogs_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UserManager.getInstance().logOut(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mCatalogDialogFragment = new CreateCatalogDialogFragment();

        switch (item.getItemId()){

            case android.R.id.home:
                UserManager.getInstance().logOut(this);
                return true;

            case R.id.addCatalog:
                mCatalogDialogFragment.show(getFragmentManager(), CreateCatalogDialogFragment.CREATE_CATALOG_TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setToolbarView(){
        catalogsToolbar = (Toolbar) findViewById(R.id.catalogsToolbar);
        setSupportActionBar(catalogsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mAdapter.setCatalogs(CatalogsDataSource.getInstance().readCatalogs());
        mAdapter.notifyDataSetChanged();
    }
}
