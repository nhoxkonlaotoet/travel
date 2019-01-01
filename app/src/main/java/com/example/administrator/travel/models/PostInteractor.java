package com.example.administrator.travel.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.os.Environment;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.OnPostActivityFinishedListener;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 31/12/2018.
 */

public class PostInteractor {
    public void loadExternalPictures(OnLoadImageFinishedListener listener) {
        int i = 0;
        try {
            File storage = new File(Environment.getExternalStorageDirectory() + "/DCIM/100ANDRO/");
            for (File f : storage.listFiles()) {
                new LoadImageTask(listener).execute(i + "", f.getAbsolutePath());
                i++;
            }
            listener.onGetImageCoutnSuccess(storage.listFiles().length);
        }
        catch (Exception ex){
            listener.onGetImageCountFailure(ex);
        }
    }
    public void postActivity(final String tourStartId, final boolean focus, final String content, final List<Bitmap> listImage, Location location, Context context, final OnPostActivityFinishedListener listener){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(context!=null) {
            SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
            final String userId = prefs.getString("AuthID", "");
            Log.e( "1: ", "___________________________________________________");
            if(!userId.equals("none")) {
                final MyLatLng latLng;
                if(location!=null)
                    latLng= new MyLatLng(location.getLatitude(),location.getLongitude());
                else 
                    latLng=null;
                final DatabaseReference timeRef = database.getReference("SystemTime");
                timeRef.setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e( "2: ", "___________________________________________________");

                        timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e( "3: ", "___________________________________________________");

                                int n = 0;
                                if(listImage!=null)
                                    n=listImage.size();
                                Long time = dataSnapshot.getValue(Long.class);
                                DatabaseReference ref = database.getReference("activities");
                                final String key = ref.push().getKey();
                                Activity activity = new Activity(userId,focus,time,content,latLng,n);
                                ref.child(tourStartId).child(key).setValue(activity).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.e( "4: ", "___________________________________________________");

                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageref = storage.getReference().child("activities/")
                                                .child(tourStartId).child(key);
                                        for(int i=0;i<listImage.size();i++)
                                        {
                                            UploadTask uploadTask= storageref.child(i+".png").putBytes(bitmapToBytes(listImage.get(i)));
                                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Log.e( "5: ", "___________________________________________________");
                                                    listener.onPostSuccess();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    listener.onPostFailure(e);
                                                }
                                            });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        listener.onPostFailure(e);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                listener.onPostFailure(databaseError.toException());

                            }
                        });
                    }
                });
               
            }
            else
                listener.onPostFailure(new Exception(""));
        }
        else
            listener.onPostFailure(new Exception(""));

    }
    public byte[] bitmapToBytes(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}
