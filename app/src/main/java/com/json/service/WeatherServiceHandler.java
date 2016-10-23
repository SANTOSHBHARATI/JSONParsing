package com.json.service;

import android.os.AsyncTask;
import com.json.dataobject.WeatherInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santosh on 22-10-2016.
 */

public class WeatherServiceHandler extends AsyncTask<String,Void,List<WeatherInfo>>{

    private OnWeatherServiceListener listener;
    private int responseCode;
    public WeatherServiceHandler(OnWeatherServiceListener listener){
        this.listener = listener;
    }


    @Override
    protected List<WeatherInfo> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        InputStream stream = null;
        BufferedReader reader = null;
        List<WeatherInfo> weatherList = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            responseCode = connection.getResponseCode();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalData = buffer.toString();
            JSONObject jsonObject = new JSONObject(finalData);
            JSONArray jsonArray = jsonObject.getJSONArray("list");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonFinalObject = jsonArray.getJSONObject(i).getJSONObject("temp");
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setMax(jsonFinalObject.getString("max"));
                weatherInfo.setMin(jsonFinalObject.getString("min"));
//                weatherInfo.setPressure(jsonFinalObject.getString("night"));
                weatherList.add(weatherInfo);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return weatherList;
    }

    @Override
    protected void onPostExecute(List<WeatherInfo> weatherInfos) {
        if (responseCode!=200){
            listener.onWeatherFailure();
        }else{
            if (weatherInfos.isEmpty()){
                listener.onWeatherDataEmpty();
            }else {
                listener.onWeatherSuccess(weatherInfos);
            }
        }
    }
}
