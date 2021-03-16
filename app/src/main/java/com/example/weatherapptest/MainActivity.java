package com.example.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavType;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.IWeatherApi;
import com.example.weatherapptest.retrofit.models.CurrentWeather;
import com.example.weatherapptest.retrofit.models.Hourly;
import com.example.weatherapptest.retrofit.models.WeatherForecast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private WeatherForecast weatherForecast;
    private String cityName;

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


        //current, daily, hourly forecast
        Call<WeatherForecast> callCurrentWeather = iWeatherApi.weatherForecast("50.431759", "30.517023", "minutely", IWeatherApi.apiKey);
        callCurrentWeather.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NotNull Call<WeatherForecast> call, @NotNull Response<WeatherForecast> response) {
                weatherForecast = response.body();
                setCurrentWeatherData();
                setWeatherForecastData();
                CardView cardView = findViewById(R.id.cardViewCurrentWeather);

                View.OnClickListener cardViewOnClickListener = v -> {
                    Intent intent = new Intent(MainActivity.this, CurrentWeatherDetails.class);
                    intent.putExtra("currentWeather", (Parcelable) weatherForecast.getCurrent());
                    intent.putExtra("cityName", cityName);
                    intent.putParcelableArrayListExtra("hourly", (ArrayList<Hourly>) weatherForecast.getHourly());
                    MainActivity.this.startActivity(intent);

                };

                cardView.setOnClickListener(cardViewOnClickListener);
            }

            @Override
            public void onFailure(@NotNull Call<WeatherForecast> call, @NotNull Throwable t) {
                t.getMessage();
            }
        });

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        TextView textViewDayOfTheWeek = findViewById(R.id.textViewDayOfTheWeek);
        textViewDayOfTheWeek.setText(new SimpleDateFormat("E, dd MMM").format(date.getTime()));

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
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(weatherForecast.getLat(), weatherForecast.getLon(), 1);
            cityName = addresses.get(0).getLocality();
            textViewCity.setText(cityName);
        } catch ( Exception err) {
            Log.e("CityError", err.getMessage());
        }
        textViewTemperature.setText(String.valueOf((int) weatherForecast.getCurrent().getTemp()));
        textViewTemperatureRealFeel.setText(String.valueOf((int) weatherForecast.getCurrent().getFeelsLike()));
        textViewWeatherText.setText(weatherForecast.getCurrent().getWeather().get(0).getMain());

        //getWeatherVIewInformation
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(weatherForecast.getCurrent()
                .getWeather().get(0).getMain());
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