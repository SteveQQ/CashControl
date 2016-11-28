package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;

public class ReceiptsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public ReceiptsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(ReceiptsActivity.FRAGMENT_NAME, "Receipts");
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipts, container, false);
        view.setTag("Receipts");
        return view;
    }
}
