package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.administrator.travel.models.entities.UserInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Henry
 */

public class UpdateInfoAccountInteractorImpl implements UpdateInfoAccountInteractor {

    String AuthID;

    @Override
    public void updateInfoAccountInteractor(final UpdateInfoAccountListener listener, SharedPreferences sharedPreferences, Bitmap bitmap, final UserInformation userInformation) {
        AuthID =  sharedPreferences.getString("AuthID","none");

        java.util.Calendar calendar = java.util.Calendar.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Link storage của mình trên firebase
        StorageReference storageRef  = storage.getReferenceFromUrl ("gs://travel-76809.appspot.com");
        //
        StorageReference mountainsRef = storageRef.child("avatar").child (AuthID).child ("avatar"+calendar.getTimeInMillis ()+".png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Upload Thất bại
                listener.onResultUpdate(0);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //
                Uri downloadUrl= taskSnapshot.getDownloadUrl ();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                //lấy địa chỉ url ảnh mới
                userInformation.setUrlAvatar(downloadUrl+"");

                mDatabase.child ("users").child(AuthID).setValue(userInformation);
                // Upload Thành Công
                listener.onResultUpdate(1);
            }
        });
    }
}
