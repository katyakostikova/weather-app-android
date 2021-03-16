package com.example.weatherapptest.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Hourly implements Parcelable {
    long dt; //date
    float temp;
    int humidity;
    float wind_speed;
    int wind_deg;
    List<Weather> weather; // weather info (clear, rain...)

    protected Hourly(Parcel in) {
        dt = in.readLong();
        temp = in.readFloat();
        humidity = in.readInt();
        wind_speed = in.readFloat();
        wind_deg = in.readInt();
        weather = in.createTypedArrayList(Weather.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dt);
        dest.writeFloat(temp);
        dest.writeInt(humidity);
        dest.writeFloat(wind_speed);
        dest.writeInt(wind_deg);
        dest.writeTypedList(weather);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel in) {
            return new Hourly(in);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };

    public List<Weather> getWeather() {
        return weather;
    }

    public long getDate() {
        return dt;
    }

    public float getTemp() {
        return temp- 273;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return wind_speed;
    }

    public int getWindDeg() {
        return wind_deg;
    }
}
