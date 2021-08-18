package com.example.weatherapptest.retrofit;


import com.example.weatherapptest.retrofit.models.WeatherForecast;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherApi {

    @GET("/data/2.5/onecall")
    Call<WeatherForecast> weatherForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("exclude") String exclude, @Query("appid") String appid);
}
