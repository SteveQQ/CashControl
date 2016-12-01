package com.steveq.cashcontrol.ui.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;

public class SimpleAlertDialogFragment extends DialogFragment {

    public static final String TAG = SimpleAlertDialogFragment.class.getSimpleName();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Item item = getArguments().getParcelable(CatalogsActivity.CATALOG_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DatePickerStyle);
        builder
                .setMessage("Are you sure deleting?")
                .setPositiveButton("Yeah!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(item instanceof Catalog) {
                            CatalogsDataSource.getInstance().deleteCatalog((Catalog)item);
                        } else if (item instanceof Receipt){
                            ReceiptsDataSource.getInstance().deleteReceipt((Receipt)item);
                        }
                    }
                })
                .setNegativeButton("Nope...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //intentionally empty
                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if(activity instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener)activity).onDismiss(dialog);
        }
    }

}
