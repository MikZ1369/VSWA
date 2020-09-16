package com.vswa.ui.home;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vswa.R;
import com.vswa.data.models.ForecastWeather;
import com.vswa.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomePresenter homePresenter;
    private TextView locationNameTextView;
    private TextView weatherMainTextView;
    private ImageView weatherIconImageView;
    private TextView weatherCurrentTempTextView;
    private TextView currentTempDayTextView;
    private TextView currentTempNightTextView;
    private TextView currentWindSpeedTextView;
    private TextView currentWindDegTextView;

    private ArrayList<TextView> daysDate = new ArrayList<>();
    private ArrayList<ImageView> daysWeatherIcon = new ArrayList<>();
    private ArrayList<TextView> daysDayTemp = new ArrayList<>();
    private ArrayList<TextView> daysNightTemp = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        locationNameTextView = view.findViewById(R.id.fragment_home_location_name);
        weatherMainTextView = view.findViewById(R.id.fragment_home_weather_main);
        weatherIconImageView = view.findViewById(R.id.fragment_home_weather_icon);
        weatherCurrentTempTextView = view.findViewById(R.id.fragment_home_current_temp);
        currentTempDayTextView = view.findViewById(R.id.fragment_home_current_temp_day);
        currentTempNightTextView = view.findViewById(R.id.fragment_home_current_temp_night);
        currentWindSpeedTextView = view.findViewById(R.id.fragment_home_current_wind_speed);
        currentWindDegTextView = view.findViewById(R.id.fragment_home_current_wind_deg);

        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day1_date));
        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day2_date));
        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day3_date));
        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day4_date));
        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day5_date));
        daysDate.add((TextView) view.findViewById(R.id.fragment_home_forecast_day6_date));

        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day1_icon));
        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day2_icon));
        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day3_icon));
        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day4_icon));
        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day5_icon));
        daysWeatherIcon.add((ImageView) view.findViewById(R.id.fragment_home_forecast_day6_icon));

        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day1_day_temp));
        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day2_day_temp));
        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day3_day_temp));
        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day4_day_temp));
        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day5_day_temp));
        daysDayTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day6_day_temp));

        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day1_night_temp));
        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day2_night_temp));
        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day3_night_temp));
        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day4_night_temp));
        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day5_night_temp));
        daysNightTemp.add((TextView) view.findViewById(R.id.fragment_home_forecast_day6_night_temp));

        homePresenter = new HomePresenter(getActivity(), this, (MainActivity) getActivity());
        homePresenter.onBindView();
        return view;
    }

    public void setLocationName(String locationName) {
        locationNameTextView.setText(locationName);
    }

    public void setWeatherMain(String weatherMain) {
        weatherMainTextView.setText(weatherMain);
    }

    public void setWeatherIcon(int id) {
        weatherIconImageView.setImageResource(id);
    }

    public void setCurrentTemp(int temp) {
        weatherCurrentTempTextView.setText(String.valueOf(temp));
    }

    public void setCurrentDayTemp(int temp) {
        currentTempDayTextView.setText(String.valueOf(temp));
    }

    public void setCurrentNightTemp(int temp) {
        currentTempNightTextView.setText(String.valueOf(temp));
    }

    public void setCurrentWindSpeed(int speed) {
        currentWindSpeedTextView.setText(String.valueOf(speed));
    }

    public void setCurrentWindDeg(String deg) { currentWindDegTextView.setText(deg);}

    public void setForecastDate(List<String> dateNameList) {
        for (int item = 0; item < dateNameList.size(); item++ ) {
            daysDate.get(item).setText(dateNameList.get(item));
        }
    }

    public void setForecastIcon(List<Integer> idList) {
        for (int item = 0; item < idList.size(); item++) {
            daysWeatherIcon.get(item).setImageResource(idList.get(item));
        }
    }

    public void setForecastTemp(List<Integer> tempDayList, List<Integer> tempNightList) {
        for (int item = 0; item < tempDayList.size(); item++) {
            daysDayTemp.get(item).setText(String.valueOf(tempDayList.get(item)));
            daysNightTemp.get(item).setText(String.valueOf(tempNightList.get(item)));
        }
    }

}
