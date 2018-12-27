package com.example.administrator.travel.views.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.fragments.ChatFragment;
import com.example.administrator.travel.views.fragments.ChatManagerFragment;
import com.example.administrator.travel.views.fragments.NewsFeedFragment;
import com.example.administrator.travel.views.fragments.SelectMyTourFragment;
import com.example.administrator.travel.views.fragments.SettingFragment;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
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
                case R.id.navigation_chat:
                    if(i==2)
                        return false;
                    i=2;
                    ChatManagerFragment chatManagerFragment = new ChatManagerFragment();
                    android.app.FragmentManager manager2 = getFragmentManager();
                    manager2.beginTransaction().replace(R.id.contenLayout,chatManagerFragment,chatManagerFragment.getTag()).commit();
                    return true;
                case R.id.navigation_setting:
                    if(i==3)
                        return false;
                    i=3;
                    SettingFragment f3 = new SettingFragment();
                    android.app.FragmentManager manager3= getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contenLayout,f3,f3.getTag()).commit();
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
////        String key;
////
//        List<String> list = new ArrayList<>();
//        list.add("-LUUOokVT7stF_hU6wX0");//da nang
//        list.add("-LUUOolXsD_6DTjuNqcg");// quang nam
//
//        userRef.child("-LQ2GIaiHuH4LmUVogi5").setValue(new Tour("DU LỊCH [ĐÓN NĂM MỚI 2019] ĐÀ NẴNG - HỘI AN - SUỐI KHOÁNG NÓNG NÚI THẦN TÀI - BÀ NÀ - CẦU VÀNG ",
//                "",
//                3,2,"Hàng không Vietjet",(float)4.5,23,true,
//                5929000,4190000,2450000,"-LUUOokQ42CGLHozCxRS",list,"uXrG8vuOubONtgINnqDHs3j6KtY2"));
//        list.clear();
//        list.add("-LUUOokWseZaVo9Ks6eu");
//        userRef.child("-LQ2GIaxsDSHdPaQ4cWO").setValue(new Tour("DU LỊCH [CHĂM SÓC SỨC KHỎE] NHA TRANG - GM DỐC LẾT",
//                "",
//                3,3,"xe lửa",(float)4,67, true,
//                3979000,3979000,1989000,"-LUUOokQ42CGLHozCxRS",list,"uXrG8vuOubONtgINnqDHs3j6KtY2"));
//      //  key= userRef.push().getKey();
//        list.clear();
//        list.add("-LUUOolRs_xlNRxSuM76");
//        userRef.child("-LQ2GIay5dB67r0BTjjy").setValue(new Tour("DU LỊCH PHÚ QUỐC - BÃI SAO [VIETJET]",
//                "",
//                3,2,"Vietjet Aviation",(float)3, 8,true,
//                3729000,2679000,1640000,"-LUUOokQ42CGLHozCxRS",list,"uXrG8vuOubONtgINnqDHs3j6KtY2"));
//
//       DatabaseReference tourStart = database.getReference("tour_start_date");
//        DatabaseReference tourBooking = database.getReference("tour_booking");
////        TourBooking tourBooking = new TourBooking("-LELTicEhxKf9k4i_tHN",tourStart.id, timestampNow,
////                numberofAdult, numberofChildren, numberofBaby,money);
//        tourStart.removeValue();
//        tourBooking.removeValue();
//        key=tourStart.push().getKey();
//        tourStart.child("-LTmGpSopuUhKOxQiujF").setValue(
 //               new TourStartDate(1541824295000L,5929000,4190000,2450000,
 //                       50,0,0,true,"-LQ2GIaiHuH4LmUVogi5"));
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELM0FvJODmxnoRhFiG").setValue(
////                    new TourBooking("-LELM0FvJODmxnoRhFiG",key,ServerValue.TIMESTAMP,1,1,0,  0));
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELTicEhxKf9k4i_tHN").setValue(
////                    new TourBooking("-LELTicEhxKf9k4i_tHN",key,ServerValue.TIMESTAMP,1,0,0,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(7);
//
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LTmGpSqQTY0yNgtP9GE").setValue(
//                new TourStartDate(1544502695000L,5929000,4190000,2450000,
//                        50,0,0,true,"-LQ2GIaiHuH4LmUVogi5"));
//
////            tourBooking.child("-LQ2GIaiHuH4LmUVogi5").child(key+ServerValue.TIMESTAMP+"-LELN8HiQSDzjnwHaPkH").setValue(
////                    new TourBooking("-LELN8HiQSDzjnwHaPkH",key, ServerValue.TIMESTAMP,2,0,1,0));
//            tourStart.child("-LQ2GIaiHuH4LmUVogi5").child(key).child("peopleBooking").setValue(3);
//
//        key=tourStart.push().getKey();
//        tourStart.child("-LTmGpSw8ZDzveDE4Nmo").setValue(
//                new TourStartDate(1545539495000L,5929000,4190000,2450000,
//                        50,0,0,true,"-LQ2GIaiHuH4LmUVogi5"));
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

