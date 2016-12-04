package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.utilities.DismissKeybord;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueriesFragment extends Fragment {

    private RadioGroup mainRadioGroup;
    private Spinner categoryInputSpinner;
    public EditText nameEditText;

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
        view.setOnClickListener(new DismissKeybord(getActivity()));

        setSpinnerView(view);
        setEditView(view);

        mainRadioGroup = (RadioGroup) view.findViewById(R.id.mainRadioGroup);
        mainRadioGroup.check(R.id.selectAllRadio);
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = mainRadioGroup.getCheckedRadioButtonId();
                ReceiptsActivity activity = (ReceiptsActivity)getActivity();
                switch(id){

                    case R.id.selectAllRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSelectAll);
                        break;

                    case R.id.selectBiggestPriceRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSelectBiggestPrice);
                        break;

                    case R.id.sortByPriceRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSortByPrice);
                        break;

                    case R.id.sortByNameRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSortByName);
                        break;

                    case R.id.sortCategoryRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSortByCategory);
                        break;

                    case R.id.selectCategoryRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSelectCategory);
                        break;

                    case R.id.selectNameRadio:
                        activity.mQueriesController.setQueryCommands(activity.mCommandSelectName);
                }
            }
        });

        return view;
    }

    private void setEditView(View view) {
        nameEditText = (EditText) view.findViewById(R.id.nameInputEditText);
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((ReceiptsActivity)getActivity()).mCommandSelectName.setInput(nameEditText.getText().toString().trim());
            }
        });
    }

    protected void setSpinnerView(View view) {
        categoryInputSpinner = (Spinner) view.findViewById(R.id.categoryInputSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryInputSpinner.setAdapter(adapter);
        categoryInputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((ReceiptsActivity)getActivity()).mCommandSelectCategory.setInput(categoryInputSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
