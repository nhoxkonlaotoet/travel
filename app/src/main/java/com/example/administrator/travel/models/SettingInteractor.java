package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public void getShareLoction(OnGetShareLocationListenter listenter){
        try {
            String userId = prefs.getString("AuthID", "");
            boolean shareLocation = prefs.getBoolean("shareLocation" + userId, false);
            listenter.onGetShareLocationSuccess(shareLocation);
        }
        catch (Exception ex){
            listenter.onGetShareLocationFailure(ex);
        }

    }
    public void setShareLocation(boolean checked,OnSetShareLocationFinishedListener listener){
        try {
            String userId = prefs.getString("AuthID", "");
            if(!userId.equals("none")) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("shareLocation"+userId, checked);
                editor.apply();
                String tourStartId = prefs.getString("participatingTourStart" + userId, "");
                if(!tourStartId.equals("")) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference shareLocationRef = database.getReference("participants")
                            .child(tourStartId + "+" + userId).child("shareLocation");
                    shareLocationRef.setValue(checked);
                }
                listener.onTurnOnShareLocationSuccess();
            }
            else
                listener.onTurnOnShareLocationFailrue(new Exception("Bạn chưa đăng nhập"));
        }
        catch (Exception ex){
            listener.onTurnOnShareLocationFailrue(ex);
        }
    }
}
