package com.example.weatherapptest.retrofit.models;

public class MainWeatherInfo {
    float temp;
    float feels_like;
    int pressure;
    int humidity;
    float temp_min;
    float temp_max;

    public float getTemp() {
        return temp - 273;
    }

    public float getFeels_like() {
        return feels_like - 273;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTemp_min() {
        return temp_min - 273;
    }

    public float getTemp_max() {
        return temp_max - 273;
    }
}
