package com.example.administrator.travel.models.impls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.administrator.travel.models.bases.BankInteractor;
import com.example.administrator.travel.models.entities.BankCard;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.models.retrofit.ApiUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 6/10/2019.
 */

public class BankInteractorImpl implements BankInteractor {
    @Override
    public void checkValidCard(final BankCard bankCard, final Context context, final Listener.OnCheckValidCardFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean result = null;
                Exception ex = null;
                try {
                    URL url = new URL("https://us-central1-bank-14cd4.cloudfunctions.net/validCard/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    //     connection.setUseCaches (false);
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    JSONObject jsonParam = bankCard.toJson();
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
                    result = Boolean.valueOf(json_response.trim());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("run fail: ", e.getMessage());
                    ex = e;
                }
                final Boolean finalResult = result;
                final Exception finalEx = ex;
                if (context != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalResult != null) {
                                listener.onCheckValidCardSuccess(finalResult);
                            } else
                                listener.onCheckValidCardFail(finalEx);
                        }
                    });
                }
            }
        }).start();

    }
}
