package com.json.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.json.R;
import com.json.dataobject.WeatherInfo;
import com.json.service.OnWeatherServiceListener;
import com.json.service.WeatherServiceHandler;
import com.json.ui.adapter.WeatherAdapter;
import com.json.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnWeatherServiceListener{

    private ProgressDialog progressBar;
    private ListView listView;
    private WeatherAdapter weatherAdapter;
    private List<WeatherInfo> weatherData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.lv_weather);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.show();
        new WeatherServiceHandler(this).execute(Constant.URL_APP);
    }

    @Override
    public void onWeatherSuccess(List<WeatherInfo> weatherData) {
        this.weatherData = weatherData;
        progressBar.dismiss();
        weatherAdapter = new WeatherAdapter(this,weatherData);
        listView.setAdapter(weatherAdapter);
    }

    @Override
    public void onWeatherDataEmpty() {
        progressBar.dismiss();
        Toast.makeText(this,"No Data is Available",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWeatherFailure() {
        progressBar.dismiss();
        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
    }
}
