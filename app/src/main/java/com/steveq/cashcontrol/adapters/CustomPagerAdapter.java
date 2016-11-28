package com.steveq.cashcontrol.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.ui.fragments.ReceiptsFragment;

import java.util.ArrayList;

public class CustomPagerAdapter extends FragmentPagerAdapter{

    ArrayList<Fragment> mData;

    public CustomPagerAdapter(FragmentManager fm, ArrayList<Fragment> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getArguments().getString(ReceiptsActivity.FRAGMENT_NAME);
    }

}
