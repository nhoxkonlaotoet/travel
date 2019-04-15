package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.listeners.Listener;
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
import java.util.List;

/**
 * Created by Admin on 4/15/2019.
 */

public class ActivityInteractorImpl implements ActivityInteractor {
    final static String ACTIVITIES_REF = "activities";

    @Override
    public void postActivity(final String tourStartId, String userId, Activity activity, final List<Bitmap> listImage, final Listener.OnPostActivityFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(ACTIVITIES_REF);
        final String key = ref.push().getKey();
        ref.child(tourStartId).child(key).setValue(activity.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageref = storage.getReference().child("activities/")
                        .child(tourStartId).child(key);
                if(listImage==null||listImage.size()==0)
                {
                    listener.onPostActivitySuccess();
                    return;
                }
                final boolean[] flags = new boolean[listImage.size()];
                for (int i = 0; i < listImage.size(); i++) {
                    UploadTask uploadTask = storageref.child(i + ".png").putBytes(bitmapToBytes(listImage.get(i)));
                    final int finalI = i;
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            flags[finalI] = true;
                            for (int j = 0; j < listImage.size(); j++)
                                if (!flags[j])
                                    return;
                            listener.onPostActivitySuccess();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onPostActivityFail(e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onPostActivityFail(e);
            }
        });
    }

    @Override
    public byte[] bitmapToBytes(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }
}
