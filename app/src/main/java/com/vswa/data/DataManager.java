package com.vswa.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.vswa.BuildConfig;
import com.vswa.data.models.LocationApp;
import com.vswa.data.models.WeatherData;
import com.vswa.ui.main.MainActivity;
import com.vswa.util.HttpRequest;

import java.util.Objects;

public class DataManager {
    protected final String APP_PREFERENCES_KEY = "com.vswa";
    private MainActivity activity;
    private final String LOCATION_SAVED_KEY = "LOCATION_SAVED_KEY";
    private final String LOCATION_AVAILABILITY_KEY = "LOCATION_AVAILABILITY_KEY";
    private final String LOCATION_NAME_KEY = "LOCATION_NAME_KEY";
    private final String LOCATION_LAT = "LOCATION_LAT";
    private final String LOCATION_LON = "LOCATION_LON";
    private final String TARGET_URL_CURRENT_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private final String TARGET_URL_ONE_CALL = "https://api.openweathermap.org/data/2.5/onecall";
    private final String THEME_KEY = "THEME_KEY";
    private SharedPreferences sharedPreferences;

    public DataManager(Context context, MainActivity activity) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        this.activity = activity;
    }

    public WeatherData getWeatherData(String json) {
        WeatherData weatherData = WeatherParser.getWeatherDataByJson(json);
        return weatherData;
    }

    public String requestLocationName(Location location) throws NotAvailableLocationException {
        return getLocationName(location).locationName;
    }

    public String requestWeatherData(Location location) throws NotAvailableLocationException {
        String jsonResponse = HttpRequest.getJsonFromURL(TARGET_URL_ONE_CALL +
                "?lat=" + location.getLatitude() +"&lon=" + location.getLongitude() +
                "&%20exclude=hourly,daily&appid=" + getAccessToken());
        return jsonResponse;
    }

    public void setLocationAvailability(boolean availability) {
        sharedPreferences.edit().putBoolean(LOCATION_AVAILABILITY_KEY, availability).apply();
    }

    public boolean checkLocationAvailability() {
        return (sharedPreferences.getBoolean(LOCATION_SAVED_KEY, false) ||
                sharedPreferences.getBoolean(LOCATION_AVAILABILITY_KEY, false));
    }

    public void setThemeKey(int key) {
        sharedPreferences.edit().putInt(THEME_KEY, key).apply();
    }

    public int getThemeKey() {
        return sharedPreferences.getInt(THEME_KEY, 0);
    }

    private LocationApp getLocationName(Location location) throws NotAvailableLocationException {
        LocationApp locationApp = new LocationApp();
        if (activity.checkLocationPermission()) {
            try {
                locationApp.latitude = (float) location.getLatitude();
                locationApp.longitude = (float) location.getLongitude();
                locationApp.locationName = getLocationNameByLocationCoordinates(locationApp);
                return locationApp;
            } catch (NullPointerException e) {
                throw new NotAvailableLocationException("Location not available");
            }
        }
        return locationApp;
    }

    private void saveLocationCoordinates(LocationApp locationApp) {
        sharedPreferences.edit().putFloat(LOCATION_LAT, locationApp.latitude).apply();
        sharedPreferences.edit().putFloat(LOCATION_LON, locationApp.longitude).apply();
    }

    private String getLocationNameByLocationCoordinates(LocationApp locationApp) {
        String jsonResponse = HttpRequest.getJsonFromURL(TARGET_URL_CURRENT_WEATHER +
                "?lat=" + locationApp.latitude + "&lon=" + locationApp.longitude + "&appid=" + getAccessToken());
        if (jsonResponse != null) {
            String locationName = WeatherParser.getLocationNameByCurrentWeather(jsonResponse);
            return locationName;
        }
        return null;
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

    public static class NotAvailableLocationException extends Exception {
        public NotAvailableLocationException(String errorMessage) {
            super(errorMessage);
        }
    }
}
