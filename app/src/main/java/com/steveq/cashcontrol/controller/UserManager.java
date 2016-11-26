package com.steveq.cashcontrol.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.steveq.cashcontrol.CashControlApplication;
import com.steveq.cashcontrol.database.UsersDataSource;
import com.steveq.cashcontrol.model.User;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.LoggingActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private final Context mContext;
    public static User mCurrentUser;
    private UsersDataSource mUsersDataSource;
    private static UserManager instance;

    public static final String CREATE_USER = "CREATE_USER";

    private UserManager(Context context){
        mContext = context;
        mUsersDataSource = new UsersDataSource(context);
    }

    public static UserManager getInstance() {
        if(instance == null){
            instance = new UserManager(CashControlApplication.getContext());
        }
        return instance;
    }

    //******USERS SERVICES*****//
    public void changePassword(String oldPassword, String newPassword){
        if(validate(oldPassword) && validate(newPassword)){
            if(mCurrentUser.getPassword().equals(oldPassword)) {
                if (!newPassword.equals(oldPassword)) {
                    mUsersDataSource.updatePassword(mCurrentUser, newPassword);
                    Toast.makeText(mContext, "Password Changed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Password duplicate", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "Only alpha-num values allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean changeUsername(String newUsername, String password){
        if(credentialValidation(newUsername, password)){
            if(mCurrentUser.getPassword().equals(password)) {
                mUsersDataSource.updateUsername(mCurrentUser, newUsername);
                Toast.makeText(mContext, "Username changed", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(mContext, "Insert correct password", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(mContext, "Only alpha-num values allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean createNewUser(final String username, final String password){
        long pk = mUsersDataSource.createUser(new User(-1, username, password));
        if(credentialValidation(username, password)){
            if(pk != -1) {
                Toast.makeText(mContext, "User created", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(mContext, "User already exists", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean logIn(Context context, String username, String password){
        mCurrentUser = mUsersDataSource.readUser(username);
        if(credentialValidation(username, password) && mCurrentUser != null){
            if(mCurrentUser.getPassword().equals(password)){
                Intent intent = new Intent(context, CatalogsActivity.class);
                context.startActivity(intent);
                Toast.makeText(mContext, "Hello!", Toast.LENGTH_SHORT).show();
                return true;
            } else{
                Toast.makeText(mContext, "Wrong Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(mContext, "No such user", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean logOut(Context context){
        mCurrentUser = null;
        Intent intent = new Intent(context, LoggingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        return true;
    }

    public boolean removeUser(String password){
        if(validate(password)){
            if(mCurrentUser.getPassword().equals(password)){
                mUsersDataSource.deleteUser(mCurrentUser);
                Toast.makeText(mContext, "User Removed", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(mContext, "Wrong Password", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(mContext, "Only alpha-num values allowed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //******USERS SERVICES*****//

    //******HELPER METHODS*****//

    private boolean validate(String input){
        Pattern pattern = Pattern.compile("[a-zA-Z\\d]*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    private boolean credentialValidation(String username, String password){
        if(!validate(username) || username.equals("")) {
            Toast.makeText(mContext, "Insert valid User Name", Toast.LENGTH_SHORT).show();
        } else if(!validate(password) || password.equals("")){
            Toast.makeText(mContext, "Insert valid Password", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }
    //******HELPER METHODS*****//
}
