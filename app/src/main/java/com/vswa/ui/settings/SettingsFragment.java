package com.vswa.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vswa.R;
import com.vswa.ui.main.MainActivity;

public class SettingsFragment extends Fragment {
    private SettingsProvider provider;
    private TextView currentThemeNameTextView;
    private LinearLayout setThemeLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        currentThemeNameTextView = view.findViewById(R.id.fragment_set_current_theme_name);
        setThemeLinearLayout = view.findViewById(R.id.fragment_set_LL_theme);
        provider = new SettingsProvider(getActivity(), (MainActivity) getActivity(), this);
        provider.onBindView();

        setThemeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provider.openSetThemeFrame();
            }
        });
        return view;
    }

    public void setCurrentThemeName(String name) {
        currentThemeNameTextView.setText(name);
    }

    public void createThemeSetAlertDialog(int checkedItem) {
        new AlertDialog.Builder(getContext(), R.style.LightAlertDialog).setSingleChoiceItems(R.array.themes_name,
                checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                provider.setThemeKey(i);
                dialogInterface.dismiss();
                provider.restartActivity();
            }
        }).show();
    }

}
