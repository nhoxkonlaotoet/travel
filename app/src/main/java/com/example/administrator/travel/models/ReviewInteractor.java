package com.example.administrator.travel.models;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.administrator.travel.models.entities.Rating;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public class ReviewInteractor {
    public void checkRated(String tourId, Context context, final OnGetRatingFinishedListener listener)
    {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String userId = prefs.getString("AuthID","");
        if(!userId.equals("none")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference rateRef = database.getReference("rating").child(tourId);
            Query query = rateRef.orderByChild("ratingPeopleId").equalTo(userId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()>0)
                        listener.onCheckRatedSuccess(true);
                    else
                        listener.onCheckRatedSuccess(false);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
            listener.onCheckRatedFailure(new Exception("Bạn chưa đăng nhập"));
    }

    public void rate(final String tourId, final Rating rating, final List<Bitmap> lstImage, final OnGetRatingFinishedListener listener){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference timeRef = database.getReference("SystemTime");
        timeRef.setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                timeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference ratingRef = database.getReference("rating");
                        ratingRef.child(tourId).child(rating.ratingPeopleId).setValue(rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(lstImage!=null && lstImage.size()!=0){
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference ratingStorageRef = storage.getReference().child("reviews/")
                                            .child(tourId).child(rating.ratingPeopleId);
                                    for(int i=0;i<lstImage.size();i++)
                                    {
                                        UploadTask uploadTask= ratingStorageRef.child(i+".png").putBytes(bitmapToBytes(lstImage.get(i)));
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                listener.onRateSuccess();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                listener.onRateFailure(e);
                                            }
                                        });
                                    }
                                }
                                else
                                    listener.onRateSuccess();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onRateFailure(e);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onRateFailure(databaseError.toException());

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onRateFailure(e);
            }
        });




    }
    public byte[] bitmapToBytes(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    public void getRating(String tourId, final OnGetRatingFinishedListener listener){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingRef = database.getReference("rating");
        ratingRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int rating=0;
                long numberofRating = dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    rating+= ds.child("rating").getValue(Integer.class);
                }
                float r=0;
                if(numberofRating>0)
                    r =((float)rating)/numberofRating;
                listener.onGetRatingSuccess(r,numberofRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetRatingFailure(databaseError.toException());
            }
        });
    }
    public void getReviews(String tourId, final OnGetRatingFinishedListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewsRef = database.getReference("rating");
        reviewsRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Rating> lstReview = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Rating rating = ds.getValue(Rating.class);
                    rating.id=ds.getKey();
                    lstReview.add(rating);

                }
                listener.onGetReviewsSuccess(lstReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetReviewsFailure(databaseError.toException());
            }
        });
    }
}
