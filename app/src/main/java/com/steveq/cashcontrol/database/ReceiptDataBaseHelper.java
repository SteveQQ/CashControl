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
    //CATALOGS TABLE ATTRIBUTES
    public static final String CATALOGS_TABLE = "CATALOGS";
    public static final String COLUMN_CATALOGS_FK_USER= "FK_USER";
    public static final String COLUMN_CATALOGS_SUM = "SUM";
    public static final String COLUMN_CATALOGS_NAME = "NAME";
    public static final String COLUMN_CATALOGS_START_DATE = "START_DATE";
    public static final String COLUMN_CATALOGS_END_DATE = "END_DATE";

    //DDL:
    //CREATE USERS TABLE
    private static String CREATE_USERS_TABLE =
            "CREATE TABLE " +
                    USERS_TABLE +
                    " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERS_USERNAME + " TEXT UNIQUE, " +
                    COLUMN_USERS_PASSWORD + " TEXT)";
    //CREATE CATALOGS TABLE
    private static String CREATE_CATALOGS_TABLE =
            "CREATE TABLE " + CATALOGS_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATALOGS_FK_USER + " INTEGER, "+
                    COLUMN_CATALOGS_SUM + " REAL, "+
                    COLUMN_CATALOGS_NAME + " TEXT, " +
                    COLUMN_CATALOGS_START_DATE + " INTEGER, " +
                    COLUMN_CATALOGS_END_DATE + " INTEGER, " +
                    "FOREIGN KEY (" + COLUMN_CATALOGS_FK_USER + ") REFERENCES USERS(_id)) ";




    public ReceiptDataBaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATALOGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
