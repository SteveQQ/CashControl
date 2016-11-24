package com.steveq.cashcontrol;

import android.content.Context;

public class CashControlApplication extends android.app.Application {

    private static CashControlApplication instance;

    public CashControlApplication(){
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }

}
