package com.json.service;

import com.json.dataobject.WeatherInfo;

import java.util.List;

/**
 * Created by Santosh on 22-10-2016.
 */

public interface OnWeatherServiceListener {
    void onWeatherSuccess(List<WeatherInfo> weatherData);
    void onWeatherDataEmpty();
    void onWeatherFailure();
}
