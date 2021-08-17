package com.example.weatherapptest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapptest.data.WeatherViewInformation;
import com.example.weatherapptest.retrofit.models.CurrentWeather;
import com.example.weatherapptest.retrofit.models.Daily;
import com.example.weatherapptest.retrofit.models.Hourly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RecyclerViewHourlyAdapter extends RecyclerView.Adapter<RecyclerViewHourlyAdapter.HourlyViewHoler> {

    private final List<Hourly> hourlyList;
    private final List<Daily> dailyForecast;
    private final String units;
    private final CurrentWeather currentWeather;

    public RecyclerViewHourlyAdapter(List<Hourly> hourly, String units, List<Daily> dailyForecast, CurrentWeather currentWeather) {
        this.dailyForecast = dailyForecast;
        hourlyList = hourly;
        this.units = units;
        this.currentWeather = currentWeather;
    }

    @NonNull
    @Override
    public HourlyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast, parent, false);
        HourlyViewHoler hourlyViewHoler = new HourlyViewHoler(view);
        return hourlyViewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHoler holder, int position) {
        Hourly hourly = hourlyList.get(position);
        Date date = new Date(TimeUnit.SECONDS.toMillis(hourly.getDate()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM HH:mm");
        holder.textViewDate.setText(dateFormat.format(date));
        holder.textViewHumidity.setText(hourly.getHumidity() + "%");
        //for icon day/night
        SimpleDateFormat dateFormatForDay = new SimpleDateFormat("dd");
        Date sunrise = null;
        Date sunset = null;
        if(Integer.parseInt(dateFormatForDay.format(date)) == Integer.parseInt(dateFormatForDay.format(new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(currentWeather.getDate())))))) {
            sunrise = new Date(TimeUnit.SECONDS.toMillis(currentWeather.getSunrise()));
            sunset =  new Date(TimeUnit.SECONDS.toMillis(currentWeather.getSunset()));
        }
        else {
            for (Daily daily: dailyForecast) {
                if(Integer.parseInt(dateFormatForDay.format(date)) == Integer.parseInt(dateFormatForDay.format(new Date(TimeUnit.SECONDS.toMillis(daily.getDt()))))) {
                    sunrise = new Date(TimeUnit.SECONDS.toMillis(daily.getSunrise()));
                    sunset =  new Date(TimeUnit.SECONDS.toMillis(daily.getSunset()));
                    break;
                }
            }
        }

        boolean isCelsius = false;
        String unitCelsius = holder.itemView.getContext().getString(R.string.celsius);
        if(units.equals(unitCelsius)) {
            isCelsius = true;
            holder.textViewUnitHourly.setText(R.string.celsius);
        } else {
            holder.textViewUnitHourly.setText(R.string.fahrenheit);
        }
        holder.textViewTemperature.setText(String.valueOf((int) hourly.getTemp(isCelsius)));
        //icon
        WeatherViewInformation.WeatherCondition weatherCondition = WeatherViewInformation.WeatherCondition.valueOf(hourly.getWeather().get(0).getMain());
        WeatherViewInformation.IconAndColorOfCurrentWeather weatherViewInfo = WeatherViewInformation.getWeatherViewInfo(weatherCondition, date, sunrise, sunset );
        holder.textViewIcon.setText(weatherViewInfo.iconCode);
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    public class HourlyViewHoler extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewTemperature;
        public TextView textViewIcon;
        public TextView textViewHumidity;
        public TextView textViewUnitHourly;

        public HourlyViewHoler(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDateHourly);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperatureHourly);
            textViewIcon = itemView.findViewById(R.id.textViewWeatherIconHourly);
            textViewHumidity = itemView.findViewById(R.id.textViewHudimityHourly);
            textViewUnitHourly = itemView.findViewById(R.id.textViewUnitHourly);
        }

    }
}
