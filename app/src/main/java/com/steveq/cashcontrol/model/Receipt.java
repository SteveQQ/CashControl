package com.steveq.cashcontrol.model;

public class Receipt {

    private int mId;
    private int mFk;
    private String mName;
    private double mPrice;
    private long mDate;
    private String mCategory;

    public Receipt(int id, int fk, String name, double price, long date, String category) {
        mId = id;
        mFk = fk;
        mName = name;
        mPrice = price;
        mDate = date;
        mCategory = category;
    }

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

    public long getDate() {
        return mDate;
    }

    public String getCategory() {
        return mCategory;
    }
}
