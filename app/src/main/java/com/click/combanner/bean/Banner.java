package com.click.combanner.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Banner implements Parcelable {

    private String picUrl;
    private String picTitle;
    private int picPosition;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public void setPicTitle(String picTitle) {
        this.picTitle = picTitle;
    }

    public int getPicPosition() {
        return picPosition;
    }

    public void setPicPosition(int picPosition) {
        this.picPosition = picPosition;
    }

    public Banner(String picUrl, String picTitle, int picPosition) {
        this.picUrl = picUrl;
        this.picTitle = picTitle;
        this.picPosition = picPosition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picUrl);
        dest.writeString(this.picTitle);
        dest.writeInt(this.picPosition);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.picUrl = in.readString();
        this.picTitle = in.readString();
        this.picPosition = in.readInt();
    }

    public static final Parcelable.Creator<Banner> CREATOR = new Parcelable.Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
