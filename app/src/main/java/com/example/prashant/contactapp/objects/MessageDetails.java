package com.example.prashant.contactapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageDetails implements Parcelable {

    public static final Creator CREATOR = new Creator() {
        public MessageDetails createFromParcel(Parcel in) {
            return new MessageDetails(in);
        }

        public MessageDetails[] newArray(int size) {
            return new MessageDetails[size];
        }
    };

    private String name, number, opt, date, time;
    private int id;

    public MessageDetails(int id, String name, String number, String otp, String date, String time) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.opt = otp;
        this.date = date;
        this.time = time;
    }

    public MessageDetails(Parcel in) {
        String[] data = new String[6];

        in.readStringArray(data);
        this.id = Integer.valueOf(data[0]);
        this.name = data[1];
        this.number = data[2];
        this.opt = data[3];
        this.date = data[4];
        this.time = data[5];

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.id),
                this.name,
                this.number,
                this.opt,
                this.date,
                this.time

        });
    }
}