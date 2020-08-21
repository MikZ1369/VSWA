package com.vswa.ui.main;

import android.content.Context;
import android.provider.ContactsContract;

import com.vswa.model.DataManager;

public class MainPresenter {
    private MainActivity activity;
    private DataManager dataManager;

    public MainPresenter(MainActivity activity, Context context) {
        this.activity = activity;
        dataManager = new DataManager(context);
    }

    public void onAttach() {
        if (dataManager.checkLocationAvailability()) {
            activity.openHomeFrame();
        } else {
            activity.openWelcomeFrame();
        }
    }
}
