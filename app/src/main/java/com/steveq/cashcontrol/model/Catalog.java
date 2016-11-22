package com.steveq.cashcontrol.model;

public class Catalog {

    private int mId;
    private int fkId;
    private String mName;
    private double mSum;
    private int mStartTime;
    private int mEndTime;

    public Catalog(int mId, int fk, double sum, String name, int startTime, int endTime) {
        this.mId = mId;
        fkId = fk;
        mSum = sum;
        mName = name;
        mStartTime = startTime;
        mEndTime = endTime;
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

    public int getStartTime() {
        return mStartTime;
    }

    public int getEndTime() {
        return mEndTime;
    }
}
