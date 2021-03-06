package com.steveq.cashcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.steveq.cashcontrol.CashControlApplication;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;

import java.util.ArrayList;

public class ReceiptsDataSource extends DataSource {

    private static ReceiptsDataSource instance;

    private ReceiptsDataSource(Context context) {
        super(context);
    }

    public static ReceiptsDataSource getInstance(){
        if(instance == null){
            return new ReceiptsDataSource(CashControlApplication.getContext());
        } else {
            return instance;
        }
    }

    public void createReceipt(Receipt receipt){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues catalogValues = new ContentValues();
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG, receipt.getFk());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME, receipt.getName());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE, receipt.getPrice());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE, receipt.getDate());
        catalogValues.put(ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY, receipt.getCategory());

        db.insert(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                null,
                catalogValues
        );

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Receipt> readReceipts(int catalogId){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                new String[]{BaseColumns._ID,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY},
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG + " = " + catalogId,
                null,
                null,
                null,
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE + " ASC",
                null
        );

        ArrayList<Receipt> receipts = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            }while(cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;
    }

    public int countReceipts(int catalogId){

        SQLiteDatabase db = open();
        db.beginTransaction();

        int result = 0;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ReceiptDataBaseHelper.RECEIPTS_TABLE
                                    + " WHERE " + ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG
                                    + " = " + catalogId, null);
        if(cursor.moveToFirst()){
            result = cursor.getInt(0);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return result;
    }

    public void deleteReceipt(Receipt receipt){

        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                BaseColumns._ID + " = " + receipt.getId(),
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public double priceSum(){
        SQLiteDatabase db = open();
        db.beginTransaction();

        double result = 0;

        Cursor cursor;
        cursor = db.rawQuery("SELECT SUM(" +
                            ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE + ") FROM " +
                            ReceiptDataBaseHelper.RECEIPTS_TABLE +
                            " WHERE " +
                            ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG + " = " + CatalogsActivity.currentCatalog.getId(), null);

        if(cursor.moveToFirst()){
            result = cursor.getDouble(0);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public ArrayList<Receipt> selectBiggestPrice(){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ArrayList<Receipt> result = new ArrayList<>();
        Cursor cursor1 = db.rawQuery("SELECT " +
                                    "MAX(" + ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE + ")"+
                                    " FROM " + ReceiptDataBaseHelper.RECEIPTS_TABLE +
                                    " WHERE " + ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG +
                                    " = " + CatalogsActivity.currentCatalog.getId(), null);

        Cursor cursor2 = null;
        if(cursor1.moveToFirst()) {
            cursor2 = db.rawQuery("SELECT * FROM " + ReceiptDataBaseHelper.RECEIPTS_TABLE +
                    " WHERE " + ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE +
                    " = " + cursor1.getDouble(0), null);
        }

        if(cursor2.moveToFirst()){
            do {
                result.add(new Receipt(getIntegerFromColumnName(cursor2, BaseColumns._ID),
                        getIntegerFromColumnName(cursor2, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor2, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor2, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor2, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor2, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY)
                ));
            }while(cursor2.moveToNext());
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public ArrayList<Receipt> sortByPrice() {

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                new String[]{BaseColumns._ID,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY},
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG + " = " + CatalogsActivity.currentCatalog.getId(),
                null,
                null,
                null,
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE + " DESC",
                null
        );

        ArrayList<Receipt> receipts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;
    }

    public ArrayList<Receipt> sortByName() {

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                new String[]{BaseColumns._ID,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY},
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG + " = " + CatalogsActivity.currentCatalog.getId(),
                null,
                null,
                null,
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME + " ASC",
                null
        );

        ArrayList<Receipt> receipts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;
    }

    public ArrayList<Receipt> sortByCategory() {

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.RECEIPTS_TABLE,
                new String[]{BaseColumns._ID,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE,
                        ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY},
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG + " = " + CatalogsActivity.currentCatalog.getId(),
                null,
                null,
                null,
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY + " ASC",
                null
        );

        ArrayList<Receipt> receipts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;
    }

    public ArrayList<Receipt> selectCategory(String category){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ReceiptDataBaseHelper.RECEIPTS_TABLE +
                                    " WHERE " + ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG +
                                    " = " + CatalogsActivity.currentCatalog.getId() + " AND " +
                                    ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY + " = " +
                                    "\"" + category + "\"" + " ;", null);


        ArrayList<Receipt> receipts = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            }while(cursor.moveToNext());
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;

    }

    public ArrayList<Receipt> selectName(String name){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ReceiptDataBaseHelper.RECEIPTS_TABLE +
                " WHERE " + ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG +
                " = " + CatalogsActivity.currentCatalog.getId() + " AND " +
                ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME + " = " +
                "\"" + name + "\"" + " ;", null);


        ArrayList<Receipt> receipts = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                receipts.add(new Receipt(getIntegerFromColumnName(cursor, BaseColumns._ID),
                        getIntegerFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_FK_CATALOG),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_NAME),
                        getDoubleFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_PRICE),
                        getLongFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_DATE),
                        getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_RECEIPTS_CATEGORY))
                );
            }while(cursor.moveToNext());
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return receipts;

    }

}
