package com.example.administrator.travel.views.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.fragments.NewsFeedFragment;
import com.example.administrator.travel.views.fragments.SelectMyTourFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Integer i=-1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(i==0)
                        return false;
                    i=0;
                    NewsFeedFragment f = new NewsFeedFragment();
                    android.app.FragmentManager manager= getFragmentManager();
                    manager.beginTransaction().replace(R.id.contenLayout,f,f.getTag()).commit();
                    return true;

                case R.id.navigation_my_tours:
                    if(i==1)
                        return false;
                    i=1;
                    SelectMyTourFragment f1 = new SelectMyTourFragment();
                    android.app.FragmentManager manager1= getFragmentManager();
                    manager1.beginTransaction().replace(R.id.contenLayout,f1,f1.getTag()).commit();
                    return true;
                case R.id.navigation_more:
                    if(i==2)
                        return false;
                    i=2;
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
        navigation.setSelectedItemId(R.id.navigation_home);

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

//        DatabaseReference tourStart = database.getReference("tour_start_date");
//        DatabaseReference tourBooking = database.getReference("tour_booking");
//
//        tourStart.removeValue();
//        tourBooking.removeValue();
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate("1/12/2018",5929000,4190000,2450000,
//                        50,0,0,true));
//            String childKey = tourBooking.push().getKey();
//            tourBooking.child(key).child(childKey).setValue(
//                    new TourBooking("-LELM0FvJODmxnoRhFiG",5,1,0, ServerValue.TIMESTAMP, 0));
//            childKey=tourBooking.push().getKey();
//            tourBooking.child(key).child(childKey).setValue(
//                    new TourBooking("-LELTicEhxKf9k4i_tHN",1,0,0, ServerValue.TIMESTAMP,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(7);
//
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate("10/12/2018",5929000,4190000,2450000,
//                        50,0,0,true));
//            childKey=tourBooking.push().getKey();
//            tourBooking.child(key).child(childKey).setValue(
//                    new TourBooking("-LELN8HiQSDzjnwHaPkH",2,0,1, ServerValue.TIMESTAMP,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(3);
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate("20/12/2018",5929000,4190000,2450000,
//                        50,0,0,true));




    }


}







