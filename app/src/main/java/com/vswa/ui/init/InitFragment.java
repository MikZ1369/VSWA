package com.vswa.ui.init;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
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
        AlphaAnimation animationWelcome = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation animationRequestMassage = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation animationAllowButton = new AlphaAnimation(0.0f, 1.0f);

        animationWelcome.setDuration(1500);
        animationWelcome.setStartOffset(1000);
        animationWelcome.setFillAfter(true);

        animationRequestMassage.setDuration(1500);
        animationRequestMassage.setStartOffset(2700);
        animationRequestMassage.setFillAfter(true);

        animationAllowButton.setDuration(1000);
        animationAllowButton.setStartOffset(2700);
        animationAllowButton.setFillAfter(true);

        welcomeTextView.startAnimation(animationWelcome);
        requestMassageTextView.startAnimation(animationRequestMassage);
        allowButton.startAnimation(animationAllowButton);
    }

    private void showNextButton() {
        AlphaAnimation animationAllowButton = new AlphaAnimation(1.0f, 0.0f);
        AlphaAnimation animationNextButton = new AlphaAnimation(0.0f, 1.0f);

        animationAllowButton.setDuration(1000);
        animationAllowButton.setStartOffset(300);
        animationAllowButton.setFillAfter(true);

        animationNextButton.setDuration(1000);
        animationNextButton.setStartOffset(300);
        animationNextButton.setFillAfter(true);

        nextButton.setVisibility(View.VISIBLE);
        nextButton.startAnimation(animationNextButton);
        allowButton.startAnimation(animationAllowButton);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {

                // If request is cancelled, the result arrays are empty.
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
