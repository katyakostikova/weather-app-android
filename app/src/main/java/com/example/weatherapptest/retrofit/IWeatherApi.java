package com.example.weatherapptest.retrofit;

import com.example.weatherapptest.retrofit.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherApi {
    @GET("/data/2.5/weather")
    Call<CurrentWeather> currentWeather(@Query("q") String cityName, @Query("appid") String apiKey);
}
