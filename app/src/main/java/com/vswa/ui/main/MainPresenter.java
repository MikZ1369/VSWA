package com.vswa.ui.main;

import android.content.Context;

import com.vswa.data.DataManager;

public class MainPresenter {
    private MainActivity activity;
    private DataManager dataManager;

    public MainPresenter(MainActivity activity, Context context) {
        this.activity = activity;
        dataManager = new DataManager(context, activity);
    }

    public void onAttach() {
        if (dataManager.checkLocationAvailability()) {
            activity.openHomeFrame();
        } else {
            activity.openWelcomeFrame();
        }
    }

    public int getThemeKey() {
        return dataManager.getThemeKey();
    }
}
