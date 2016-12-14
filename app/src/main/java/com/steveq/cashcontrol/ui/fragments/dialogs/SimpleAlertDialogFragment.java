package com.steveq.cashcontrol.ui.fragments.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.AlertListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.ui.fragments.ReceiptsFragment;

import java.util.ArrayList;
import java.util.List;

public class SimpleAlertDialogFragment extends DialogFragment {

    public static final String TAG = SimpleAlertDialogFragment.class.getSimpleName();
    public static final String ITEM_KEY = "ITEM_KEY";
    private String mMessage = "no message";

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DatePickerStyle);
        builder
                .setMessage(mMessage)
                .setPositiveButton("Yeah!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Activity owner = getDialog().getOwnerActivity();
                        if(owner instanceof CatalogsActivity &&
                                owner instanceof AlertListener){
                            ((AlertListener)owner).reactOnAlert();
                        } else if (owner instanceof ReceiptsActivity){
                            List<Fragment> fragments = ((ReceiptsActivity)getActivity()).getFragments();
                            ViewPager vPager = ((ReceiptsActivity)getActivity()).getViewPager();
                            Fragment visibleFragment = fragments.get(vPager.getCurrentItem());
                            if(visibleFragment instanceof AlertListener){
                                ((AlertListener) visibleFragment).reactOnAlert();
                            }

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
