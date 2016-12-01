package com.steveq.cashcontrol.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Catalog extends Item implements Parcelable{

    private long mStartTime;
    private long mEndTime;
    private String mCurrency;

    public Catalog(int id, int fk, double price, String name, long startTime, long endTime, String currency) {
        mId = id;
        mFk = fk;
        mPrice = price;
        mName = name;
        mStartTime = startTime;
        mEndTime = endTime;
        mCurrency = currency;
    }

    protected Catalog(Parcel in) {
        mId = in.readInt();
        mFk = in.readInt();
        mPrice = in.readDouble();
        mName = in.readString();
        mStartTime = in.readLong();
        mEndTime = in.readLong();
        mCurrency = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mFk);
        dest.writeDouble(mPrice);
        dest.writeString(mName);
        dest.writeLong(mStartTime);
        dest.writeLong(mEndTime);
        dest.writeString(mCurrency);
    }

    public static final Creator<Catalog> CREATOR = new Creator<Catalog>() {
        @Override
        public Catalog createFromParcel(Parcel in) {
            return new Catalog(in);
        }

        @Override
        public Catalog[] newArray(int size) {
            return new Catalog[size];
        }
    };
}
