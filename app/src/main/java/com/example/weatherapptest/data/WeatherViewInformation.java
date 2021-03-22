package com.example.weatherapptest.data;

import com.example.weatherapptest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class WeatherViewInformation {

    public enum WeatherCondition {
        Thunderstorm,
        Drizzle,
        Rain,
        Snow,
        Mist,
        Smoke,
        Haze,
        Dust,
        Fog,
        Sand,
        Ash,
        Squall,
        Tornado,
        Clear,
        Clouds
    }

    public static IconAndColorOfCurrentWeather getWeatherViewInfo(WeatherCondition weatherCondition, Date currentTime) {
        IconAndColorOfCurrentWeather iconAndColorOfCurrentWeather = new IconAndColorOfCurrentWeather();
        switch (weatherCondition) {
            case Thunderstorm:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.thunderstorm);
                iconAndColorOfCurrentWeather.iconCode = "Q";
                break;
            case Drizzle:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.drizzle);
                iconAndColorOfCurrentWeather.iconCode = "l";
                break;
            case Rain:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.rain);
                iconAndColorOfCurrentWeather.iconCode = "K";
                break;
            case Snow:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.snow);
                iconAndColorOfCurrentWeather.iconCode = "I";
                break;
            case Smoke:
            case Sand:
            case Ash:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.sand);
                iconAndColorOfCurrentWeather.iconCode = "C";
                break;
            case Haze:
            case Mist:
            case Fog:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.mist);
                iconAndColorOfCurrentWeather.iconCode = "Z";
                break;
            case Squall:
            case Dust:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.dust);
                iconAndColorOfCurrentWeather.iconCode = "E";
                break;
            case Tornado:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.tornado);
                iconAndColorOfCurrentWeather.iconCode = ":";
                break;
            case Clear:
                if(currentTime == null) {
                    iconAndColorOfCurrentWeather.iconCode = "1";
                    iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.clear_bg);
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
                    if(parseInt(dateFormat.format(currentTime)) < 18 && parseInt(dateFormat.format(currentTime)) > 5) {
                        iconAndColorOfCurrentWeather.iconCode = "1";
                        iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.clear_bg);
                    } else {
                        iconAndColorOfCurrentWeather.iconCode = "6";
                        iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.clear_bg_night);
                    }
                }
                break;
            case Clouds:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.clouds);
                iconAndColorOfCurrentWeather.iconCode = "3";
                break;
        }
        return iconAndColorOfCurrentWeather;
    }

    public static class IconAndColorOfCurrentWeather {
        public String iconCode;
        public int cardBackgroundColorId;
    }
}
