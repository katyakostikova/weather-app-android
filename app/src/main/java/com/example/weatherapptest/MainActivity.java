package com.example.weatherapptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.IWeatherApi;
import com.example.weatherapptest.retrofit.models.Daily;
import com.example.weatherapptest.retrofit.models.Hourly;
import com.example.weatherapptest.retrofit.models.WeatherForecast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<WeatherForecast> {

    private WeatherForecast weatherForecast;
    private String cityName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(AppSettingsActivity.settingsFileName, MODE_PRIVATE);

        LoaderManager.getInstance(this).initLoader(1, null, this);

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

        boolean isCelsius = false;
        TextView textViewUnit = findViewById(R.id.textViewUnit);
        TextView textViewUnit2 = findViewById(R.id.textViewUnit2);
        if(sharedPreferences.getString("UNIT_PARAMS", getResources().getString(R.string.celsius)).equals(getResources().getString(R.string.celsius))) {
            isCelsius = true;
            textViewUnit.setText(R.string.celsius);
            textViewUnit2.setText(R.string.celsius);
        } else {
            textViewUnit.setText(R.string.fahrenheit);
            textViewUnit2.setText(R.string.fahrenheit);
        }
        textViewTemperature.setText(String.valueOf((int) weatherForecast.getCurrent().getTemp(isCelsius)));
        textViewTemperatureRealFeel.setText(String.valueOf((int) weatherForecast.getCurrent().getFeelsLike(isCelsius)));
        textViewWeatherText.setText(weatherForecast.getCurrent().getWeather().get(0).getMain());

        //getWeatherVIewInformation
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(weatherForecast.getCurrent()
                .getWeather().get(0).getMain());
        Date sunrise = new Date(TimeUnit.SECONDS.toMillis(weatherForecast.getCurrent().getSunrise()));
        Date sunset = new Date(TimeUnit.SECONDS.toMillis(weatherForecast.getCurrent().getSunset()));
        WeatherViewInformation.IconAndColorOfCurrentWeather weatherViewInfo = WeatherViewInformation.getWeatherViewInfo(weatherCondition, Calendar.getInstance().getTime(), sunrise, sunset);
        textViewWeatherIcon.setText(weatherViewInfo.iconCode);
        cardViewCurrentWeather.setCardBackgroundColor(Color.parseColor(getResources().getString(weatherViewInfo.cardBackgroundColorId)));
    }

    private void setWeatherForecastData() {
        RecyclerView recyclerViewWeatherForecast = findViewById(R.id.recyclerViewWeatherForecast);
        weatherForecast.getDaily().remove(0); // 0 index element is current day
        RecyclerViewWeatherForecastAdapter recyclerViewWeatherForecastAdapter = new RecyclerViewWeatherForecastAdapter(weatherForecast.getDaily(),
                sharedPreferences.getString("UNIT_PARAMS", getResources().getString(R.string.celsius)));
        recyclerViewWeatherForecast.setAdapter(recyclerViewWeatherForecastAdapter);
        recyclerViewWeatherForecast.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setCardViewOnClickListener() {
        CardView cardView = findViewById(R.id.cardViewCurrentWeather);

        View.OnClickListener cardViewOnClickListener = v -> {
            Intent intent = new Intent(MainActivity.this, CurrentWeatherDetails.class);
            intent.putExtra("currentWeather", (Parcelable) weatherForecast.getCurrent());
            intent.putExtra("cityName", cityName);
            intent.putParcelableArrayListExtra("hourly", (ArrayList<Hourly>) weatherForecast.getHourly());
            intent.putParcelableArrayListExtra("daily", (ArrayList<Daily>) weatherForecast.getDaily());
            intent.putExtra("units", sharedPreferences.getString("UNIT_PARAMS", getResources().getString(R.string.celsius)));
            MainActivity.this.startActivity(intent);
        };

        cardView.setOnClickListener(cardViewOnClickListener);
    }

    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_settings:
                Intent intent = new Intent(this, AppSettingsActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.app_bar_update:
                LoaderManager.getInstance(this).restartLoader(1, null, this);
                TextView textViewIcon = findViewById(R.id.textViewWeatherIcon);
                CardView cardView = findViewById(R.id.cardViewCurrentWeather);
                textViewIcon.setText("•");
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //LOADER
    @NonNull
    @Override
    public androidx.loader.content.Loader<WeatherForecast> onCreateLoader(int id, @Nullable Bundle args) {
        return new WeatherForecastLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<WeatherForecast> loader, WeatherForecast data) {
        if(data != null) {
            this.weatherForecast = data;
            setCurrentWeatherData();
            setWeatherForecastData();
            setCardViewOnClickListener();
        } else {
            Toast.makeText( this, "Server error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<WeatherForecast> loader) {
    }


    private static class WeatherForecastLoader extends AsyncTaskLoader<WeatherForecast> {

        public WeatherForecastLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public WeatherForecast loadInBackground() {
            WeatherForecast weatherForecast = null;
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl("https://api.openweathermap.org/").
                    addConverterFactory(GsonConverterFactory.create()).
                    build();

            IWeatherApi iWeatherApi = retrofit.create(IWeatherApi.class);

            Call<WeatherForecast> callCurrentWeather = iWeatherApi.weatherForecast("50.431759", "30.517023", "minutely", BuildConfig.WEATHER_API_KEY);
            try {
                Response<WeatherForecast> response = callCurrentWeather.execute();
                weatherForecast = response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherForecast;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }
    }
}

