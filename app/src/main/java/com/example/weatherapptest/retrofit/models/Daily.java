package com.example.weatherapptest.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Daily implements Parcelable {
    long dt; // date
    long sunrise;
    long sunset;
    Temperature temp;
    List<Weather> weather;

    protected Daily(Parcel in) {
        dt = in.readLong();
        sunrise = in.readLong();
        sunset = in.readLong();
        weather = in.createTypedArrayList(Weather.CREATOR);
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public long getDt() {
        return dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public Temperature getTemp() {
        return temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dt);
        dest.writeLong(sunrise);
        dest.writeLong(sunset);
        dest.writeTypedList(weather);
    }
}
