package com.vswa.data;

import com.vswa.data.models.CurrentWeather;
import com.vswa.data.models.ForecastWeather;
import com.vswa.data.models.LocationApp;
import com.vswa.data.models.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherParser {
    public static WeatherData getWeatherDataByJson(String json) {
        WeatherData weatherData = new WeatherData();
        CurrentWeather currentWeather = new CurrentWeather();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject currentJson = jsonObject.getJSONObject("current");
            currentWeather.dt = currentJson.getLong("dt");
            currentWeather.temp = (float) currentJson.getDouble("temp");
            currentWeather.feelsLike = (float) currentJson.getDouble("feels_like");
            currentWeather.pressure = (float) currentJson.getDouble("pressure");
            currentWeather.humidity = (float) currentJson.getDouble("humidity");
            currentWeather.uvi = (float) currentJson.getDouble("uvi");
            currentWeather.windSpeed = (float) currentJson.getDouble("wind_speed");
            currentWeather.windDeg = (float) currentJson.getDouble("wind_deg");

            JSONArray weatherArray = currentJson.getJSONArray("weather");
            currentWeather.weatherName = weatherArray.getJSONObject(0).getString("main");
            currentWeather.weatherIcon = weatherArray.getJSONObject(0).getString("icon");
        } catch (JSONException e) { }

        weatherData.currentWeather = currentWeather;
        ArrayList<ForecastWeather> forecastWeatherArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray dailyArray = jsonObject.getJSONArray("daily");
            for (int item = 0; item < dailyArray.length(); item++) {
                ForecastWeather forecastWeather = new ForecastWeather();
                JSONObject day = dailyArray.getJSONObject(item);
                forecastWeather.dt = day.getLong("dt");
                forecastWeather.tempDay = (float) day.getJSONObject("temp").getDouble("day");
                forecastWeather.tempNight = (float) day.getJSONObject("temp").getDouble("night");

                forecastWeather.feelsLikeDay = (float) day.getJSONObject("feels_like").getDouble("day");
                forecastWeather.feelsLikeNight = (float) day.getJSONObject("feels_like").getDouble("night");

                forecastWeather.pressure = (float) day.getDouble("pressure");
                forecastWeather.humidity = (float) day.getDouble("humidity");
                forecastWeather.windSpeed = (float) day.getDouble("wind_speed");
                forecastWeather.windDeg = (float) day.getDouble("wind_deg");

                forecastWeather.weatherName = day.getJSONArray("weather").getJSONObject(0).getString("main");
                forecastWeather.weatherIcon = day.getJSONArray("weather").getJSONObject(0).getString("icon");

                forecastWeatherArrayList.add(forecastWeather);
            }
        } catch (JSONException e) { }

        weatherData.forecastWeatherArrayList = forecastWeatherArrayList;

        return weatherData;
    }

    public static LocationApp getCoordinatesByCurrentWeather(String json) {
        LocationApp locationApp = new LocationApp();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject coordinatesJson = jsonObject.getJSONObject("coord");
            locationApp.latitude = (float) coordinatesJson.getDouble("lat");
            locationApp.longitude = (float) coordinatesJson.getDouble("lon");
            return locationApp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLocationNameByCurrentWeather(String json) {
        try {
            String locationName;
            JSONObject jsonObject = new JSONObject(json);
            locationName = jsonObject.getString("name");
            return locationName;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
