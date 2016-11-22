package com.steveq.cashcontrol.model;

public class Catalog {

    private int _mId;
    private String mName;
    private int mStartTime;
    private int mEndTime;

    public Catalog(int _mId, String name, int startTime, int endTime) {
        this._mId = _mId;
        mName = name;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    public int get_mId() {
        return _mId;
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
