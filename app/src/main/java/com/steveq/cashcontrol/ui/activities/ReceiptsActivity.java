package com.steveq.cashcontrol.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.CustomPagerAdapter;
import com.steveq.cashcontrol.ui.fragments.QueriesFragment;
import com.steveq.cashcontrol.ui.fragments.ReceiptsFragment;
import com.steveq.cashcontrol.ui.fragments.ReportFragment;

import java.util.ArrayList;

public class ReceiptsActivity extends AppCompatActivity {

    private Toolbar receiptsToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;
    private FragmentPagerAdapter mPagerAdapter;
    public static final String FRAGMENT_NAME = "fragment_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);
        setToolbarView();

        Intent intent = getIntent();
        int id = intent.getIntExtra(CatalogsActivity.CATALOG_ID, -1);

        setPagerView();

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

    private void setToolbarView(){
        receiptsToolbar = (Toolbar) findViewById(R.id.receiptsToolbar);
        setSupportActionBar(receiptsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
