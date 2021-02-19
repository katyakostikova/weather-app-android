package com.example.weatherapptest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.models.Daily;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecyclerViewWeatherForecastAdapter extends RecyclerView.Adapter<RecyclerViewWeatherForecastAdapter.DailyForecastViewHolder> {

    private final List<Daily> dailyForecast;

    public RecyclerViewWeatherForecastAdapter(List<Daily> daily) {
        dailyForecast = daily;
    }

    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast, parent, false);
        return new DailyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position) {
        // getting all data needed (day, icon)
        Daily daily = dailyForecast.get(position);
        Date date = new Date(TimeUnit.SECONDS.toMillis(daily.getDt()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM");
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(daily.getWeather().get(0).getMain());
        WeatherViewInformation.IconAndColorOfCurrentWeather weatherViewInfo = WeatherViewInformation.getWeatherViewInfo(weatherCondition);
        //data bind
        holder.textViewDate.setText(dateFormat.format(date));
        holder.textViewWeatherIconDaily.setText(weatherViewInfo.iconCode);
        holder.textViewTempDay.setText(String.valueOf((int) daily.getTemp().getDay()));
        holder.textViewTempNight.setText(String.valueOf((int) daily.getTemp().getNight()));
    }

    @Override
    public int getItemCount() {
        return dailyForecast.size();
    }

    public static class DailyForecastViewHolder extends RecyclerView.ViewHolder {

        protected TextView textViewDate;
        protected TextView textViewWeatherIconDaily;
        protected TextView textViewTempDay;
        protected TextView textViewTempNight;

        public DailyForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewWeatherIconDaily = itemView.findViewById(R.id.textViewWeatherIconDaily);
            textViewTempDay = itemView.findViewById(R.id.textViewTempDay);
            textViewTempNight = itemView.findViewById(R.id.textViewTempNight);
        }
    }
}
