package com.vswa.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import com.vswa.R;
import com.vswa.data.Constants;
import com.vswa.data.DataManager;
import com.vswa.data.models.WeatherData;
import com.vswa.ui.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomePresenter {
    private DataManager dataManager;
    private HomeFragment view;
    private Context context;

    public HomePresenter(Context context, HomeFragment view, MainActivity activity) {
        dataManager = new DataManager(context, activity);
        this.view = view;
        this.context = context;
    }

    public void onBindView() {
        HomePresenterAsync homePresenterAsync = new HomePresenterAsync();
        homePresenterAsync.execute();
    }

    class HomePresenterAsync extends AsyncTask<Void, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(Void... voids) {
            return dataManager.getWeatherData();
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {
            super.onPostExecute(weatherData);
            setView(weatherData);
        }
    }

    private void setView(WeatherData weatherData) {
        view.setLocationName(weatherData.location.locationName.toUpperCase());
        view.setWeatherMain(weatherData.currentWeather.weatherName);
        view.setWeatherIcon(getResourceIdForWeatherIcon(weatherData.currentWeather.weatherIcon));
        view.setCurrentTemp(getTempFromKelvin(weatherData.currentWeather.temp));
        view.setCurrentDayTemp(getTempFromKelvin(weatherData.forecastWeatherArrayList.get(0).tempDay));
        view.setCurrentNightTemp(getTempFromKelvin(weatherData.forecastWeatherArrayList.get(0).tempNight));
        view.setCurrentWindSpeed((int) Math.round(weatherData.currentWeather.windSpeed));
        view.setCurrentWindDeg("SE direction"); // TODO rewrite

        ArrayList<String> forecastDateList = new ArrayList<>();
        ArrayList<Integer> iconIdList = new ArrayList<>();
        ArrayList<Integer> forecastTempDayList = new ArrayList<>();
        ArrayList<Integer> forecastTempNightList = new ArrayList<>();

        for (int item = 1; item < 7; item++) {
            forecastDateList.add(getDayOfWeekFromUnixTime(
                    weatherData.forecastWeatherArrayList.get(item).dt));
            iconIdList.add(getResourceIdForWeatherIcon(
                    weatherData.forecastWeatherArrayList.get(item).weatherIcon));
            forecastTempDayList.add(getTempFromKelvin(
                    weatherData.forecastWeatherArrayList.get(item).tempDay));
            forecastTempNightList.add(getTempFromKelvin(
                    weatherData.forecastWeatherArrayList.get(item).tempNight));
        }

        view.setForecastDate(forecastDateList);
        view.setForecastIcon(iconIdList);
        view.setForecastTemp(forecastTempDayList, forecastTempNightList);
    }

    private int getResourceIdForWeatherIcon(String iconName) {
        switch (iconName) {
            case Constants.CLEAR_SKY: {
                return R.drawable.ic_clear_sky_day;
            }
            case Constants.CLEAR_SKY_NIGHT: {
                return R.drawable.ic_clear_sky_night;
            }
            case Constants.FEW_CLOUDS: {
                return R.drawable.ic_few_clouds_day;
            }
            case Constants.FEW_CLOUDS_NIGHT: {
                return R.drawable.ic_few_clouds_night;
            }
            case Constants.SCATTERED_CLOUDS:
            case Constants.BROKEN_CLOUDS:
            case Constants.BROKEN_CLOUDS_NIGHT:
            case Constants.SCATTERED_CLOUDS_NIGHT: {
                return R.drawable.ic_scattered_clouds;
            }
            case Constants.SHOWER_RAIN:
            case Constants.SHOWER_RAIN_NIGHT: {
                return R.drawable.ic_shower_rain;
            }
            case Constants.RAIN: {
                return R.drawable.ic_rain_day;
            }
            case Constants.RAIN_NIGHT: {
                return R.drawable.ic_rain_night;
            }
            case Constants.THUNDERSTORM:
            case Constants.THUNDERSTORM_NIGHT: {
                return R.drawable.ic_thunderstorm;
            }
            case Constants.SNOW:
            case Constants.SNOW_NIGHT: {
                return R.drawable.ic_snow;
            }
            case Constants.MIST:
            case Constants.MIST_NIGHT: {
                return R.drawable.ic_mist;
            }
        }
        return R.drawable.ic_clear_sky_day;
    }

    private int getTempFromKelvin(float kelvinTemp) {
        return (int) Math.round(kelvinTemp - 273.15);
    }

    private String getDayOfWeekFromUnixTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        String dayOfWeek = dateFormat.format(date);
        switch (dayOfWeek) {
            case "Mon": {
                return context.getResources().getString(R.string.Mon);
            }
            case "Tue": {
                return context.getResources().getString(R.string.Tue);
            }
            case "Wed": {
                return context.getResources().getString(R.string.Wed);
            }
            case "Thu": {
                return context.getResources().getString(R.string.Thu);
            }
            case "Fri": {
                return context.getResources().getString(R.string.Fri);
            }
            case "Sat": {
                return context.getResources().getString(R.string.Sat);
            }
            case "Sun": {
                return context.getResources().getString(R.string.Sun);
            }
        }
        return context.getResources().getString(R.string.Mon);
    }
}

