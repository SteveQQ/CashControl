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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.CustomPagerAdapter;
import com.steveq.cashcontrol.controller.QueriesController;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.commands.CommadSelectAll;
import com.steveq.cashcontrol.database.commands.CommandSelectBiggestPrice;
import com.steveq.cashcontrol.interfaces.ItemOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.fragments.dialogs.CreateReceiptDialogFragment;
import com.steveq.cashcontrol.ui.fragments.QueriesFragment;
import com.steveq.cashcontrol.ui.fragments.ReceiptsFragment;
import com.steveq.cashcontrol.ui.fragments.ReportFragment;
import com.steveq.cashcontrol.ui.fragments.dialogs.SimpleAlertDialogFragment;

import java.util.ArrayList;

public class ReceiptsActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private Toolbar receiptsToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mPagerAdapter;
    public static final String FRAGMENT_NAME = "fragment_name";
    private CreateReceiptDialogFragment mReceiptDialogFragment;
    public QueriesController mQueriesController;
    public CommadSelectAll mCommadSelectAll;
    public CommandSelectBiggestPrice mCommandSelectBiggestPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        mQueriesController = new QueriesController();

        Intent intent = getIntent();
        int id = intent.getIntExtra(CatalogsActivity.CATALOG_ID, -1);

        setToolbarView();
        createQueries();
        setPagerView();

    }
    private void createQueries() {
        mCommadSelectAll = new CommadSelectAll();
        mCommandSelectBiggestPrice = new CommandSelectBiggestPrice();
    }

    private void setPagerView() {
        mFragments = getFragments();

        mQueriesController.setQueryCommands(mCommadSelectAll);
        mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager = (ViewPager) findViewById(R.id.receiptsPager);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.receiptsTab);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ReceiptsActivity.this, Integer.toString(position), Toast.LENGTH_LONG).show();
                if(position == 0) {
                    ((ReceiptsFragment) mFragments.get(position)).mAdapter.refreshData(mQueriesController.commandExecute());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        getSupportActionBar().setTitle(String.format("%s  -  %.2f %s",
                                                        getResources().getString(R.string.receipts_title),
                                                        CatalogsActivity.currentCatalog.getPrice(),
                                                        CatalogsActivity.currentCatalog.getCurrency()));
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 0) super.onBackPressed();
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((ReceiptsFragment)mFragments.get(0)).mAdapter.refreshData(mQueriesController.commandExecute());
        Log.d("DEBUG", Double.toString(CatalogsActivity.currentCatalog.getPrice()));
        setToolbarView();
    }



}
