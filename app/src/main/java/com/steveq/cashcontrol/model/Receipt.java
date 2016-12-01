package com.steveq.cashcontrol.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Receipt extends Item implements Parcelable{

    private long mDate;
    private String mCategory;

    public Receipt(int id, int fk, String name, double price, long date, String category) {
        mId = id;
        mFk = fk;
        mPrice = price;
        mName = name;
        mDate = date;
        mCategory = category;
    }

    protected Receipt(Parcel in) {
        mId = in.readInt();
        mFk = in.readInt();
        mPrice = in.readDouble();
        mName = in.readString();
        mDate = in.readLong();
        mCategory = in.readString();
    }


    public long getDate() {
        return mDate;
    }

    public String getCategory() {
        return mCategory;
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
        dest.writeLong(mDate);
        dest.writeString(mCategory);
    }


    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };
}
