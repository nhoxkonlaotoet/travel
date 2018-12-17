package com.example.administrator.travel.views.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.views.fragments.ChatFragment;
import com.example.administrator.travel.views.fragments.ChatManagerFragment;
import com.example.administrator.travel.views.fragments.NewsFeedFragment;
import com.example.administrator.travel.views.fragments.SelectMyTourFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



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
                case R.id.navigation_chat:
                    if(i==2)
                        return false;
                    i=2;
                    ChatManagerFragment chatManagerFragment = new ChatManagerFragment();
                    android.app.FragmentManager manager2 = getFragmentManager();
                    manager2.beginTransaction().replace(R.id.contenLayout,chatManagerFragment,chatManagerFragment.getTag()).commit();
                    return true;
                case R.id.navigation_more:
                    if(i==3)
                        return false;
                    i=3;
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


        //init database


            FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userRef = database.getReference("tours");
//        String key;
//
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
//
//        DatabaseReference tourStart = database.getReference("tour_start_date");
//        DatabaseReference tourBooking = database.getReference("tour_booking");
////        TourBooking tourBooking = new TourBooking("-LELTicEhxKf9k4i_tHN",tourStart.id, timestampNow,
////                numberofAdult, numberofChildren, numberofBaby,money);
//        tourStart.removeValue();
//        tourBooking.removeValue();
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate(1541824295000L,5929000,4190000,2450000,
//                        50,0,0,true));
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELM0FvJODmxnoRhFiG").setValue(
////                    new TourBooking("-LELM0FvJODmxnoRhFiG",key,ServerValue.TIMESTAMP,1,1,0,  0));
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELTicEhxKf9k4i_tHN").setValue(
////                    new TourBooking("-LELTicEhxKf9k4i_tHN",key,ServerValue.TIMESTAMP,1,0,0,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(7);
//
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate(1544502695000L,5929000,4190000,2450000,
//                        50,0,0,true));
//
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELN8HiQSDzjnwHaPkH").setValue(
////                    new TourBooking("-LELN8HiQSDzjnwHaPkH",key, ServerValue.TIMESTAMP,2,0,1,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(3);
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).setValue(
//                new TourStartDate(1545539495000L,5929000,4190000,2450000,
//                        50,0,0,true));
//        DatabaseReference daytopicRef = database.getReference("days");
//        DatabaseReference scheduleRef = database.getReference("schedules");
//        String keyDay = daytopicRef.push().getKey();
//        daytopicRef.child("-LQ2GIaiHuH4LmUVogi5").child(keyDay).setValue(new Day(1,"TP. HỒ CHÍ MINH - ĐÀ NẴNG (Ăn trưa, chiều)"));
//        String keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"5:30","Bay đi Đà Nẵng",
//                        "Buổi sáng, quý khách tập trung tại cổng D4, Ga đi trong nước, sân bay Tân Sơn Nhất. Lên chuyến bay VJ622 lúc 06:15 đi Đà Nẵng",
//                        new MyLatLng(10.8177103,106.6585133)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"9:00","Tham quan bán đảo Sơn Trà",
//                        "Đến Đà Nẵng, tham quan bán đảo Sơn Trà, ngắm cảng Tiên Sa",
//                        new MyLatLng(16.0513156,108.1737577)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"","Tham quan bán đảo Sơn Trà",
//                        "Viếng chùa Linh Ứng Bãi Bụt, chiêm bái tượng Phật Quan Thế Âm",
//                        new MyLatLng(16.1002935,108.275667)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"","Tham quan bán đảo Sơn Trà",
//                        "Tham quan thắng cảnh Ngũ Hành Sơn",
//                        new MyLatLng(16.0045181,108.2595895)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"","Tham quan bán đảo Sơn Trà",
//                        "Tham quan làng điêu khắc đá Hòa Hải",
//                        new MyLatLng(  15.9989193,108.2590161)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"19:00","Tham quan tự túc",
//                        "Tự do dạo phố, ngắm Cầu Rồng",
//                        new MyLatLng(  16.0610422,108.2257837)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"","Tham quan tự túc",
//                        "cầu Trần Thị Lý",
//                        new MyLatLng(  16.0501961,108.2266065)));
//        keySchedule = scheduleRef.push().getKey();
//        scheduleRef.child("-LQ2GIaiHuH4LmUVogi5").child(keySchedule).setValue(
//                new Schedule(keyDay,"","Tham quan tự túc",
//                        "trải nghiệm \"Vòng quay mặt trời\"",
//                        new MyLatLng(  16.0404923,108.2278089)));



    }


}







