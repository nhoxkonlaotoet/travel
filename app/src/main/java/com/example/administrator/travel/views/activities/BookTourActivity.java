package com.example.administrator.travel.views.activities;

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
import com.google.firebase.database.ServerValue;
public class BookTourActivity extends AppCompatActivity implements BookTourView {


    RelativeLayout btnAccept, btnCancel;
    TourStartDate tourStart;
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
        tourStart = (TourStartDate) getIntent().getSerializableExtra("tour");
        presenter=new BookTourPresenter(this);
        mapping();
        showPrice();
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
        TourBooking tourBooking = new TourBooking("-LELTicEhxKf9k4i_tHN",numberofAdult, numberofChildren, numberofBaby,
                ServerValue.TIMESTAMP,money);
        presenter.bookTour(tourStart.id, tourBooking);
    }

    @Override
    public void notifyBookingSuccess() {
        Toast.makeText(this, "Bạn đã đặt tour thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyBookingFailure() {
        Toast.makeText(this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        finish();
    }
}
