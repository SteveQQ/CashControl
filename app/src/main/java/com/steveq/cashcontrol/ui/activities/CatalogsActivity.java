package com.steveq.cashcontrol.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.AlertListener;
import com.steveq.cashcontrol.interfaces.ItemOnClickListener;
import com.steveq.cashcontrol.interfaces.ItemOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;
import com.steveq.cashcontrol.ui.fragments.dialogs.CreateCatalogDialogFragment;
import com.steveq.cashcontrol.ui.fragments.dialogs.SimpleAlertDialogFragment;

import java.util.ArrayDeque;

public class CatalogsActivity extends AppCompatActivity implements AlertListener, DialogInterface.OnDismissListener, ItemOnClickListener, ItemOnLongClickListener {

    private Toolbar catalogsToolbar;
    private RecyclerView recyclerView;
    private CatalogsAdapter mAdapter;
    private CreateCatalogDialogFragment mCatalogDialogFragment;
    public static final String CATALOG_ID = "CATALOG_ID";
    public static Catalog currentCatalog;
    private ArrayDeque<Catalog> highlightedCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highlightedCatalog = new ArrayDeque<>();
        setContentView(R.layout.activity_catalogs);
        setToolbarView();
        createRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        currentCatalog = null;
        mAdapter.refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
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

            case R.id.addItem:
                mCatalogDialogFragment.show(getFragmentManager(), CreateCatalogDialogFragment.CREATE_CATALOG_TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mAdapter.refreshData();
    }

    @Override
    public void onLongClick(Item item) {
        highlightedCatalog.addFirst((Catalog)item);
        SimpleAlertDialogFragment alertDialog = new SimpleAlertDialogFragment();
        alertDialog.show(getFragmentManager(), SimpleAlertDialogFragment.TAG);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable(SimpleAlertDialogFragment.ITEM_KEY, catalog);
//        alertDialog.setArguments(bundle);
    }

    @Override
    public void onClick(Item item) {

        currentCatalog = (Catalog)item;
        Intent intent = new Intent(this, ReceiptsActivity.class);
        intent.putExtra(CATALOG_ID, item.getId());
        startActivity(intent);

    }

    private void createRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.catalogsRecycler);
        mAdapter = new CatalogsAdapter(this, CatalogsDataSource.getInstance().readCatalogs());

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(mAdapter);
    }

    private void setToolbarView(){
        catalogsToolbar = (Toolbar) findViewById(R.id.catalogsToolbar);
        setSupportActionBar(catalogsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.catalogs_title));
    }

    @Override
    public void reactOnAlert() {
        CatalogsDataSource.getInstance().deleteCatalog(highlightedCatalog.poll());
        mAdapter.refreshData();
    }
}
