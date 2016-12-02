package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueriesFragment extends Fragment {

    RadioGroup mainRadioGroup;

    public QueriesFragment() {
        // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putString(ReceiptsActivity.FRAGMENT_NAME, "Queries");
        this.setArguments(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queries, container, false);

        mainRadioGroup = (RadioGroup) view.findViewById(R.id.mainRadioGroup);
        mainRadioGroup.check(R.id.selectAllRadio);
        mainRadioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = mainRadioGroup.getCheckedRadioButtonId();
                switch(id){

                    case R.id.selectAllRadio:
                        break;

                    case R.id.selectBiggestPriceRadio:
                        break;

                    case R.id.sortByPriceRadio:
                        break;

                    case R.id.sortByNameRadio:
                        break;

                    case R.id.sortCategoryRadio:
                        break;

                    case R.id.selectCategoryRadio:
                        break;
                }

            }
        });

        return view;
    }


}
