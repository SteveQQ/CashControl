package com.steveq.cashcontrol.database;

import android.content.Context;

public class ReceiptDataSource {

    private Context mContext;
    private ReceiptDataBaseHelper mReceiptDataBaseHelper;

    public ReceiptDataSource(Context context) {
        mContext = context;
        mReceiptDataBaseHelper = new ReceiptDataBaseHelper(context);
    }
}
