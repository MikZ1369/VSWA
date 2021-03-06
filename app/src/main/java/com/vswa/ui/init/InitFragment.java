package com.vswa.ui.init;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.vswa.R;
import com.vswa.data.DataManager;
import com.vswa.ui.main.MainActivity;

public class InitFragment extends Fragment {
    private TextView welcomeTextView;
    private TextView requestMassageTextView;
    private Button allowButton;
    private Button nextButton;
    private final int PERMISSION_REQUEST_LOCATION = 99;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init, container, false);
        welcomeTextView = view.findViewById(R.id.fragment_init_welcome_text);
        requestMassageTextView = view.findViewById(R.id.fragment_init_request_massage_text);
        allowButton = view.findViewById(R.id.fragment_init_allow_button);
        nextButton = view.findViewById(R.id.fragment_init_next_button);

        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openHomeFrame();
                new DataManager(getActivity(), (MainActivity) getActivity()).setLocationAvailability(true);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showWelcomeText();
    }

    private void showWelcomeText() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_show);

            welcomeTextView.startAnimation(animation);
            requestMassageTextView.startAnimation(animation);
            allowButton.startAnimation(animation);
        }

    }

    private void showNextButton() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Animation animationShow = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_show);
            Animation animationHide = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_hide);
            nextButton.startAnimation(animationShow);
            allowButton.startAnimation(animationHide);
        }

        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showNextButton();
                } else {
                }
                return;
            }
        }
    }
}
