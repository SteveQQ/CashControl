package com.steveq.cashcontrol.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.steveq.cashcontrol.database.ReceiptDataSource;
import com.steveq.cashcontrol.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private static UserManager instance = null;
    private Context mContext;
    private User mCurrentUser;
    private ReceiptDataSource mDataSource;

    public static final String CREATE_USER = "CREATE_USER";


    protected UserManager(Context context){
        mContext = context;
        mDataSource = new ReceiptDataSource(context);
    }

    public User getCurrentUser() {
        if(mCurrentUser != null) {
            return mCurrentUser;
        } else {
            return new User("", "");
        }
    }

    public static UserManager getInstance(Context context){
        if(instance == null){
            instance = new UserManager(context);
        }
        return instance;
    }

    //******USERS SERVICES*****//
//    public void changePassword(Context ctx, String oldPassword, String newPassword){
//        if(     validate(oldPassword)              &&
//                validate(newPassword)           &&
//                passwordMatches(mCurrentUser, oldPassword)){
//            if(!newPassword.equals(oldPassword)) {
//                mCurrentUser.setPassword(newPassword);
//                Toast.makeText(ctx, "Password Changed", Toast.LENGTH_LONG).show();
//                logOut(ctx);
//            } else {
//                Toast.makeText(ctx, "Password duplicate", Toast.LENGTH_LONG).show();
//            }
//        } else {
//            Toast.makeText(ctx, "Insert valid credentials", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    public boolean changeUsername(Context ctx, String newUsername, String password){
//        if(credentialValidation(newUsername, password)){
//            if(passwordMatches(loadUser(mCurrentUser.getUserName()), password)) {
//                User user = loadUser(mCurrentUser.getUserName());
//                user.setUserName(newUsername);
//                saveUser(newUsername, user);
//                registerUser(newUsername);
//                deleteUser(mCurrentUser.getUserName());
//                logOut(ctx);
//                return true;
//            } else {
//                Toast.makeText(ctx, "Insert correct password", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        }else {
//            Toast.makeText(ctx, "Insert valid credentials", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }

    public boolean createNewUser(Context ctx, final String username, final String password){
        if(credentialValidation(username, password)){
            mDataSource.createUser(new User(username, password));
            //saveUser(username, new User(username, password));
            //registerUser(username);
            return true;
        } else {
            Toast.makeText(ctx, "User already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

//    public boolean logIn(Context ctx, String username, String password){
//        if(credentialValidation(username, password) && mUserNames.contains(username)){
//            if(passwordMatches(loadUser(username), password)){
//                mCurrentUser = loadUser(username);
//                Intent intent = new Intent(mContext, MainActivity.class);
//                mContext.startActivity(intent);
//                Toast.makeText(ctx, "Hello!", Toast.LENGTH_SHORT).show();
//                return true;
//            } else {
//                Toast.makeText(ctx, "Wrong Password", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        } else {
//            Toast.makeText(ctx, "No such user", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }
//
//    public boolean logOut(Context ctx){
//        Intent intent = new Intent(ctx, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        ctx.startActivity(intent);
//        return true;
//    }
//
//    public boolean removeUser(Context ctx, String password){
//        if(credentialValidation(mCurrentUser.getUserName(), password) && mUserNames.contains(mCurrentUser.getUserName())){
//            if(passwordMatches(loadUser(mCurrentUser.getUserName()), password)){
//                deleteUser(mCurrentUser.getUserName());
//                Toast.makeText(ctx, "User Removed", Toast.LENGTH_LONG).show();
//                logOut(ctx);
//                return true;
//            } else {
//                Toast.makeText(ctx, "Wrong Password", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        } else {
//            Toast.makeText(ctx, "Wrong Credentials", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }

    //******USERS SERVICES*****//

    //******HELPER METHODS*****//
    private boolean passwordMatches(User user, String password){
        return user.getPassword().equals(password);
    }


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

//    private boolean deleteUser(String username){
//        mEditor.remove(username);
//        mUserNames.remove(username);
//        mEditor.putStringSet(KEY_USERS_SET, mUserNames);
//        mEditor.commit();
//        return true;
//    }
    //******HELPER METHODS*****//
}
