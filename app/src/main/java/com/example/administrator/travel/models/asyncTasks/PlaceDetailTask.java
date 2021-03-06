package com.example.administrator.travel.models.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;


import com.example.administrator.travel.models.entities.PlaceDetail;
import com.example.administrator.travel.models.listeners.Listener;

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

/**
 * Created by Admin on 5/9/2019.
 */

public class PlaceDetailTask extends AsyncTask<String, Void, String> {
    String data;
    Listener.OnGetPlaceDetailFinishedListener listener;

    public PlaceDetailTask(Listener.OnGetPlaceDetailFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];

        try {
            Log.e("place detail: ", url);
            data = readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(String res) {
        try {
            parseJSon(res);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            listener.onGetPlaceDetailFail(e);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onGetPlaceDetailFail(e);
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;
        PlaceDetail placeDetail = new PlaceDetail();

        JSONObject jsonData = new JSONObject(data).getJSONObject("result");
        if (!jsonData.isNull("geometry") && !jsonData.getJSONObject("geometry").isNull("location")) {
            JSONObject jsonLocation = jsonData.getJSONObject("geometry").getJSONObject("location");
            if (!jsonLocation.isNull("lat") && !jsonLocation.isNull("lng")) {
                placeDetail.latitude = jsonLocation.getDouble("lat");
                placeDetail.longitude = jsonLocation.getDouble("lng");

            }
        }
        if (!jsonData.isNull("place_id"))
            placeDetail.placeId = jsonData.getString("place_id");
        if (!jsonData.isNull("name"))
            placeDetail.name = jsonData.getString("name");
        if (!jsonData.isNull("rating"))
            placeDetail.rating = jsonData.getDouble("rating");
        if (!jsonData.isNull("rating"))
            placeDetail.rating = jsonData.getDouble("rating");
        if (!jsonData.isNull("user_rating_total"))
            placeDetail.userRatingsTotal = jsonData.getInt("user_rating_total");
        if (!jsonData.isNull("formatted_address"))
            placeDetail.formattedAddress = jsonData.getString("formatted_address");

        JSONArray jsonPhotos = jsonData.getJSONArray("photos");
        for (int i = 0; i < jsonPhotos.length(); i++) {
            JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);

            if (!jsonPhoto.isNull("photo_reference")) {
                String photo = jsonPhoto.getString("photo_reference");
                placeDetail.photos.add(photo);
            }
        }
        listener.onGetPlaceDetailSuccess(placeDetail);
    }
}
