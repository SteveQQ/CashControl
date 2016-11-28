package com.steveq.cashcontrol.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.utilities.Converter;

public class CreateReceiptDialogFragment extends DialogFragment {


    public static final String CREATE_RECEIPT_TAG = "CREATE_RECEIPT_TAG";

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder
//                .setView(inflater.inflate(R.layout.add_receipt_dialog, null))
//                .setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        CatalogsDataSource
//                                .getInstance()
//                                .createCatalog(new Catalog(-1,
//                                        UserManager.mCurrentUser.getId(),
//                                        0,
//                                        name.getText().toString(),
//                                        mConverter.stringToTimestamp(startDate.getText().toString()),
//                                        mConverter.stringToTimestamp(endDate.getText().toString()),
//                                        currencySpinner.getSelectedItem().toString()));
//                    }
//                })
//                .setNegativeButton(R.string.back_button, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        CreateCatalogDialogFragment.this.getDialog().cancel();
//                    }
//                });
//
//
//        return builder.create();
//    }

}
