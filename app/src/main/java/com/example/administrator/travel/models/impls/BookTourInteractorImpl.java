package com.example.administrator.travel.models.impls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.administrator.travel.models.bases.BookTourInteractor;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.models.entities.TourBookingRequest;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.models.retrofit.ApiUtils;
import com.example.administrator.travel.models.retrofit.NoSSLv3SocketFactory;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 5/26/2019.
 */

public class BookTourInteractorImpl implements BookTourInteractor {
    @Override
    public void getBooking(String bookingId, Listener.OnGetBookingFinishedListener listener) {

    }

    @Override
    public void bookTour(final TourBookingRequest tourBookingRequest, final Context context, final Listener.OnBookTourFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean result = null;
                Exception ex = null;
                try {
                    URL url = new URL("https://us-central1-travel-76809.cloudfunctions.net/booking/");
                    //URL url = new URL("https://us-central1-bank-14cd4.cloudfunctions.net/test/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    //     connection.setUseCaches (false);
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    JSONObject jsonParam = tourBookingRequest.toJson();
                    wr.writeBytes(jsonParam.toString());
                    wr.flush();
                    String json_response = "";
                    InputStreamReader in = new InputStreamReader(connection.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    String text = "";
                    while ((text = br.readLine()) != null) {
                        json_response += text;
                    }
                    wr.close();
                    Log.e("run: ", json_response);
                    result = Boolean.valueOf(json_response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("run fail: ", e.getMessage());
                    ex = e;
                }
                final Boolean finalResult = result;
                final Exception[] finalEx = {ex};
                if (context != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalResult != null && finalResult)
                                listener.onBookTourSuccess();
                            else {
                                if (finalEx[0] == null)
                                    finalEx[0] = new Exception("Không thể thanh toán");
                                listener.onBookTourFail(finalEx[0]);
                            }
                        }

                    });
                }
            }
        }).start();


    }
}
