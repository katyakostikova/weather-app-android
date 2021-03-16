package com.example.weatherapptest.retrofit.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather implements Parcelable {

    String dt; //date
    int sunrise;
    int sunset;
    float temp;
    float feels_like;
    int pressure;
    int humidity;
    float wind_speed;
    int wind_deg;
    float uvi;
    List<Weather> weather; // weather info (clear, rain...)

    protected CurrentWeather(Parcel in) {
        dt = in.readString();
        sunrise = in.readInt();
        sunset = in.readInt();
        temp = in.readFloat();
        feels_like = in.readFloat();
        pressure = in.readInt();
        humidity = in.readInt();
        wind_speed = in.readFloat();
        wind_deg = in.readInt();
        uvi = in.readFloat();
        weather = in.createTypedArrayList(Weather.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dt);
        dest.writeInt(sunrise);
        dest.writeInt(sunset);
        dest.writeFloat(temp);
        dest.writeFloat(feels_like);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
        dest.writeFloat(wind_speed);
        dest.writeInt(wind_deg);
        dest.writeFloat(uvi);
        dest.writeTypedList(weather);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>() {
        @Override
        public CurrentWeather createFromParcel(Parcel in) {
            return new CurrentWeather(in);
        }

        @Override
        public CurrentWeather[] newArray(int size) {
            return new CurrentWeather[size];
        }
    };

    public List<Weather> getWeather() {
        return weather;
    }

    public String getDate() {
        return dt;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public float getTemp() {
        return temp- 273;
    }

    public float getFeelsLike() {
        return feels_like- 273;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return (float) (wind_speed * 3.6);
    }

    public int getWindDeg() {
        return wind_deg;
    }

    public float getUvi() {
        return uvi;
    }
}
