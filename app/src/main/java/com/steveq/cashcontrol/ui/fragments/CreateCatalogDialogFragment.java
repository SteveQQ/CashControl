package com.steveq.cashcontrol.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.model.Catalog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateCatalogDialogFragment extends DialogFragment {

    int curYear;
    int curMonth;
    int curDay;
    private EditText name;
    private EditText startDate;
    private EditText endDate;
    public static final String CREATE_CATALOG_TAG = "CREATE_CATALOG_TAG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(inflater.inflate(R.layout.add_catalog_dialog, null))
                .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int startTimeStamp = 0;
                        int endTimeStamp = 0;
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            Date startDateTime = dateFormat.parse(startDate.getText().toString());
                            Date endDateTime = dateFormat.parse(endDate.getText().toString());
                            long startTime = startDateTime.getTime();
                            long endTime = endDateTime.getTime();
                            startTimeStamp = (int)new Timestamp(startTime).getTime();
                            endTimeStamp = (int)new Timestamp(endTime).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        CatalogsDataSource
                                .getInstance()
                                .createCatalog(new Catalog(-1,
                                                            UserManager.mCurrentUser.getId(),
                                                            0,
                                                            name.getText().toString(),
                                                            startTimeStamp,
                                                            endTimeStamp));
                    }
                })
                .setNegativeButton(R.string.back_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateCatalogDialogFragment.this.getDialog().cancel();
                    }
                });


        return builder.create();
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
    }

    protected class setDateListener implements DatePickerDialog.OnDateSetListener{

        private EditText field;

        public setDateListener(EditText field){
            this.field = field;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if ((year >= curYear &&
                    month >= curMonth)) {
                field.setText(String.format("%02d/%02d/%d", dayOfMonth, month, year));
            } else {
                field.setText(String.format("%02d/%02d/%d", curDay, curMonth, curYear));
            }
            field.clearFocus();
        }
    }

    private void getCurDate(){
        Calendar calendar = Calendar.getInstance();
        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        curDay = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
