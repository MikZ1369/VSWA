package com.vswa.ui.start;

import android.content.Context;

import com.vswa.data.DataManager;

public class StartPresenter {
    private DataManager dataManager;

    public StartPresenter(Context context) {
        dataManager = new DataManager(context);
    }

    public void saveLocationName(String locationName) {
        dataManager.saveLocationName(locationName);
    }
}
