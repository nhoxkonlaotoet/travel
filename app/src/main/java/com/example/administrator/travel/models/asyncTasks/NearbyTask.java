package com.example.administrator.travel.models.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.listeners.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 4/16/2019.
 */

public class NearbyTask extends AsyncTask<Object, String, String> {
    private String googlePlacesData;
    private String url;
    Listener.OnGetNearbyFinishedListener listener;

    public NearbyTask(Listener.OnGetNearbyFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];
        Log.e("nearby request: ", url);
        try {
            googlePlacesData = readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<Nearby> lstNearby;
        NearbyParser parser = new NearbyParser();
        lstNearby = parser.parse(s);
        listener.onGetNearbySuccess(lstNearby, parser.nextPageToken);
    }

    public String readUrl(String myUrl) throws IOException {
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           try {
               inputStream.close();
           }
           catch (Exception e){}
           urlConnection.disconnect();

        }
        return data;
    }


}
