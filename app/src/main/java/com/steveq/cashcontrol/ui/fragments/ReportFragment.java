package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    public ReportFragment() {
        // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putString(ReceiptsActivity.FRAGMENT_NAME, "Report");
        this.setArguments(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        view.setTag("Report");
        return view;
    }

}
