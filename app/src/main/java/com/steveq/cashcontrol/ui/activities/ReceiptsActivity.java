package com.steveq.cashcontrol.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.CustomPagerAdapter;
import com.steveq.cashcontrol.interfaces.CatalogOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.ui.fragments.dialogs.CreateReceiptDialogFragment;
import com.steveq.cashcontrol.ui.fragments.QueriesFragment;
import com.steveq.cashcontrol.ui.fragments.ReceiptsFragment;
import com.steveq.cashcontrol.ui.fragments.ReportFragment;

import java.util.ArrayList;

public class ReceiptsActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, CatalogOnLongClickListener {

    private Toolbar receiptsToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mPagerAdapter;
    public static final String FRAGMENT_NAME = "fragment_name";
    private CreateReceiptDialogFragment mReceiptDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        Intent intent = getIntent();
        int id = intent.getIntExtra(CatalogsActivity.CATALOG_ID, -1);

        setPagerView();
        setToolbarView();

    }

    private void setPagerView() {
        mFragments = getFragments();

        mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager = (ViewPager) findViewById(R.id.receiptsPager);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.receiptsTab);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private ArrayList<Fragment> getFragments() {

        ArrayList<Fragment> result = new ArrayList<>();
        result.add(new ReceiptsFragment());
        result.add(new QueriesFragment());
        result.add(new ReportFragment());

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mReceiptDialogFragment = new CreateReceiptDialogFragment();

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                return true;

            case R.id.addItem:
                mReceiptDialogFragment.show(getFragmentManager(), CreateReceiptDialogFragment.CREATE_RECEIPT_TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setToolbarView(){
        receiptsToolbar = (Toolbar) findViewById(R.id.receiptsToolbar);
        setSupportActionBar(receiptsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.receipts_title));
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 0) super.onBackPressed();
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((ReceiptsFragment)mFragments.get(0)).mAdapter.refreshData();
    }

    @Override
    public void onLongClick(Catalog catalog) {
        //TODO implement deleting receipt
    }
}