//        DatabaseReference companyRef = database.getReference("companies");
//        companyRef.child("uXrG8vuOubONtgINnqDHs3j6KtY2").setValue(new Company("Công ty du lịch Viettravel",
//                "38 668 999","https://www.vietravel.com/",new MyLatLng(10.7808237,106.6956983),
//                "190 Pasteur, P.6, Q.3, Tp. Hồ Chí Minh, Việt Nam"));

//        DatabaseReference placetypeRef = database.getReference("placetypes");
//        String typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("atm","ATM"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("bar","Bar"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("cafe","Cafe"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("casino","Casino"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("church","Nhà thờ"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("convenience_store","Cửa hàng tiện lợi"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("hospital","Bệnh viện"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("park","Công viên"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("pharmacy","Hiệu thuốc"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("police","Trụ sở công an"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("restaurant","Nhà hàng"));
//        typekey=placetypeRef.push().getKey();
//        placetypeRef.child(typekey).setValue(new NearbyType("supermarket","Siêu thị"));

//            DatabaseReference cityRef = database.getReference("cities");
//            String cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("TP Hồ Chí Minh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hà Nội"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Quảng Bình"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đà Nẵng"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Nha Trang - Khánh Hòa"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đà Lạt - Lâm Đồng"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Huế"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Vũng Tàu"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("An Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bắc Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bắc Kạn"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bắc Ninh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bến Tre"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bình Định"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bình Dương"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bình Phước"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Bình Thuận"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Cà Mau"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Cao Bằng"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đắk Lắk"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đắk Nông"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Điện Biên"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đồng Nai"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Đồng Tháp"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Gia Lai"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hà Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hà Nam"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hà Tĩnh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hải Dương"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hậu Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hòa Bình"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hưng Yên"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Kiên Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Kom Tum"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Lai Châu"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Lạng Sơn"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Lào Cai"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Long An"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Nam Định"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Nghệ An"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Ninh Thuận"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Phú Thọ"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Quảng Nam"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Quảng Ngãi"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Quảng Ninh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Quảng Trị"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Sóc Trăng"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Sơn La"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Tây Ninh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Thái Bình"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Thái Nguyên"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Thanh Hóa"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Tiền Giang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Trà Vinh"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Tuyên Quang"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Vĩnh Long"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Vĩnh Phúc"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Yên Bái"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Phú Yên"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Cần Thơ"));
//            cityKey=cityRef.push().getKey();
//            cityRef.child(cityKey).setValue(new City("Hải Phòng"));

//        DatabaseReference ref = database.getReference("test");
//        String testkey= ref.push().getKey();
//
//        HashMap<String, String> names = new HashMap<>();
//        names.put("John", "John");
//        names.put("Tim", "Tim");
//        names.put("Sam", "Sam");
//        names.put("Ben", "Ben");
//        ref.child(testkey).setValue(names);

//        DatabaseReference ratingRef = database.getReference("rating");
//        DatabaseReference reviewRef = database.getReference("reviews");
//        String ratingKey= ratingRef.push().getKey();
//        ratingRef.child("-LQ2GIaiHuH4LmUVogi5").child(ratingKey).setValue(new Rating(4,"14FjAkNax8S35zBDWEL13yGveyT2"));
//        reviewRef.child("-LQ2GIaiHuH4LmUVogi5").child(ratingKey).setValue(new Review(1544543228000L,3,
//                "giá hợp lý, hướng dẫn viên dễ thương <3","14FjAkNax8S35zBDWEL13yGveyT2"));
//
//        ratingKey= ratingRef.push().getKey();
//        ratingRef.child("-LQ2GIaiHuH4LmUVogi5").child(ratingKey).setValue(new Rating(3,"14FjAkNax8S35zBDWEL13yGveyT2"));
//        reviewRef.child("-LQ2GIaiHuH4LmUVogi5").child(ratingKey).setValue(new Review(1546098428000L,0,
//                "bị giật đồ không vui tí nào :(","BzlTfPkRrKaG3QfUVvg9APlC8zY2"));
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}







