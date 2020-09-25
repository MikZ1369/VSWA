package com.vswa.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.vswa.R;
import com.vswa.ui.home.HomeFragment;
import com.vswa.ui.init.InitFragment;
import com.vswa.ui.settings.SettingsFragment;

public class MainActivity extends Activity {
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainPresenter = new MainPresenter(this, this);
        switch (mainPresenter.getThemeKey()) {
            case 0: {
                setTheme(R.style.LightMode);
                break;
            }
            case 1: {
                setTheme(R.style.DarkMode);
                break;
            }
        }
        setContentView(R.layout.activity_main);
        mainPresenter.onAttach();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment, boolean forceNewFragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getFragmentManager();
        if (forceNewFragment) {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_main_frame_layout, fragment, backStateName);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(backStateName);
            fragmentTransaction.commit();
        } else {
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

            if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frame_layout, fragment, backStateName);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
        }
    }

    public void openWelcomeFrame() { openFragment(new InitFragment(), true); }

    public void openHomeFrame() { openFragment(new HomeFragment(), false);}

    public void openSettingsFrame() { openFragment(new SettingsFragment(), false);}

    public boolean checkLocationPermission() {
        int locationPermission = this.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        return (locationPermission == PackageManager.PERMISSION_GRANTED);
    }

}
