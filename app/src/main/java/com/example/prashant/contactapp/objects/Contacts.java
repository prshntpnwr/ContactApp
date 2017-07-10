package com.example.prashant.contactapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Contacts implements Parcelable {

    public static final Creator CREATOR = new Creator() {
        public Contacts createFromParcel(Parcel in) {
            return new Contacts(in);
        }

        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };

    private String name, number, id;

    public Contacts(String name, String number, String id) {
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public Contacts(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.name = data[0];
        this.number = data[1];
        this.id = data[2];

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name,
                this.number,
                this.id
        });
    }
}