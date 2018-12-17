package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.InfoUserSearchPresenter;
import com.example.administrator.travel.presenters.InfoUserSearchPresenterImpl;
import com.example.administrator.travel.views.InfoUserSearchView;


public class InfoUserSearchFragment extends Fragment implements InfoUserSearchView{


//                bundle.putString("name",user.getName());
//                bundle.putString("sex",user.getSex());
//                bundle.putString("sdt",user.getSdt());
//                bundle.putString("mail",user.getMail());
//                bundle.putString("urlAvatar",user.getUrlAvatar());
//
//                bundle.putString("UserID",keyUser.get(index));
    android.content.SharedPreferences sharedPreferences;
    android.widget.Button btnAddFriend,btnUnFriend;
    InfoUserSearchPresenter infoUserSearchPresenter;
    int friendRe = 0;

    public InfoUserSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//chay truoc oncreateView
        if (getArguments() != null) {
            String UserIDAddFr = getArguments().getString("UserID");
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("UserIDAddFr", UserIDAddFr);
            editor.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_user_search, container, false);
        //
        de.hdodenhof.circleimageview.CircleImageView imgAvatar = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_info_user_search_avatar);
        android.widget.TextView tvName = (android.widget.TextView) view.findViewById(R.id.tv_info_user_search_name);
        android.widget.TextView tvSex = (android.widget.TextView) view.findViewById(R.id.tv_info_user_search_sex);
        android.widget.TextView tvSDT = (android.widget.TextView) view.findViewById(R.id.tv_info_user_search_sdt);
        android.widget.TextView tvEmail = (android.widget.TextView) view.findViewById(R.id.tv_info_user_search_email);
        btnAddFriend = (android.widget.Button) view.findViewById(R.id.btn_info_user_search_add);
        btnUnFriend = (android.widget.Button) view.findViewById(R.id.btn_info_user_search_unfriend);
//
        tvName.setText(getArguments().getString("name"));
        tvSDT.setText(getArguments().getString("sdt"));
        tvEmail.setText(getArguments().getString("mail"));
        com.squareup.picasso.Picasso.with(getActivity())
                .load(getArguments().getString("urlAvatar"))
                .into (imgAvatar);

//        presenter.get
        infoUserSearchPresenter = new InfoUserSearchPresenterImpl(this);
//       sharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        infoUserSearchPresenter.TestFriendedPresenter(sharedPreferences);

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onResultTestFriendedView(final int friend) {
        this.friendRe = friend;
        if(friendRe == 3){
            btnAddFriend.setText("Bạn bè");
            btnAddFriend.setEnabled(false);
            btnUnFriend.setVisibility(View.VISIBLE);
        }else if(friendRe == 1){
            btnAddFriend.setText("Đã gửi kết bạn");
            btnAddFriend.setEnabled(true);
        }else if(friendRe == 2){
            btnAddFriend.setText("Chấp nhận kết bạn");
            btnAddFriend.setEnabled(true);
        }
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friendRe == 0){//gửi kết bạn
                    infoUserSearchPresenter.RequestAddFriendPresenter();
                    btnAddFriend.setText("Đã gửi kết bạn");
                    btnUnFriend.setVisibility(View.GONE);
                    friendRe = 1;
                }else if(friendRe == 1){// Huỷ kết bạn
                    infoUserSearchPresenter.CancelAddFriendPresenter();
                    btnAddFriend.setText("Kết bạn");
                    btnUnFriend.setVisibility(View.GONE);
                    friendRe = 0;
                }else if(friendRe == 2){//Chấp nhận kết bạn
                    infoUserSearchPresenter.AcceptAddFriendPresenter();
                    btnAddFriend.setText("Bạn bè");
                    btnAddFriend.setEnabled(false);
                    friendRe = 3;
                    btnUnFriend.setVisibility(View.VISIBLE);
                }
            }
        });
        btnUnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(friendRe == 3){//Unfriend
                    infoUserSearchPresenter.UnFriendPresenter();
                    friendRe = 2;
                    btnAddFriend.setText("Chấp nhận kết bạn");
                    btnAddFriend.setEnabled(true);
                    btnUnFriend.setVisibility(View.GONE);
                }
            }
        });
    }
}
