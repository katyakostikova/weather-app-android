package com.example.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.models.CurrentWeather;
import com.example.weatherapptest.retrofit.models.Hourly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CurrentWeatherDetails extends AppCompatActivity {

    private CurrentWeather currentWeather;

    private List<Hourly> hourly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather_details);
        Intent intent = getIntent();
        currentWeather = intent.getExtras().getParcelable("currentWeather");
        hourly = intent.getParcelableArrayListExtra("hourly");

        //on back button
        ImageButton imageButtonBack = findViewById(R.id.buttonBack);
        View.OnClickListener onBackClickListener = v -> {
            onBackPressed();
        };
        imageButtonBack.setOnClickListener(onBackClickListener);

        //icon and weather text
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(currentWeather
                .getWeather().get(0).getMain());
        WeatherViewInformation.IconAndColorOfCurrentWeather weatherViewInfo = WeatherViewInformation.getWeatherViewInfo(weatherCondition);
        TextView textViewWeatherIconInDet = findViewById(R.id.textViewWeatherIconInDet);
        textViewWeatherIconInDet.setText(weatherViewInfo.iconCode);
        TextView textViewWeatherTextInDet = findViewById(R.id.textViewWeatherTextInDet);
        textViewWeatherTextInDet.setText(currentWeather.getWeather().get(0).getMain());
        //current weather info
        TextView textViewTempInDet = findViewById(R.id.textViewTempInDet);
        TextView textViewRealFeelInDet = findViewById(R.id.textViewRealFeelInDet);
        TextView textViewSunrise = findViewById(R.id.textViewSunrise);
        TextView textViewSunset = findViewById(R.id.textViewSunset);
        TextView textViewHumidity = findViewById(R.id.textViewHumidity);
        TextView textViewPressure = findViewById(R.id.textViewPressure);
        TextView textViewWindIcon = findViewById(R.id.textViewWindIcon);
        TextView textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        TextView textViewUVI = findViewById(R.id.textViewUVI);
        TextView textViewCityInDet = findViewById(R.id.textViewCityInDet);

        textViewCityInDet.setText(intent.getExtras().getString("cityName"));
        textViewTempInDet.setText(String.valueOf((int) currentWeather.getTemp()));
        textViewRealFeelInDet.setText(String.valueOf((int) currentWeather.getFeelsLike()));

        Date sunrise = new Date(TimeUnit.SECONDS.toMillis(currentWeather.getSunrise()));
        Date sunset = new Date(TimeUnit.SECONDS.toMillis(currentWeather.getSunset()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        textViewSunrise.setText(dateFormat.format(sunrise));
        textViewSunset.setText(dateFormat.format(sunset));
        textViewHumidity.setText(currentWeather.getHumidity() + "%");
        textViewPressure.setText(String.valueOf(currentWeather.getPressure()));
        textViewWindSpeed.setText(Float.toString(currentWeather.getWindSpeed()) + " km/h");
        if (currentWeather.getUvi() <= 2.5) {
            textViewUVI.setText(R.string.uvi_low);
        } else if(currentWeather.getUvi() <= 6.5 && currentWeather.getUvi() > 2.5) {
            textViewUVI.setText(R.string.uvi_medium);
        } else if(currentWeather.getUvi() <= 8.5 && currentWeather.getUvi() > 6.5) {
            textViewUVI.setText(R.string.uvi_high);
        } else if(currentWeather.getUvi() > 8.5) {
            textViewUVI.setText(R.string.uvi_very_high);
        }

        if ( 22 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 68) {
            textViewWindIcon.setText(".");
        } else if ( 68 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 112) {
            textViewWindIcon.setText("·");
        } else if (112 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 156) {
            textViewWindIcon.setText("?");
        } else if (156 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 203) {
            textViewWindIcon.setText("¿");
        } else if (203 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 248) {
            textViewWindIcon.setText("\"");
        } else if (248 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 293) {
            textViewWindIcon.setText("'");
        } else if (293 < currentWeather.getWindDeg() && currentWeather.getWindDeg() <= 328) {
            textViewWindIcon.setText(";");
        } else if (338 < currentWeather.getWindDeg() ) {
            textViewWindIcon.setText("#");
        } else if ( currentWeather.getWindDeg() <= 22 ) {
            textViewWindIcon.setText("#");
        }


        //recycler view

        RecyclerView recyclerViewHourly = findViewById(R.id.recyclerViewHourly);
        RecyclerViewHourlyAdapter recyclerViewHourlyAdapter = new RecyclerViewHourlyAdapter(hourly);
        recyclerViewHourly.setAdapter(recyclerViewHourlyAdapter);
        recyclerViewHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



    }



}