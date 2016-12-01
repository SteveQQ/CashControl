package com.steveq.cashcontrol.model;

public class Item {

    protected int mId;
    protected int mFk;
    protected String mName;
    protected double mPrice;

    public Item(){}

    public int getId() {
        return mId;
    }

    public int getFk() {
        return mFk;
    }

    public String getName() {
        return mName;
    }

    public double getPrice() {
        return mPrice;
    }
}
