package com.steveq.cashcontrol.ui.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.Calendar;

public class CreateCatalogDialogFragment extends DialogFragment{

    int curYear;
    int curMonth;
    int curDay;
    private EditText name;
    private EditText startDate;
    private EditText endDate;
    private Spinner currencySpinner;
    private Converter mConverter;
    public static final String CREATE_CATALOG_TAG = "CREATE_CATALOG_TAG";

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;

                    if(wantToCloseDialog){
                        d.dismiss();
                    }
                }
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getCurDate();
        name = (EditText) getDialog().findViewById(R.id.catalogNameEditText);
        startDate = (EditText) getDialog().findViewById(R.id.startDateEditText);
        endDate = (EditText) getDialog().findViewById(R.id.endDateEditText);

        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    new DatePickerDialog(getActivity(),
                            R.style.DatePickerStyle,
                            new setDateListener(startDate),
                            curYear,
                            curMonth,
                            curDay).show();
                }
            }
        });

        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(getActivity(),
                            R.style.DatePickerStyle,
                            new setDateListener(endDate),
                            curYear,
                            curMonth,
                            curDay).show();
                }
            }
        });

        setSpinnerView();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mConverter = new Converter();

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder
//                .setView(inflater.inflate(R.layout.add_catalog_dialog, null))
//                .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        if(allFieldsFilled()) {
//                            CatalogsDataSource
//                                    .getInstance()
//                                    .createCatalog(new Catalog(-1,
//                                            UserManager.mCurrentUser.getId(),
//                                            0,
//                                            name.getText().toString(),
//                                            mConverter.stringToTimestamp(startDate.getText().toString()),
//                                            mConverter.stringToTimestamp(endDate.getText().toString()),
//                                            currencySpinner.getSelectedItem().toString()));
//                        } else {
//                            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                })
//                .setNegativeButton(R.string.back_button, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        CreateCatalogDialogFragment.this.getDialog().cancel();
//                    }
//                });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(inflater.inflate(R.layout.add_catalog_dialog, null))
                .setPositiveButton(R.string.accept_button, null)
                .setNegativeButton(R.string.back_button, null);

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button positiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(allFieldsFilled()) {
                            CatalogsDataSource
                                    .getInstance()
                                    .createCatalog(new Catalog(-1,
                                            UserManager.mCurrentUser.getId(),
                                            0,
                                            name.getText().toString(),
                                            mConverter.stringToTimestamp(startDate.getText().toString()),
                                            mConverter.stringToTimestamp(endDate.getText().toString()),
                                            currencySpinner.getSelectedItem().toString()));
                            mAlertDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Button negativeButton = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });

            }
        });

        return mAlertDialog;
    }

    private boolean allFieldsFilled() {
        return (!name.getText().toString().equals("")) &&
                (!startDate.getText().toString().equals("")) &&
                (!endDate.getText().toString().equals(""));
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener)activity).onDismiss(dialog);
        }
    }

    private void setSpinnerView() {
        currencySpinner = (Spinner) getDialog().findViewById(R.id.currencySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                                                                R.array.currency_array,
                                                                                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }

    private void getCurDate(){
        Calendar calendar = Calendar.getInstance();
        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        curDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    protected class setDateListener implements DatePickerDialog.OnDateSetListener{

        private EditText field;
        public setDateListener(EditText field){
            this.field = field;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            int monthOffset = month + 1;
            if (year >= curYear) {
                field.setText(String.format("%02d/%02d/%d", dayOfMonth, monthOffset, year));
            } else {
                field.setText(String.format("%02d/%02d/%d", curDay, curMonth, curYear));
            }
            field.clearFocus();
        }
    }

}
