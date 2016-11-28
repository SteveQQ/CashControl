package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.CatalogsAdapter;
import com.steveq.cashcontrol.adapters.ReceiptsAdapter;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.CatalogOnLongClickListener;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;

public class ReceiptsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    public ReceiptsAdapter mAdapter;

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
        createRecyclerView(view);

        return view;
    }


    private void createRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.receiptsRecycler);
        mAdapter = new ReceiptsAdapter((CatalogOnLongClickListener)getActivity(), ReceiptsDataSource.getInstance().readReceipts());

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
    }
}
