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
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.Calendar;

public class CreateReceiptDialogFragment extends FormDialogFragment {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText dateEditText;
    private Spinner categorySpinner;
    public static final String CREATE_RECEIPT_TAG = "CREATE_RECEIPT_TAG";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(inflater.inflate(R.layout.add_receipt_dialog, null))
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
                            ReceiptsDataSource
                                    .getInstance()
                                    .createReceipt(new Receipt(-1,
                                            CatalogsActivity.currentCatalog.getId(),
                                            nameEditText.getText().toString(),
                                            Double.parseDouble(priceEditText.getText().toString()),
                                            mConverter.stringToTimestamp(dateEditText.getText().toString()),
                                            categorySpinner.getSelectedItem().toString())
                                    );
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

    protected void setSpinnerView() {
        categorySpinner = (Spinner) getDialog().findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    @Override
    protected boolean allFieldsFilled() {
        return !nameEditText.getText().toString().equals("") &&
                !priceEditText.getText().toString().equals("") &&
                !dateEditText.getText().toString().equals("");
    }
}
