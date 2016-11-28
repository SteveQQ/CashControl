package com.steveq.cashcontrol.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.Calendar;

public class CreateReceiptDialogFragment extends DialogFragment {


    int curYear;
    int curMonth;
    int curDay;
    private EditText nameEditText;
    private EditText priceEditText;
    private EditText dateEditText;
    private Spinner categorySpinner;
    private Converter mConverter;
    public static final String CREATE_RECEIPT_TAG = "CREATE_RECEIPT_TAG";

    @Override
    public void onResume() {
        super.onResume();

        getCurDate();
        nameEditText = (EditText) getDialog().findViewById(R.id.receiptNameEditText);
        priceEditText = (EditText) getDialog().findViewById(R.id.priceEditText);
        dateEditText = (EditText) getDialog().findViewById(R.id.dateEditText);
        categorySpinner = (Spinner) getDialog().findViewById(R.id.categorySpinner);

        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    new DatePickerDialog(getActivity(),
                            R.style.DatePickerStyle,
                            new setDateListener(dateEditText),
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(inflater.inflate(R.layout.add_receipt_dialog, null))
                .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ReceiptsDataSource
                                .getInstance()
                                .createReceipt(new Receipt(-1,
                                        CatalogsActivity.currentCatalogId,
                                        nameEditText.getText().toString(),
                                        Double.parseDouble(priceEditText.getText().toString()),
                                        mConverter.stringToTimestamp(dateEditText.getText().toString()),
                                        categorySpinner.getSelectedItem().toString())
                                );
                    }
                })
                .setNegativeButton(R.string.back_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateReceiptDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void setSpinnerView() {
        categorySpinner = (Spinner) getDialog().findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
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
