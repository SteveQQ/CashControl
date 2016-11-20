package com.steveq.cashcontrol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ReceiptDataBaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "receipts.db";
    private static final int DB_VERSION = 1;

    //ATTRIBUTES:
    //USERS TABLE ATTRIBUTES
    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_USERS_USERNAME = "USERNAME";
    public static final String COLUMN_USERS_PASSWORD = "PASSWORD";

    //DDL:
    //CREATE USERS TABLE
    private static String CREATE_USERS_TABLE =
            "CREATE TABLE " +
                    USERS_TABLE +
                    " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERS_USERNAME + " TEXT UNIQUE, " +
                    COLUMN_USERS_PASSWORD + " TEXT)";


    public ReceiptDataBaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
