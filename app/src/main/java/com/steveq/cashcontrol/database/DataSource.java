package com.steveq.cashcontrol.database;

import android.content.Context;
import android.database.Cursor;
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

    protected String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    protected int getIntegerFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    protected double getDoubleFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getDouble(columnIndex);
    }

    protected long getLongFromColumnName(Cursor cursor, String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getLong(columnIndex);
    }
}
