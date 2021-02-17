package com.example.weatherapptest.data;

import com.example.weatherapptest.R;

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

    public static IconAndColorOfCurrentWeather getWeatherViewInfo(WeatherCondition weatherCondition) {
        IconAndColorOfCurrentWeather iconAndColorOfCurrentWeather = new IconAndColorOfCurrentWeather();
        switch (weatherCondition) {
            case Thunderstorm:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "Q";
                break;
            case Drizzle:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "l";
                break;
            case Rain:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "K";
                break;
            case Snow:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "I";
                break;
            case Smoke:
            case Sand:
            case Ash:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "C";
                break;
            case Haze:
            case Mist:
            case Fog:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "Z";
                break;
            case Squall:
            case Dust:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = "E";
                break;
            case Tornado:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
                iconAndColorOfCurrentWeather.iconCode = ":";
                break;
            case Clear:
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.clear_bg);
                iconAndColorOfCurrentWeather.iconCode = "1";
                break;
            case Clouds:
                //TODO
                iconAndColorOfCurrentWeather.cardBackgroundColorId = (R.color.white);
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
