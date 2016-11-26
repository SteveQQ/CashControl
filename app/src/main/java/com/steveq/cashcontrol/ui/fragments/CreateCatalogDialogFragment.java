package com.steveq.cashcontrol.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.steveq.cashcontrol.R;

import java.util.Calendar;

public class CreateCatalogDialogFragment extends DialogFragment {

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
                        //TODO passing data back to activity
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

        final EditText startDate = (EditText) getDialog().findViewById(R.id.startDateEditText);
        EditText endDate = (EditText) getDialog().findViewById(R.id.endDateEditText);

        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    final Calendar calendar = Calendar.getInstance();
                    final int curYear = calendar.get(Calendar.YEAR);
                    final int curMonth = calendar.get(Calendar.MONTH);
                    final int curDay = calendar.get(Calendar.DAY_OF_MONTH);

                    new DatePickerDialog(getActivity(),
                            R.style.DatePickerStyle,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    Toast.makeText(getActivity(), Integer.toString(dayOfMonth), Toast.LENGTH_LONG).show();
                                    if(year >= curYear &&
                                            month >= curMonth &&
                                            dayOfMonth >= curDay) {

                                        startDate.setText(String.format("%d/%d/%d", dayOfMonth, month, year));
                                    } else {
                                        startDate.setText(String.format("%d/%d/%d", curDay, curMonth, curYear));
                                    }
                                    startDate.clearFocus();
                                }
                            },
                            curYear,
                            curMonth,
                            curDay).show();
                }
            }
        });

        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    new DatePickerDialog(getActivity(),
                            R.style.DatePickerStyle,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    Toast.makeText(getActivity(), Integer.toString(dayOfMonth), Toast.LENGTH_LONG).show();
                                }
                            },
                            year,
                            month,
                            day).show();
                }
            }
        });
    }
}
