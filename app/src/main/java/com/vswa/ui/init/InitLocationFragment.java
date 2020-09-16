package com.vswa.ui.init;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vswa.R;
import com.vswa.ui.main.MainActivity;

public class InitLocationFragment extends Fragment {
    private InitLocationPresenter initLocationPresenter;
    private CheckBox deviceLocationCheckBox;
    private final int PERMISSION_REQUEST_LOCATION = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init, container, false);
        initLocationPresenter = new InitLocationPresenter(getActivity(), this, (MainActivity) getActivity());
        TextView nextTextView = view.findViewById(R.id.fragment_start_next_text_view);
        final EditText locationEditText = view.findViewById(R.id.fragment_welcome_edit_text);
        deviceLocationCheckBox = view.findViewById(R.id.fragment_start_device_location_checkbox);
        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!locationEditText.getText().toString().isEmpty()) {
                    initLocationPresenter.saveLocationName(locationEditText.getText().toString());
                    ((MainActivity)getActivity()).openHomeFrame();
                } else if (deviceLocationCheckBox.isChecked()) {
                    initLocationPresenter.setLocationAvailability(true);
                    ((MainActivity)getActivity()).openHomeFrame();
                }
                else {
                    Toast.makeText(getActivity(), "You haven't entered anything", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deviceLocationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                locationEditText.setEnabled(!b);
                if (b) requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_LOCATION);
            }
        });
        return view;
    }

    public void setCheckedCheckBox(boolean check) {
        deviceLocationCheckBox.setChecked(check);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    setCheckedCheckBox(false);
                }
                return;
            }
        }
    }
}
