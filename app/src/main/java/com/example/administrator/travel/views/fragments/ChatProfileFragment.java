package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.ChatProfilePresenter;
import com.example.administrator.travel.presenters.ChatProfilePresenterImpl;
import com.example.administrator.travel.views.ChatProfileView;
import com.example.administrator.travel.views.activities.UpdateInfoAccountActivity;

/**
 * Created by Henry
 */

public class ChatProfileFragment extends Fragment implements ChatProfileView{

    TextView tvName, tvSex, tvEmail, tvSDT;
    Button btnChangeInfo;
    de.hdodenhof.circleimageview.CircleImageView imgAvatar;
    String mURLAvatar;
    android.content.SharedPreferences sharedPreferences;
    ChatProfilePresenter chatProfilePresenter;

    public ChatProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_profile, container, false);

        tvName = (TextView) view.findViewById(R.id.tv_chat_frofile_name);
        tvSex = (TextView) view.findViewById(R.id.tv_chat_profile_sex);
        tvEmail = (TextView) view.findViewById(R.id.tv_chat_profile_email);
        tvSDT = (TextView) view.findViewById(R.id.tv_chat_profile_phone);
        btnChangeInfo = (Button) view.findViewById(R.id.btn_chat_profile_change);
        imgAvatar = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_chat_profile_avatar);

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), UpdateInfoAccountActivity.class);
                intent.putExtra("name",tvName.getText());
                intent.putExtra("sdt",tvSDT.getText());
                intent.putExtra("sex",tvSex.getText());
                intent.putExtra("email",tvEmail.getText());
                intent.putExtra("urlAvatar",mURLAvatar);
                startActivity (intent);
            }
        });

        //presenter.get
        chatProfilePresenter = new ChatProfilePresenterImpl(this);
//       sharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        chatProfilePresenter.getUserProfilePresenter(sharedPreferences);
        return view;
    }

    @Override
    public void showResultGetProfileUser(String mName, String mSDT, String mSex, String mEmail, String mURL) {

        tvName.setText(mName);
        tvSDT.setText(mSDT);
        tvSex.setText(mSex);
        tvEmail.setText(mEmail);
        mURLAvatar = mURL;

        com.squareup.picasso.Picasso.with(getActivity())
                .load(mURLAvatar)
                .into (imgAvatar);
    }

}

