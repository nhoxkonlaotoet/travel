package com.example.administrator.travel.models.impls;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class RatingInteractorImpl implements RatingInteractor {
    @Override
    public void getRating(String tourId, final Listener.OnGetRatingTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rateRef = database.getReference("ratings");
        rateRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float value = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    float rating = snapshot.child("rating").getValue(Float.class);
                    value += rating;
                }
                if(dataSnapshot.getChildrenCount()>0)
                    value/=dataSnapshot.getChildrenCount();
                listener.onGetRatingTourSuccess(value,dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetRatingTourFail(databaseError.toException());
            }
        });
    }

    @Override
    public void checkRated(String tourId, String userId, final Listener.OnCheckRatedFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rateRef = database.getReference("ratings").child(tourId);
        Query query = rateRef.orderByChild("ratingPeopleId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0)
                    listener.onCheckRatedTrue();
                else
                    listener.onCheckRatedFalse();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCheckRatedError(databaseError.toException());
            }
        });
    }

    @Override
    public void rateTour(final String tourId, final Rating rating, final List<Bitmap> imageList, final Listener.OnRateTourFinishedListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingRef = database.getReference("ratings");
        ratingRef.child(tourId).child(rating.ratingPeopleId).setValue(rating.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (imageList != null && imageList.size() != 0) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference ratingStorageRef = storage.getReference().child("reviews/")
                            .child(tourId).child(rating.ratingPeopleId);
                    for (int i = 0; i < imageList.size(); i++) {
                        UploadTask uploadTask = ratingStorageRef.child(i + ".png").putBytes(bitmapToBytes(imageList.get(i)));
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onRateTourFail(e);
                            }
                        });
                    }
                    listener.onRateTourSuccess();
                } else
                    listener.onRateTourSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onRateTourFail(e);
            }
        });
    }


    @Override
    public void getReviews(String tourId, final Listener.OnGetReviewsTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewsRef = database.getReference("ratings");
        reviewsRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Rating> lstReview = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Rating rating = ds.getValue(Rating.class);
                    rating.id = ds.getKey();
                    lstReview.add(rating);
                }
                listener.onGetReviewTourSuccess(lstReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetReviewTourFail(databaseError.toException());
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
