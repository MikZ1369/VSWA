package com.vswa.ui.init;

import android.content.Context;

import com.vswa.data.DataManager;
import com.vswa.ui.main.MainActivity;

public class InitLocationPresenter {
    private DataManager dataManager;
    private InitLocationFragment view;

    public InitLocationPresenter(Context context, InitLocationFragment fragment, MainActivity activity) {
        dataManager = new DataManager(context, activity);
        view = fragment;
    }

    public void saveLocationName(String locationName) {
        dataManager.saveLocationName(locationName);
    }

    public void setLocationAvailability(boolean availability) {
        dataManager.setLocationAvailability(availability);
    }

}
