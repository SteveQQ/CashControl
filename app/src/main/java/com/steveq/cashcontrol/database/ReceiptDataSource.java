package com.steveq.cashcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.steveq.cashcontrol.model.User;

public class ReceiptDataSource {

    private Context mContext;
    private ReceiptDataBaseHelper mReceiptDataBaseHelper;

    public ReceiptDataSource(Context context) {
        mContext = context;
        mReceiptDataBaseHelper = new ReceiptDataBaseHelper(context);
    }

    private SQLiteDatabase open(){
        return mReceiptDataBaseHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase db){
        db.close();
    }

    public void createUser(User user){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues userValues = new ContentValues();
        userValues.put(ReceiptDataBaseHelper.COLUMN_USERS_USERNAME, user.getUsername());
        userValues.put(ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD, user.getPassword());

        db.insert(ReceiptDataBaseHelper.USERS_TABLE, null, userValues);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);

    }

    public User readPassword(String username){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.USERS_TABLE,
                new String[]{ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD},
                ReceiptDataBaseHelper.COLUMN_USERS_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
            return new User(getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_USERS_USERNAME),
                            getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD));
        } else {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
            return null;
        }


    }

    public void updateUsername(String username){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues updateUsernameValue = new ContentValues();
        updateUsernameValue.put(ReceiptDataBaseHelper.COLUMN_USERS_USERNAME, username);
        db.update(
                ReceiptDataBaseHelper.USERS_TABLE,
                updateUsernameValue,
                ReceiptDataBaseHelper.COLUMN_USERS_USERNAME + "=" + username,
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    public void updatePassword(String username, String password){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues updatePasswordValue = new ContentValues();
        updatePasswordValue.put(ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD, password);
        db.update(
                ReceiptDataBaseHelper.USERS_TABLE,
                updatePasswordValue,
                ReceiptDataBaseHelper.COLUMN_USERS_USERNAME + " = " + username,
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteUser(String username){

        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(
                ReceiptDataBaseHelper.USERS_TABLE,
                ReceiptDataBaseHelper.COLUMN_USERS_USERNAME + " = " + username,
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    private String getStringFromColumnName(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }


}
