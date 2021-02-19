package com.example.weatherapptest.retrofit.models;

import java.util.List;

public class Daily {
    long dt;
    long sunrise;
    long sunset;
    Temperature temp;
    List<Weather> weather;

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
}
