package com.example.administrator.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourBookingDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 6/6/2019.
 */

public class TourBookingAdapter extends RecyclerView.Adapter<TourBookingAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private RecyclerView parent;
    private ArrayList<TourBookingDetail> tourBookingDetailList;

    private String[] yearArr = new String[151];
    private String[] monthArr = new String[12];
    private String[] dayArr = new String[31];
    private ArrayAdapter<String> dayArrayAdapter, monthArrayAdapter, yearArrayAdapter;

    public TourBookingAdapter(Context context) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        tourBookingDetailList = new ArrayList<>();
        for (int i = 0; i < yearArr.length; i++) {
            yearArr[i] = String.valueOf(2019 - i);
            if (i < 31) {
                dayArr[i] = String.valueOf(i+1);
                if (i < 12)
                    monthArr[i] = String.valueOf(i+1);
            }
        }

        dayArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, dayArr);
        monthArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, monthArr);
       yearArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, yearArr);
    }

    public TourBookingAdapter(Context context, List<TourBookingDetail> tourBookingDetailList) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        this.tourBookingDetailList = (ArrayList<TourBookingDetail>) tourBookingDetailList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_book_tour, parent, false);
        return new TourBookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TourBookingDetail tourBookingDetail = tourBookingDetailList.get(position);
        holder.txtTouristNo.setText("HÀNH KHÁCH " + (position + 1));
        if (tourBookingDetail.touristName != null)
            holder.etxtTouristName.setText(tourBookingDetail.touristName);
        else
            holder.etxtTouristName.setText(null);
        if (tourBookingDetail.touristEmail != null)
            holder.etxtTouristEmail.setText(tourBookingDetail.touristEmail);
        else
            holder.etxtTouristEmail.setText(null);
        if (tourBookingDetail.dayOfBirth != null)
            holder.spinnerDayOfBirth.setSelection(tourBookingDetail.dayOfBirth);
        if (tourBookingDetail.monthOfBirth != null)
            holder.spinnerMonthOfBirth.setSelection(tourBookingDetail.monthOfBirth);
        if (tourBookingDetail.yearOfBirth != null)
            holder.spinnerYearOfBirth.setSelection(2019 - tourBookingDetail.yearOfBirth);
        if (tourBookingDetail.male != null)
            holder.rbtnFemale.setChecked(!tourBookingDetail.male);
        if (tourBookingDetail.touristType != null) {
            if (tourBookingDetail.touristType == 0)
                holder.txTouristType.setText("Người lớn");
            else if (tourBookingDetail.touristType == 1)
                holder.txTouristType.setText("Trẻ em");
            else
                holder.txTouristType.setText("Em bé");
        }
        if (tourBookingDetail.price != null) {
            holder.txtPrice.setText(String.valueOf(tourBookingDetail.price + "đ"));
        }
    }

    @Override
    public int getItemCount() {
        return tourBookingDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etxtTouristName, etxtTouristEmail;
        Spinner spinnerDayOfBirth, spinnerMonthOfBirth, spinnerYearOfBirth;
        RadioButton rbtnMale, rbtnFemale;
        TextView txtTouristNo, txTouristType, txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            etxtTouristName = itemView.findViewById(R.id.etxtTouristName);
            etxtTouristEmail = itemView.findViewById(R.id.etxtTouristEmail);
            spinnerDayOfBirth = itemView.findViewById(R.id.spinnerDayOfBirth);
            spinnerMonthOfBirth = itemView.findViewById(R.id.spinnerMonthOfBirth);
            spinnerYearOfBirth = itemView.findViewById(R.id.spinnerYearOfBirth);
            rbtnMale = itemView.findViewById(R.id.rbtnMale);
            rbtnFemale = itemView.findViewById(R.id.rbtnFemale);
            txtTouristNo = itemView.findViewById(R.id.txtTouristNo);
            txTouristType = itemView.findViewById(R.id.txTouristType);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            spinnerDayOfBirth.setAdapter(dayArrayAdapter);
            spinnerMonthOfBirth.setAdapter(monthArrayAdapter);
            spinnerYearOfBirth.setAdapter(yearArrayAdapter);
            etxtTouristName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (i2 > 0)
                        getItem(getAdapterPosition()).touristName = charSequence.toString();
                    else
                        getItem(getAdapterPosition()).touristName = null;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            etxtTouristEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (i2 > 0)
                        tourBookingDetailList.get(getAdapterPosition()).touristEmail = charSequence.toString();
                    else
                        tourBookingDetailList.get(getAdapterPosition()).touristEmail = null;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            spinnerDayOfBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0)
                        tourBookingDetailList.get(getAdapterPosition()).dayOfBirth = Integer.parseInt(dayArr[i]);
                    else
                        tourBookingDetailList.get(getAdapterPosition()).dayOfBirth = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerMonthOfBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0)
                        tourBookingDetailList.get(getAdapterPosition()).monthOfBirth = Integer.parseInt(monthArr[i]);
                    else
                        tourBookingDetailList.get(getAdapterPosition()).monthOfBirth = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerYearOfBirth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0)
                        tourBookingDetailList.get(getAdapterPosition()).yearOfBirth = Integer.parseInt(yearArr[i]);
                    else
                        tourBookingDetailList.get(getAdapterPosition()).yearOfBirth = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            rbtnMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    tourBookingDetailList.get(getAdapterPosition()).male = b;
                }
            });
            rbtnFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    tourBookingDetailList.get(getAdapterPosition()).male = !b;
                }
            });
        }
    }

    private boolean isFilled(int pos) {
        TourBookingDetail tourBookingDetail = tourBookingDetailList.get(pos);
        if (tourBookingDetail.touristName != null && tourBookingDetail.touristEmail != null
                && tourBookingDetail.dayOfBirth != null && tourBookingDetail.monthOfBirth != null
                && tourBookingDetail.yearOfBirth != null && tourBookingDetail.male != null
                && tourBookingDetail.touristType != null && tourBookingDetail.price != null)
            return true;
        return false;
    }

    public TourBookingDetail getItem(int pos) {
        return tourBookingDetailList.get(pos);
    }

    public void addTourists(int numberOfTourist, int touristType, int price) {
        for (int i = 0; i < numberOfTourist; i++) {
            TourBookingDetail tourBookingDetail = new TourBookingDetail();
            tourBookingDetail.touristType = touristType;
            tourBookingDetail.price = price;
            tourBookingDetailList.add(tourBookingDetail);
        }
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void removeTourists(int numberOfTourist, int touristType) {
        int removeCount = 0;
        for (int i = tourBookingDetailList.size() - 1; i >= 0; i--) {
            if (tourBookingDetailList.get(i).touristType == touristType) {
                tourBookingDetailList.remove(i);
                removeCount++;
                if (removeCount == numberOfTourist)
                    break;
            }
        }
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public ArrayList<TourBookingDetail> getTourists() {
        for(int i=0;i<tourBookingDetailList.size();i++)
            if(!isFilled(i))
                return null;
        return tourBookingDetailList;
    }
}
