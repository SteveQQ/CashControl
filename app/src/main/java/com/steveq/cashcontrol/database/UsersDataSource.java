package com.steveq.cashcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.steveq.cashcontrol.model.User;

public class UsersDataSource extends DataSource{

    public UsersDataSource(Context context) {
        super(context);
    }

    public long createUser(User user){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues userValues = new ContentValues();
        userValues.put(ReceiptDataBaseHelper.COLUMN_USERS_USERNAME, user.getUsername());
        userValues.put(ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD, user.getPassword());

        long index = db.insert(ReceiptDataBaseHelper.USERS_TABLE, null, userValues);

        db.setTransactionSuccessful();
        db.endTransaction();
        close(db);
        return index;
    }

    public User readUser(String username){

        SQLiteDatabase db = open();
        db.beginTransaction();

        Cursor cursor = db.query(
                ReceiptDataBaseHelper.USERS_TABLE,
                new String[]{BaseColumns._ID, ReceiptDataBaseHelper.COLUMN_USERS_USERNAME, ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD},
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
            return new User(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
                            getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_USERS_USERNAME),
                            getStringFromColumnName(cursor, ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD));
        } else {
            db.setTransactionSuccessful();
            db.endTransaction();
            close(db);
            return null;
        }
    }

    public void updateUsername(User user, String newUsername){

        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues updateUsernameValue = new ContentValues();
        updateUsernameValue.put(ReceiptDataBaseHelper.COLUMN_USERS_USERNAME, newUsername);
        db.update(
                ReceiptDataBaseHelper.USERS_TABLE,
                updateUsernameValue,
                BaseColumns._ID + " = " + user.getId(),
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void updatePassword(User user, String password){
        SQLiteDatabase db = open();
        db.beginTransaction();

        ContentValues updatePasswordValue = new ContentValues();
        updatePasswordValue.put(ReceiptDataBaseHelper.COLUMN_USERS_PASSWORD, password);
        db.update(
                ReceiptDataBaseHelper.USERS_TABLE,
                updatePasswordValue,
                BaseColumns._ID + " = " + user.getId(),
                null
        );

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteUser(User user){

        SQLiteDatabase db = open();
        db.beginTransaction();

        db.delete(
                ReceiptDataBaseHelper.USERS_TABLE,
                BaseColumns._ID + " = " + user.getId(),
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
