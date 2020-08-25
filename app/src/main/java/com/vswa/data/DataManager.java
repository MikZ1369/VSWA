package com.vswa.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.vswa.BuildConfig;
import com.vswa.data.models.LocationApp;
import com.vswa.data.models.WeatherData;
import com.vswa.util.HttpRequest;

public class DataManager {
    protected final String APP_PREFERENCES_KEY = "com.vswa";
    private final String LOCATION_SAVED_KEY = "LOCATION_SAVED_KEY";
    private final String LOCATION_NAME_KEY = "LOCATION_NAME_KEY";
    private final String LOCATION_LAT = "LOCATION_LAT";
    private final String LOCATION_LON = "LOCATION_LON";
    private final String TARGET_URL_CURRENT_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private final String TARGET_URL_ONE_CALL = "https://api.openweathermap.org/data/2.5/onecall";
    private SharedPreferences sharedPreferences;

    public DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_KEY,
                Context.MODE_PRIVATE);
    }

    public WeatherData getWeatherData() {
        LocationApp locationApp = getLocation();
        if (locationApp == null) return null;
        String jsonResponse = HttpRequest.getJsonFromURL(TARGET_URL_ONE_CALL +
                "?lat=" + locationApp.latitude +"&lon=" + locationApp.longitude +
                        "&%20exclude=hourly,daily&appid=" + getAccessToken());
        WeatherData weatherData = WeatherParser.getWeatherDataByJson(jsonResponse);
        return weatherData;
    }

    public void saveLocationName(String locationName) {
        sharedPreferences.edit().putString(LOCATION_NAME_KEY, locationName).apply();
        sharedPreferences.edit().putBoolean(LOCATION_SAVED_KEY, true).apply();
    }

    public boolean checkLocationAvailability() {
        return sharedPreferences.getBoolean(LOCATION_SAVED_KEY, false);
    }

    private LocationApp getLocation() {
        LocationApp locationApp = new LocationApp();
        if (sharedPreferences.getFloat(LOCATION_LON, -200) == -200) {
            String locationName = sharedPreferences.getString(LOCATION_NAME_KEY, "");
            locationApp = getLocationCoordinatesByLocationName(locationName);
            if (locationApp == null) return null;
            locationApp.locationName = locationName;
            saveLocationCoordinates(locationApp);
            return locationApp;
        } else {
            locationApp.locationName = sharedPreferences.getString(LOCATION_NAME_KEY, "");
            locationApp.longitude = sharedPreferences.getFloat(LOCATION_LON, -200);
            locationApp.latitude = sharedPreferences.getFloat(LOCATION_LAT, -200);
            return locationApp;
        }
    }


    private void saveLocationCoordinates(LocationApp locationApp) {
        sharedPreferences.edit().putFloat(LOCATION_LAT, locationApp.latitude).apply();
        sharedPreferences.edit().putFloat(LOCATION_LON, locationApp.longitude).apply();
    }

    private LocationApp getLocationCoordinatesByLocationName(String locationName) {
        String jsonResponse = HttpRequest.getJsonFromURL(TARGET_URL_CURRENT_WEATHER +
                "?q=" + locationName + "&appid=" + getAccessToken());
        if (jsonResponse != null) {
            LocationApp locationApp = WeatherParser.getCoordinatesByCurrentWeather(jsonResponse);
            return locationApp;
        }
        return null;
    }

    private String getAccessToken() {
        return BuildConfig.ACCESS_TOKEN;
    }
}
