package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.FormatMoney;
import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.TourBookingAdapter;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.presenters.bases.BookTourPresenter;
import com.example.administrator.travel.presenters.impls.BookTourPresenterImpl;
import com.example.administrator.travel.views.bases.BookTourView;

import java.util.ArrayList;


public class BookTourActivity extends AppCompatActivity implements BookTourView, TextView.OnEditorActionListener, View.OnFocusChangeListener {
    private RecyclerView recyclerViewBooking;
    private Button btnNext;
    private TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice, txtNumberAvailableSlot;
    private EditText etxtNumberOfAdult, etxtNumberOfChildren, etxtNumberOfBaby;
    private BookTourPresenter presenter;
    private TourBookingAdapter tourBookingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tour);
        mapping();
        setOnEditTextStopTyping();
        setOnButtonNextClick();
        Bundle bundle = getIntent().getExtras();
        tourBookingAdapter = new TourBookingAdapter(this);
        recyclerViewBooking.setAdapter(tourBookingAdapter);
        presenter = new BookTourPresenterImpl(this);
        presenter.onViewCreated(bundle);

    }

    private void mapping() {
        txtAdultPrice = findViewById(R.id.txtAdultPrice);
        txtChildrenPrice = findViewById(R.id.txtchildrenPrice);
        txtBabyPrice = findViewById(R.id.txtBabyPrice);
        txtNumberAvailableSlot = findViewById(R.id.txtNumberAvailableSlot);
        etxtNumberOfAdult = findViewById(R.id.etxtNumberOfAdult);
        etxtNumberOfChildren = findViewById(R.id.etxtNumberOfChildren);
        etxtNumberOfBaby = findViewById(R.id.etxtNumberOfBaby);
        recyclerViewBooking = findViewById(R.id.recyclerViewBooking);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setOnEditTextStopTyping() {
        etxtNumberOfAdult.setOnEditorActionListener(this);
        etxtNumberOfAdult.setOnFocusChangeListener(this);
        etxtNumberOfChildren.setOnEditorActionListener(this);
        etxtNumberOfChildren.setOnFocusChangeListener(this);
        etxtNumberOfBaby.setOnEditorActionListener(this);
        etxtNumberOfBaby.setOnFocusChangeListener(this);
    }

    private void setOnButtonNextClick() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonNextClicked();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode, resultCode, data);
    }

    @Override
    public void showPrice(int adultPrice, int childrenPrice, int babyPrice) {
        txtAdultPrice.setText(FormatMoney.formatToString(adultPrice)+"đ");
        txtChildrenPrice.setText(FormatMoney.formatToString(childrenPrice)+"đ");
        txtBabyPrice.setText(FormatMoney.formatToString(babyPrice)+"đ");
    }

    @Override
    public void showNumberAvailableSlot(int availableNumber) {
        txtNumberAvailableSlot.setText(String.valueOf(availableNumber));
    }

    @Override
    public void addTourists(int numberOfTourist, int touristType, int price) {
        tourBookingAdapter.addTourists(numberOfTourist, touristType, price);
    }

    @Override
    public void removeTourists(int numberOfTourist, int touristType) {
        tourBookingAdapter.removeTourists(numberOfTourist, touristType);
    }

    @Override
    public void gotoLoginActivity() {
        startActivity(new Intent(BookTourActivity.this, LoginActivity.class));
    }

    @Override
    public void gotoCardAuthorizationActivity(int authorizationCode, ArrayList<TourBookingDetail> tourBookingDetailList,
                                              String tourStartDateId, Integer numberOfAdult, Integer numberOfChildren,
                                              Integer numberOfBaby, Integer money, String ownerId) {
        Intent intent = new Intent(BookTourActivity.this, CardAuthorizationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("bookingDetailList", tourBookingAdapter.getTourists());
        intent.putExtras(bundle);
        intent.putExtra("tourStartId", tourStartDateId);
        intent.putExtra("numberOfAdult", numberOfAdult);
        intent.putExtra("numberOfChildren", numberOfChildren);
        intent.putExtra("numberOfBaby", numberOfBaby);
        intent.putExtra("money", money);
        intent.putExtra("owner", ownerId);
        TourBookingDetail tourBookingDetail = (TourBookingDetail) intent.getParcelableArrayListExtra("bookingDetailList").get(0);
        Log.e("asdasdasdasd: ", tourBookingDetail.touristName+" "+tourBookingDetail.touristEmail
                +" "+tourBookingDetail.dayOfBirth+" "+tourBookingDetail.monthOfBirth+" "+tourBookingDetail.yearOfBirth
                +" "+tourBookingDetail.male+" "+tourBookingDetail.price+ " "+tourBookingDetail.touristType );
        startActivityForResult(intent, authorizationCode);
    }

    @Override
    public void showButtonNext() {
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtonNext() {
        btnNext.setVisibility(View.GONE);
    }


    @Override
    public void notify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close() {
        finish();
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent != null &&
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                switch (textView.getId()) {
                    case R.id.etxtNumberOfAdult:
                        presenter.onEtxtNumberOfAdultTypingStoped(Integer.parseInt(etxtNumberOfAdult.getText().toString()));
                        break;
                    case R.id.etxtNumberOfChildren:
                        presenter.onEtxtNumberOfChildrenTypingStoped(Integer.parseInt(etxtNumberOfChildren.getText().toString()));
                        break;
                    case R.id.etxtNumberOfBaby:
                        presenter.onEtxtNumberOfBabyTypingStoped(Integer.parseInt(etxtNumberOfBaby.getText().toString()));
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            switch (view.getId()) {
                case R.id.etxtNumberOfAdult:
                    presenter.onEtxtNumberOfAdultTypingStoped(Integer.parseInt(etxtNumberOfAdult.getText().toString()));
                    break;
                case R.id.etxtNumberOfChildren:
                    presenter.onEtxtNumberOfChildrenTypingStoped(Integer.parseInt(etxtNumberOfChildren.getText().toString()));
                    break;
                case R.id.etxtNumberOfBaby:
                    presenter.onEtxtNumberOfBabyTypingStoped(Integer.parseInt(etxtNumberOfBaby.getText().toString()));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public TourBookingAdapter getBookingAdapter() {
        return tourBookingAdapter;
    }
}
