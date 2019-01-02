package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.entities.MyLatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourInteractor {
    SharedPreferences  prefs;
    public void getImages(final String tourId, final OnGetTourImagesFinishedListener listener)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbtourRef = database.getReference().child("tours").child(tourId).child("numberofImages");
        dbtourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Integer number = dataSnapshot.getValue(Integer.class)-1;
                Log.e("Value is: ", number + "");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference tourRef = storage.getReference().child("tours/"+tourId+"/");
                final Bitmap[] bitmaps = new Bitmap[number];
                final long ONE_MEGABYTE = 1024 * 1024;

                final Boolean[] downloadedFlags = new Boolean[number];
                for(int i=0;i<number;i++)
                    downloadedFlags[i]=false;

                for(int i=0;i<number;i++){
                    final Integer j = i;
                    tourRef.child(i + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            bitmaps[j]=bmp;
                            downloadedFlags[j]=true;
                            Log.e("onSuccess images: ", (bitmaps[j]==null)+ "");

                            for(int k=0;k<number;k++)
                                if(downloadedFlags[k]==false)
                                    return;
                            Log.e("finish: ", "ok");
                            listener.onSuccess(bitmaps,number);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listener.onFailure();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }
    public void setTourFinishListener(Context context, final OnTourFinishedListener listener)
    {
        final SharedPreferences prefs =context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        final String userId = prefs.getString("AuthID", "");
        if(!userId.equals("none")){
            String tourStartId = prefs.getString("participatingTourStart" + userId, "");
            if(!tourStartId.equals("")){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference tourFinishRef = database.getReference("tour_start_date")
                        .child(tourStartId).child("finished");
                tourFinishRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean isFinished = dataSnapshot.getValue(Boolean.class);
                        if(isFinished) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("participatingTourStart"+userId, "");
                            editor.putString("participatingTour"+userId, "");
                            editor.apply();
                            listener.onTourFinished();

                        }
                        Log.e("listenr tour finish: ", isFinished+"");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public boolean isShareLocation(Context context){
        prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID","");
        if(!userId.equals("none")) {
            return prefs.getBoolean("shareLocation" + userId,false);

        }
        return false;
    }
    public void updateMyLocation(String tourStartId, double lat, double lng){
        String userId = prefs.getString("AuthID","");
        if(!userId.equals("none")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("participants").child(tourStartId+"+"+userId).child("latLng");
            ref.setValue(new MyLatLng(lat,lng));

        }
    }
}
