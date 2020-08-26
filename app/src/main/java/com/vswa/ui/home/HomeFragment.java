package com.vswa.ui.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vswa.R;
import com.vswa.data.models.ForecastWeather;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomePresenter homePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homePresenter = new HomePresenter(getActivity(), this);
        homePresenter.onBindView();
        return view;
    }

}
