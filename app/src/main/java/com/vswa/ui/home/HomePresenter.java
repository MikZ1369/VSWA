package com.vswa.ui.home;

import android.content.Context;
import android.os.AsyncTask;

import com.vswa.data.DataManager;
import com.vswa.data.models.WeatherData;

public class HomePresenter {
    private DataManager dataManager;
    private HomeFragment view;

    public HomePresenter(Context context, HomeFragment view) {
        dataManager = new DataManager(context);
        this.view = view;
    }

    public void onBindView() {
        HomePrenterAsync homePrenterAsync = new HomePrenterAsync();
        homePrenterAsync.execute();
    }

    class HomePrenterAsync extends AsyncTask<Void, Void, WeatherData> {

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

    }


}
