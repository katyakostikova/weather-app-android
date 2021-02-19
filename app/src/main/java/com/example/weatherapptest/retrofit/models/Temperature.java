package com.example.weatherapptest.retrofit.models;

public class Temperature {
    float day;
    float night;

    public float getDay() {
        return day - 273;
    }

    public float getNight() {
        return night - 273;
    }
}
