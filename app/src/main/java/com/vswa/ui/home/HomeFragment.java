package com.vswa.ui.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vswa.R;

public class HomeFragment extends Fragment {
    private HomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homePresenter = new HomePresenter(getActivity());
        homePresenter.onBindView();
        return view;
    }
}
