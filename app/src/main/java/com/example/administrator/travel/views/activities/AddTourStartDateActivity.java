package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.FormatMoney;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.AddTourStartDatePresenter;
import com.example.administrator.travel.presenters.impls.AddTourStartDatePresenterImpl;
import com.example.administrator.travel.views.bases.AddTourStartDateView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class AddTourStartDateActivity extends AppCompatActivity implements AddTourStartDateView {
    MCalendarView calendarTourStart;
    TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice;
    EditText etxtNumberOfAdult, etxtNumberOfChildren, etxtNumberOfBaby;
    Button btnAddTourStart;
    private Toolbar toolbar;

    AddTourStartDatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour_start_date);
        mapping();
        setActionbar();
        setOnButtonAddClick();
        setOnCalendarChange();
        for (int i = 0; i < calendarTourStart.getMarkedDates().getAll().size(); i++) {
            calendarTourStart.unMarkDate(calendarTourStart.getMarkedDates().getAll().get(i));
        }
        presenter = new AddTourStartDatePresenterImpl(this);
        presenter.onViewCreated(getIntent().getExtras());
    }

    private void mapping() {
        calendarTourStart = findViewById(R.id.calendarTourStart);
        txtAdultPrice = findViewById(R.id.txtAdultPrice);
        txtChildrenPrice = findViewById(R.id.txtChildrenPrice);
        txtBabyPrice = findViewById(R.id.txtBabyPrice);
        etxtNumberOfAdult = findViewById(R.id.etxtNumberOfAdult);
        etxtNumberOfChildren = findViewById(R.id.etxtNumberOfChildren);
        etxtNumberOfBaby = findViewById(R.id.etxtNumberOfBaby);
        btnAddTourStart = findViewById(R.id.btnAddTourStart);
        toolbar = findViewById(R.id.toolbar);

    }

    private void setActionbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private void setOnButtonAddClick() {
        btnAddTourStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonAddTourStartDateClick();
            }
        });
    }
    @Override
    public List<DateData> getListDate(){
        return calendarTourStart.getMarkedDates().getAll();
    }
    private void setOnCalendarChange() {
        calendarTourStart.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                try {

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date tempDate = dateFormat.parse(date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
                    Long time = tempDate.getTime();
                    presenter.onDateClick(time, date.getYear(), date.getMonth(), date.getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void showPrices(int adultPrice, int childrenPrice, int babyPrice) {
        txtAdultPrice.setText(FormatMoney.formatToString(adultPrice) + "đ");
        txtChildrenPrice.setText(FormatMoney.formatToString(childrenPrice) + "đ");
        txtBabyPrice.setText(FormatMoney.formatToString(babyPrice) + "đ");
    }

    @Override
    public void showExistDate(List<TourStartDate> tourStartDateList) {
        for (TourStartDate tourStartDate : tourStartDateList) {
            Date date = new Date(tourStartDate.startDate);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = dateFormat.format(date);
            int day = Integer.parseInt(dateString.substring(0, 2));
            int month = Integer.parseInt(dateString.substring(3, 5));
            int year = Integer.parseInt(dateString.substring(6, 10));
            calendarTourStart.markDate(new DateData(year, month, day).setMarkStyle(new MarkStyle(MarkStyle.DEFAULT, Color.RED)));
        }
    }

    @Override
    public String getEditTextNumberOfAdultValue() {
        return etxtNumberOfAdult.getText().toString();
    }

    @Override
    public String getEditTextNumberOfChildrenValue() {
        return etxtNumberOfChildren.getText().toString();
    }

    @Override
    public String getEditTextNumberOfBabyValue() {
        return etxtNumberOfBaby.getText().toString();
    }

    @Override
    public void removeDate(int year, int month, int day) {
        calendarTourStart.unMarkDate(year, month, day);
    }

    @Override
    public void addDate(int year, int month, int day) {
        calendarTourStart.markDate(year, month, day);
    }

    @Override
    public void notify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
