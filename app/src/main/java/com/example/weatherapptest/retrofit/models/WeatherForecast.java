package com.example.weatherapptest.retrofit.models;

import java.util.List;

public class WeatherForecast {
    List<Daily> daily;
    CurrentWeather current;
    List<Hourly> hourly;
    String timezone;
    double lat;
    double lon;

    public List<Daily> getDaily() {
        return daily;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public String getTimezone() {
        return timezone;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }
}
