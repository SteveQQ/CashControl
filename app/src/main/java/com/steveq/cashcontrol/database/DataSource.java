package com.steveq.cashcontrol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

    private Context mContext;
    private ReceiptDataBaseHelper mReceiptDataBaseHelper;

    public DataSource(Context context) {
        mContext = context;
        mReceiptDataBaseHelper = new ReceiptDataBaseHelper(context);
    }

    protected SQLiteDatabase open(){
        return mReceiptDataBaseHelper.getWritableDatabase();
    }

    protected void close(SQLiteDatabase db){
        db.close();
    }

}
