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

    RelativeLayout btnAccept, btnCancel;
    TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice,txtNumberofPeople,
            txtNumberofAdult,txtNumberofChildren,txtNumberofBaby;
    Button btnDecreaseAdult, btnIncreaseAdult,
            btnDecreaseChildren, btnIncreaseChildren,
            btnDecreaseBaby, btnIncreaseBaby;
    BookTourPresenter presenter;
    String tourStartId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tour);
        mapping();

        Bundle bundle = getIntent().getExtras();
        tourStartId = bundle.getString("tourStartId");
        Log.e( "onCreate: ",tourStartId );
        presenter=new BookTourPresenter(this);
        presenter.onViewLoad(tourStartId);
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
    @Override
    public void showPrice(int adultPrice, int childrenPrice, int babyPrice, int availableNumber){
        txtAdultPrice.setText(adultPrice+"");
        txtChildrenPrice.setText(childrenPrice+"");
        txtBabyPrice.setText(babyPrice+"");
        txtNumberofPeople.setText(availableNumber+"");
    }

    @Override
    public void showNumberPeople(int adultNumber, int childrenNumber, int babyNumber) {
        txtNumberofAdult.setText(adultNumber+"");
        txtNumberofChildren.setText(childrenNumber+"");
        txtNumberofBaby.setText(babyNumber+"");
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
    public void disableBtnAccept() {
        btnAccept.setVisibility(View.INVISIBLE);
    }

    @Override
    public void enableBtnAccept() {
        btnAccept.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateAdultNumber(int number) {
        txtNumberofAdult.setText(number+"");
    }

    @Override
    public void updateChildrenNumber(int number) {
        txtNumberofChildren.setText(number+"");
    }

    @Override
    public void updateBabyNumber(int number) {
        txtNumberofBaby.setText(number+"");
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


}
