package com.example.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.IWeatherApi;
import com.example.weatherapptest.retrofit.models.CurrentWeather;
import com.example.weatherapptest.retrofit.models.WeatherForecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CurrentWeather currentWeather;
    private WeatherForecast weatherForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://api.openweathermap.org/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        IWeatherApi iWeatherApi = retrofit.create(IWeatherApi.class);


        Call<CurrentWeather> callCurrentWeather = iWeatherApi.currentWeather("kyiv", IWeatherApi.apiKey);


        callCurrentWeather.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                currentWeather = response.body();
                setCurrentWeatherData();
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                t.getMessage();
            }
        });

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        TextView textViewDayOfTheWeek = findViewById(R.id.textViewDayOfTheWeek);
        textViewDayOfTheWeek.setText(new SimpleDateFormat("E, dd MMM", Locale.ENGLISH).format(date.getTime()));

        //weather forecast
        Call<WeatherForecast> callWeatherForecast = iWeatherApi.weatherForecast("50.431759", "30.517023", "current,minutely,hourly", IWeatherApi.apiKey);
        callWeatherForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                weatherForecast = response.body();
                setWeatherForecastData();
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void setCurrentWeatherData() {
        // find all TextViews to fill
        TextView textViewCity = findViewById(R.id.textViewCity);
        TextView textViewTemperature = findViewById(R.id.textViewTemperature);
        TextView textViewTemperatureRealFeel = findViewById(R.id.textViewTemperatureRealFeel);
        TextView textViewWeatherText = findViewById(R.id.textViewWeatherText);
        TextView textViewWeatherIcon = findViewById(R.id.textViewWeatherIcon);
        CardView cardViewCurrentWeather = findViewById(R.id.cardViewCurrentWeather);

        // filling all data for current weather
        textViewCity.setText(currentWeather.getName());
        textViewTemperature.setText(String.valueOf((int) currentWeather.getMainWeatherInfo().getTemp()));
        textViewTemperatureRealFeel.setText(String.valueOf((int) currentWeather.getMainWeatherInfo().getFeels_like()));
        textViewWeatherText.setText(currentWeather.getWeather().get(0).getMain());

        //getWeatherVIewInformation
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(currentWeather.getWeather().get(0).getMain());
        WeatherViewInformation.IconAndColorOfCurrentWeather weatherViewInfo = WeatherViewInformation.getWeatherViewInfo(weatherCondition);
        textViewWeatherIcon.setText(weatherViewInfo.iconCode);
        cardViewCurrentWeather.setCardBackgroundColor(Color.parseColor(getResources().getString(weatherViewInfo.cardBackgroundColorId)));
    }

    private void setWeatherForecastData() {
        RecyclerView recyclerViewWeatherForecast = findViewById(R.id.recyclerViewWeatherForecast);
        weatherForecast.getDaily().remove(0); // 0 index element is current day
        RecyclerViewWeatherForecastAdapter recyclerViewWeatherForecastAdapter = new RecyclerViewWeatherForecastAdapter(weatherForecast.getDaily());
        recyclerViewWeatherForecast.setAdapter(recyclerViewWeatherForecastAdapter);
        recyclerViewWeatherForecast.setLayoutManager(new LinearLayoutManager(this));
    }
}