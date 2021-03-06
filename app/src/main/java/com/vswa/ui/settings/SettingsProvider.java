package com.vswa.ui.settings;

import android.app.Activity;
import android.content.Context;
import com.vswa.data.DataManager;
import com.vswa.ui.main.MainActivity;

public class SettingsProvider {
    private DataManager dataManager;
    private Activity activity;
    private SettingsFragment view;
    private int themeId;

    public SettingsProvider(Context context, MainActivity activity, SettingsFragment settingsFragment) {
        dataManager = new DataManager(context, activity);
        this.activity = activity;
        view = settingsFragment;
    }

    public void onBindView() {
        themeId = dataManager.getThemeKey();
        switch (themeId) {
            case 0: {
                view.setCurrentThemeName("Light");
                break;
            }
            case 1: {
                view.setCurrentThemeName("Dark");
                break;
            }
        }
    }

    public void openSetThemeFrame() {
        view.createThemeSetAlertDialog(themeId);
    }

    public void setThemeKey(int key) {
        dataManager.setThemeKey(key);
    }

    public void restartActivity() {
        activity.recreate();
    }
}
