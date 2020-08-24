package com.vswa.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.vswa.BuildConfig;
import com.vswa.data.models.LocationApp;

public class DataManager {
    protected final String APP_PREFERENCES_KEY = "com.vswa";
    private final String LOCATION_SAVED_KEY = "LOCATION_SAVED_KEY";
    private final String LOCATION_NAME_KEY = "LOCATION_NAME_KEY";
    private SharedPreferences sharedPreferences;

    public DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_KEY,
                Context.MODE_PRIVATE);
    }

    public String getLocationName() {
        return sharedPreferences.getString(LOCATION_NAME_KEY, "");
    }

    public void saveLocationName(String locationName) {
        sharedPreferences.edit().putString(LOCATION_NAME_KEY, locationName).apply();
        sharedPreferences.edit().putBoolean(LOCATION_SAVED_KEY, true).apply();
    }

    public boolean checkLocationAvailability() {
        return sharedPreferences.getBoolean(LOCATION_SAVED_KEY, false);
    }

    private LocationApp getLocationCoordinates() {

        return new LocationApp();
    }

    private String getAccessToken() {
        return BuildConfig.ACCESS_TOKEN;
    }
}
