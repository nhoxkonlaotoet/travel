package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.BookTourPresenter;
import com.example.administrator.travel.views.BookTourView;


public class BookTourActivity extends AppCompatActivity implements BookTourView {

    static final String ACTION_BOOK_TOUR="book tour";
    String action;
    RelativeLayout btnAccept, btnCancel;
    TourStartDate tourStart;
    String tourId,userId;
    TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice,txtNumberofPeople,
            txtNumberofAdult,txtNumberofChildren,txtNumberofBaby;
    Button btnDecreaseAdult, btnIncreaseAdult,
            btnDecreaseChildren, btnIncreaseChildren,
            btnDecreaseBaby, btnIncreaseBaby;
    Integer numberofAdult, numberofChildren, numberofBaby, money;
    BookTourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tour);

        SharedPreferences prefs = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        userId = prefs.getString("AuthID","");
        if(userId.equals(""))
        {
            startActivity((new Intent(this, LoginActivity.class)));
           // finish();
        }
        tourStart = (TourStartDate) getIntent().getSerializableExtra("tour");
        Bundle bundle = getIntent().getExtras();
        tourId=bundle.getString("tourId");
        presenter=new BookTourPresenter(this);
        mapping();
        showPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        userId = prefs.getString("AuthID","");
    }

    public void mapping(){
        btnAccept=findViewById(R.id.btnAccept);
        btnCancel=findViewById(R.id.btnCancel);
        btnDecreaseAdult=findViewById(R.id.btnDecreaseAdult);
        btnIncreaseAdult=findViewById(R.id.btnIncreaseAdult);
        btnDecreaseChildren=findViewById(R.id.btnDecreaseChildren);
        btnIncreaseChildren=findViewById(R.id.btnIncreaseChildren);
        btnDecreaseBaby=findViewById(R.id.btnDecreaseBaby);
        btnIncreaseBaby=findViewById(R.id.btnIncreaseBaby);
        txtAdultPrice=findViewById(R.id.txtAdultPrice);
        txtChildrenPrice=findViewById(R.id.txtchildrenPrice);
        txtBabyPrice=findViewById(R.id.txtBabyPrice);
        txtNumberofPeople=findViewById(R.id.txtNumberofPeople);
        txtNumberofAdult=findViewById(R.id.txtNumberofAdult);
        txtNumberofChildren=findViewById(R.id.txtNumberofChildren);
        txtNumberofBaby=findViewById(R.id.txtNumberofBaby);
    }

    public void showPrice(){
        txtAdultPrice.setText(tourStart.adultPrice.toString());
        txtChildrenPrice.setText(tourStart.childrenPrice.toString());
        txtBabyPrice.setText(tourStart.babyPrice.toString());
        txtNumberofPeople.setText(tourStart.peopleBooking.toString());
        numberofAdult=0;
        numberofChildren=0;
        numberofBaby=0;
        money=0;
    }

    public void btnAccept_Click(View view){
        presenter.onBtnAcceptClicked();
    }

    public void btnCancel_Click(View view){
        presenter.onBtnCancelClicked();
    }

    public void btnDecreaseAdult_Click(View view){
        presenter.onBtnDecreaseAdultClicked();
    }

    public void btnIncreaseAdult_Click(View view){
        presenter.onBtnIncreaseAdultClick();
    }

    public void btnDecreaseChildren_Click(View view){
        presenter.onBtnDecreaseChildrenClicked();
    }

    public void btnIncreaseChildren_Click(View view){
        presenter.onBtnIncreaseChildrenClicked();
    }

    public void btnDecreaseBaby_Click(View view){
        presenter.onBtnDecreaseBabyClicked();
    }

    public void btnIncreaseBaby_Click(View view){
        presenter.onBtnIncreaseBabyClicked();
    }

    @Override
    public void changeNumberOfPeople(String type, Integer number) {
        switch (type) {
            case TYPE_ADULT:
                if(numberofAdult+number<0)
                    return;
                numberofAdult += number;
                txtNumberofAdult.setText(numberofAdult.toString());
                break;
            case TYPE_CHILDREN:
                if(numberofChildren+number<0)
                    return;
                numberofChildren += number;
                txtNumberofChildren.setText(numberofChildren.toString());
                break;
            case TYPE_BABY:
                if(numberofBaby+number<0)
                    return;
                numberofBaby += number;
                txtNumberofBaby.setText(numberofBaby.toString());
                break;
            default: break;
        }
        money = tourStart.adultPrice*numberofAdult + tourStart.childrenPrice*numberofChildren + tourStart.babyPrice*numberofBaby;
    }

    @Override
    public void sendBookingTour(){
        // thời gian hệ thống trả về ở receivedCurrentTime(Long time)
        // hành động đc cài đặt sẽ thực hiện sau khi nhận được thời gian
        action=ACTION_BOOK_TOUR;
        presenter.getCurrentTime();
//
    }

    @Override
    public void notifyBookingSuccess() {
        Toast.makeText(this, "Bạn đã đặt tour thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyBookingFailure(Exception ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        finish();
    }
    @Override
    public void receivedCurrentTime(Long time)
    {
        if(action==null || action.isEmpty())
            return;
        if(action ==ACTION_BOOK_TOUR) {
            money = tourStart.adultPrice * numberofAdult + tourStart.childrenPrice * numberofChildren + tourStart.babyPrice * numberofChildren;
            TourBooking tourBooking = new TourBooking(userId, tourStart.id, time,
                    numberofAdult, numberofChildren, numberofBaby, money *= 0);
            presenter.bookTour(tourId, tourBooking);
        }
    }

    @Override
    public void receivedCurrentTime(Exception ex) {
        Toast.makeText(this, "Không thể cập nhật thời gian hiện tại \r\n"+ex.toString(), Toast.LENGTH_LONG).show();
    }
}
