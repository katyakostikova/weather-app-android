package com.example.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.IWeatherApi;
import com.example.weatherapptest.retrofit.models.CurrentWeather;

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


        Call<CurrentWeather> call = iWeatherApi.currentWeather("kyiv", "03469fb74d63125e36c7377337c6789d");


        call.enqueue(new Callback<CurrentWeather>() {
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
        //    int day = calendar.get(Calendar.DAY_OF_WEEK);
        Date date = calendar.getTime();
        TextView textViewDayOfTheWeek = findViewById(R.id.textViewDayOfTheWeek);
        textViewDayOfTheWeek.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
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
}