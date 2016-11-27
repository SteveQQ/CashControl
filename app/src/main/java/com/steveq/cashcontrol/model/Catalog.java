package com.steveq.cashcontrol.model;

public class Catalog {

    private int mId;
    private int fkId;
    private String mName;
    private double mSum;
    private long mStartTime;
    private long mEndTime;
    private String mCurrency;

    public Catalog(int mId, int fk, double sum, String name, long startTime, long endTime, String currency) {
        this.mId = mId;
        fkId = fk;
        mSum = sum;
        mName = name;
        mStartTime = startTime;
        mEndTime = endTime;
        mCurrency = currency;
    }

    public int getId() {
        return mId;
    }

    public int getFkId() {
        return fkId;
    }

    public double getSum() {
        return mSum;
    }

    public String getName() {
        return mName;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public String getCurrency() {
        return mCurrency;
    }
}
