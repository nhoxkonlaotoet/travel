package com.example.administrator.travel.views.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PreLoadingActivity extends AppCompatActivity {
    long startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_loading);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTime = System.nanoTime();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tourRef = database.getReference("version");
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                endTime = System.nanoTime();
                Log.e("time load: ", ((float)(endTime-startTime))/1000000000+"s");
                gotoHome();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    void gotoHome(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
