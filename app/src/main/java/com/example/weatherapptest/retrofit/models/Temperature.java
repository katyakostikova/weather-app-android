package com.example.weatherapptest.retrofit.models;

public class Temperature {
    float day;
    float night;

    public float getDay(boolean isCelsius) {
        if(isCelsius) {
            return day - 273;
        }
        return ((day - 273)*9/5+32);
    }

    public float getNight(boolean isCelsius) {
        if(isCelsius) {
            return night - 273;
        }
        return ((night - 273)*9/5+32);
    }
}
