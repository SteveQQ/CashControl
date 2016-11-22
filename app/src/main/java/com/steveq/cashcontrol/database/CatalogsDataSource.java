package com.steveq.cashcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.User;

import java.util.ArrayList;

public class CatalogsDataSource extends DataSource{

    public CatalogsDataSource(Context context) {
        super(context);
    }

    public void createCatalog(Catalog catalog){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues catalogValues = new ContentValues();
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_FK_USER, catalog.getFkId());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_SUM, catalog.getSum());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_NAME, catalog.getName());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_START_DATE, catalog.getStartTime());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_END_DATE, catalog.getEndTime());

        db.insert(
                ReceiptDataBaseHelper.CATALOGS_TABLE,
                null,
                catalogValues
        );

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Catalog> readCatalogs(){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.CATALOGS_TABLE,
                new String[]{BaseColumns._ID,
                        ReceiptDataBaseHelper.COLUMN_CATALOGS_FK_USER,
                        ReceiptDataBaseHelper.COLUMN_CATALOGS_SUM,
                        ReceiptDataBaseHelper.COLUMN_CATALOGS_NAME,
                        ReceiptDataBaseHelper.COLUMN_CATALOGS_START_DATE,
                        ReceiptDataBaseHelper.COLUMN_CATALOGS_END_DATE},
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Catalog> catalogs = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                catalogs.add(new Catalog(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_CATALOGS_FK_USER),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_CATALOGS_SUM),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_CATALOGS_NAME),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_CATALOGS_START_DATE),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_CATALOGS_END_DATE))
                        );
            }while(cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return catalogs;
    }

    public void deleteCatalog(Catalog catalog){

        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(
                ReceiptDataBaseHelper.CATALOGS_TABLE,
                BaseColumns._ID + " = " + catalog.getId(),
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public void updateCatalogSum(Catalog catalog, double sum){


        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues newSum = new ContentValues();
        newSum.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_SUM, sum);

        db.update(
                ReceiptDataBaseHelper.CATALOGS_TABLE,
                newSum,
                BaseColumns._ID + " = ?",
                new String[]{Integer.toString(catalog.getId())}
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

}
