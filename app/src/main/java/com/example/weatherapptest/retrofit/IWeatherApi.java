package com.example.weatherapptest.retrofit;

import com.example.weatherapptest.retrofit.models.CurrentWeather;
import com.example.weatherapptest.retrofit.models.WeatherForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherApi {

    String apiKey = "03469fb74d63125e36c7377337c6789d";

    @GET("/data/2.5/onecall")
    Call<List<WeatherForecast>> currentWeather(@Query("lat") String lat, @Query("lon") String lon, @Query("exclude") String exclude, @Query("appid") String appid);

    // https://api.openweathermap.org/data/2.5/onecall?lat=50.431759&lon=30.517023&exclude=daily&appid=03469fb74d63125e36c7377337c6789d

    @GET("/data/2.5/onecall")
    Call<WeatherForecast> weatherForecast(@Query("lat") String lat, @Query("lon") String lon, @Query("exclude") String exclude, @Query("appid") String appid);
}
