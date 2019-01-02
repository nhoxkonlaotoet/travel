package com.example.administrator.travel.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public class AcceptBookingAdapter extends BaseAdapter {
    List<TourBooking> lstBooking;
    List<UserInformation> lstUser;
    Context context;
    public AcceptBookingAdapter(List<TourBooking> lstBooking, Context context){
        this.lstBooking=lstBooking;
        if(context!=null)
            this.context=context;
        lstUser=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lstBooking.size();
    }

    @Override
    public Object getItem(int i)
    {
        if(lstBooking.size()==0)
            return null;
        return lstBooking.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_accept_booking, null);

        TextView txtUserName = convertView.findViewById(R.id.txtUserName);
        TextView txtAdultNumber = convertView.findViewById(R.id.txtAdultNumber);
        TextView txtChildrenNumber = convertView.findViewById(R.id.txtChildrenNumber);
        TextView txtBabyNumber = convertView.findViewById(R.id.txtBabyNumber);
        TextView txtMoney = convertView.findViewById(R.id.txtMoney);
        RelativeLayout btnAccept = convertView.findViewById(R.id.btnAccept);
        RelativeLayout btnCall = convertView.findViewById(R.id.btnCall);
        final ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        final TourBooking tourBooking =lstBooking.get(position);
        btnCall.setEnabled(false);

        if(tourBooking.accepted) {
            btnAccept.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
        for(int i=0;i<lstUser.size();i++)
            if(lstUser.get(i).id.equals(tourBooking.userId))
            {
                txtUserName.setText(lstUser.get(i).getName());
                btnCall.setEnabled(true);
                btnCall.setTag(lstUser.get(i).sdt);
                break;
            }
        txtAdultNumber.setText(tourBooking.adult+"");
        txtChildrenNumber.setText(tourBooking.children+"");
        txtBabyNumber.setText(tourBooking.baby+"");
        txtMoney.setText(tourBooking.money+"đ");

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context==null)
                    return;
                String phoneNumber = view.getTag().toString();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},100);
                        return;
                    }
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("tour_booking")
                        .child(tourBooking.tourStartDateId+"+"+tourBooking.userId).child("accepted");
                reference.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.setVisibility(View.INVISIBLE);
                        DatabaseReference tourStartRef = database.getReference("tour_start_date")
                                .child(tourBooking.tourStartDateId).child("peopleBooking");
                        tourStartRef.setValue(tourBooking.adult+tourBooking.children);
                        btnDelete.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("tour_booking")
                        .child(tourBooking.tourStartDateId+"+"+tourBooking.userId);
                reference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        lstBooking.remove(tourBooking);
                        notifyDataSetChanged();
                    }
                });
            }
        });

        return convertView;
    }
    public void updateUserInfor(UserInformation user){
        for(int i=0;i<lstUser.size();i++)
            if(lstUser.get(i).id.equals(user.id))
                return;
        lstUser.add(user);
        notifyDataSetChanged();
        Log.e( "updateUserInfor: ", user.name);
    }
}
