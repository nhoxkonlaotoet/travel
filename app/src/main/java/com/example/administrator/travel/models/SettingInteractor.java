package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 22/12/2018.
 */

public class SettingInteractor {
    SharedPreferences prefs;
    public SettingInteractor(){}
    public void getUserId(OnGetUserIdFinishedListener listener, Context context)
    {
        try {
            prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
            String userId = prefs.getString("AuthID", "");
            Log.e( "getUserId", userId);
            if(userId.equals("none"))
                listener.onGetUserIdFailure(new Exception("Không có dữ liệu"));
            else
                listener.onGetUserIdSuccess(userId);
        }
        catch (Exception ex){
            listener.onGetUserIdFailure(ex);
        }
    }
    public void logout(OnUserLogoutFinishedListener listener, Context context)
    {
        try {
           // prefs =context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("AuthID", "none");
            editor.apply();
            String userId = prefs.getString("AuthID", "");
            Log.e("logout: ",userId );
            listener.onLogoutSuccess();
        }
        catch (Exception ex){
            listener.onLogoutFailure(ex);
        }
    }
}
