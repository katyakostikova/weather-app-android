package com.example.weatherapptest.retrofit.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {
    String main;
    String description;

    protected Weather(Parcel in) {
        main = in.readString();
        description = in.readString();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(main);
        dest.writeString(description);
    }
}
