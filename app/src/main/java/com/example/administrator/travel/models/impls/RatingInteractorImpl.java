package com.example.administrator.travel.models.impls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class RatingInteractorImpl implements RatingInteractor {
    private final static String RATINGS_REF = "ratings";

    @Override
    public void checkRated(String tourId, String userId, final Listener.OnCheckRatedFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF).child(tourId);
        Query query = ratingsRef.orderByChild("ratingPeopleId").equalTo(userId);
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
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF);
        ratingsRef.child(tourId).child(rating.ratingPeopleId).setValue(rating.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference toursRef = database.getReference("tours");
                toursRef.child(tourId).child("ratings").child(rating.ratingPeopleId).setValue(rating.rating);
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
    public void getReview(String tourId, String userId, final Listener.OnGetReviewTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF);
        ratingsRef.child(tourId).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    listener.onGetReviewTourSuccess(null);
                } else {
                    Rating rating = new Rating();
                    rating.id = dataSnapshot.getKey();
                    rating.content = dataSnapshot.child("content").getValue(String.class);
                    rating.numberOfImages = dataSnapshot.child("numberOfImages").getValue(Integer.class);
                    rating.rating = dataSnapshot.child("rating").getValue(Integer.class);
                    rating.ratingPeopleId = dataSnapshot.child("ratingPeopleId").getValue(String.class);
                    rating.ratingTime = dataSnapshot.child("ratingTime").getValue(Long.class);
                    if (!(dataSnapshot.child("likes").getValue().equals("none"))) {
                        rating.likes = (HashMap<String, Boolean>) dataSnapshot.child("likes").getValue();
                    }
                    listener.onGetReviewTourSuccess(rating);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetReviewTourFail(databaseError.toException());
            }
        });
    }

    @Override
    public void getReviews(String tourId, final Listener.OnGetReviewsTourFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF);
        ratingsRef.child(tourId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Rating> lstReview = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Rating rating = new Rating();
                    rating.id = ds.getKey();
                    rating.content = ds.child("content").getValue(String.class);
                    rating.numberOfImages = ds.child("numberOfImages").getValue(Integer.class);
                    rating.rating = ds.child("rating").getValue(Integer.class);
                    rating.ratingPeopleId = ds.child("ratingPeopleId").getValue(String.class);
                    rating.ratingTime = ds.child("ratingTime").getValue(Long.class);
                    if (!(ds.child("likes").getValue().equals("none"))) {
                        rating.likes = (HashMap<String, Boolean>) ds.child("likes").getValue();
                    }
                    lstReview.add(rating);
                }
                listener.onGetReviewsTourSuccess(lstReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onGetReviewsTourFail(databaseError.toException());
            }
        });
    }

    // review ID = reviewER ID
    @Override
    public void reactReview(String tourId, String reviewId, String userId, boolean like) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF);
        ratingsRef.child(tourId).child(reviewId).child("likes").child(userId).setValue(like);

    }

    @Override
    public void removeReactReview(String tourId, String reviewId, String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = database.getReference(RATINGS_REF);
        ratingsRef.child(tourId).child(reviewId).child("likes").child(userId).removeValue();
    }

    @Override
    public byte[] bitmapToBytes(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    @Override
    public void getReviewImage(final int index, final String tourId, String reviewerId, final Listener.OnGetReviewImageFinishedListener listener) {

        final long ONE_MEGABYTE = 1024 * 1024;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference tourRef = storage.getReference().child("reviews/" + tourId + "/" + reviewerId + "/");
        tourRef.child(index + ".png").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetReviewImageSuccess(index, tourId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
