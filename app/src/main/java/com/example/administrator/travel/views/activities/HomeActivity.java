package com.example.administrator.travel.views.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.views.HomeView;
import com.example.administrator.travel.views.fragments.NewsFeedFragment;
import com.example.administrator.travel.views.fragments.SelectMyTourFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    NewsFeedFragment f = new NewsFeedFragment();
                    android.app.FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenLayout,f,f.getTag()).commit();
                    return true;

                case R.id.navigation_my_tours:
                    SelectMyTourFragment f1 = new SelectMyTourFragment();
                    android.app.FragmentManager manager1= getFragmentManager();
                    manager1.beginTransaction().replace(R.id.contenLayout,f1,f1.getTag()).commit();
                    return true;
                case R.id.navigation_more:

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("tours");
        String key;

//        userRef.child("-LQ2GIaiHuH4LmUVogi5").setValue(new Tour("DU LỊCH [ĐÓN NĂM MỚI 2019] ĐÀ NẴNG - HỘI AN - SUỐI KHOÁNG NÓNG NÚI THẦN TÀI - BÀ NÀ - CẦU VÀNG ",
//                "",
//                3,2,"Hàng không Vietjet",(float)4.5,23,true,
//                5929000,4190000,2450000,"-LELM0FvJODmxnoRhFiG"));
//
//        userRef.child("-LQ2GIaxsDSHdPaQ4cWO").setValue(new Tour("DU LỊCH [CHĂM SÓC SỨC KHỎE] NHA TRANG - GM DỐC LẾT",
//                "",
//                3,3,"xe lửa",(float)4,67, true,
//                3979000,3979000,1989000,"-LELTicEhxKf9k4i_tHN"));
//        key= userRef.push().getKey();
//        userRef.child("-LQ2GIay5dB67r0BTjjy").setValue(new Tour("DU LỊCH PHÚ QUỐC - BÃI SAO [VIETJET]",
//                "",
//                3,2,"Vietjet Aviation",(float)3, 8,true,
//                3729000,2679000,1640000,"-LELTicEhxKf9k4i_tHN"));


    }


}







