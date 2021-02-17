package com.example.weatherapptest.retrofit.models;

import java.util.List;

public class CurrentWeather {
    List<Weather> weather;
    MainWeatherInfo main;
    Wind wind;
    SystemParams sys;
    String name; // City name

    public List<Weather> getWeather() {
        return weather;
    }

    public MainWeatherInfo getMainWeatherInfo() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public SystemParams getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }
}
