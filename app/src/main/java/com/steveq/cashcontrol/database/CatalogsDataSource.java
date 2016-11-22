package com.steveq.cashcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.steveq.cashcontrol.controller.UserManager;
import com.steveq.cashcontrol.model.Catalog;

public class CatalogsDataSource extends DataSource{

    public CatalogsDataSource(Context context) {
        super(context);
    }

    public void createCatalog(Catalog catalog){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues catalogValues = new ContentValues();
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_CATALOGS_FK_USER, UserManager.mCurrentUser.getId());
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

}
