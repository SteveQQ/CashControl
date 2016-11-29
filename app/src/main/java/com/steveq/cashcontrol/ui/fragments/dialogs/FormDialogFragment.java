package com.steveq.cashcontrol.ui.fragments.dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.steveq.cashcontrol.utilities.Converter;

import java.util.Calendar;

public abstract class FormDialogFragment extends DialogFragment {

    protected int curYear;
    protected int curMonth;
    protected int curDay;
    protected Converter mConverter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConverter = new Converter();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener)activity).onDismiss(dialog);
        }
    }

    protected void getCurDate(){
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

    protected abstract boolean allFieldsFilled();
}
