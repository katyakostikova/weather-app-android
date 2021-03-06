package com.example.weatherapptest.retrofit.models;

import java.util.List;

public class Hourly {
    String dt; //date
    float temp;
    int humidity;
    float wind_speed;
    int wind_deg;
    List<Weather> weather; // weather info (clear, rain...)

    public List<Weather> getWeather() {
        return weather;
    }

    public String getDate() {
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
