package com.example.administrator.travel.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.InfoUserSearchPresenter;
import com.example.administrator.travel.presenters.InfoUserSearchPresenterImpl;
import com.example.administrator.travel.views.InfoUserSearchView;

/**
 * Created by Henry
 */

public class InfoUserSearchActivity extends AppCompatActivity  implements InfoUserSearchView {

    android.content.SharedPreferences sharedPreferences;
    android.widget.Button btnAddFriend,btnUnFriend;
    InfoUserSearchPresenter infoUserSearchPresenter;
    int friendRe = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info_user_search);

        String UserIDAddFr = getIntent().getStringExtra("UserID");
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserIDAddFr", UserIDAddFr);
        editor.commit();


        //
        de.hdodenhof.circleimageview.CircleImageView imgAvatar = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.img_info_user_search_avatar);
        android.widget.TextView tvName = (android.widget.TextView) findViewById(R.id.tv_info_user_search_name);
        android.widget.TextView tvSex = (android.widget.TextView) findViewById(R.id.tv_info_user_search_sex);
        android.widget.TextView tvSDT = (android.widget.TextView) findViewById(R.id.tv_info_user_search_sdt);
        android.widget.TextView tvEmail = (android.widget.TextView) findViewById(R.id.tv_info_user_search_email);
        btnAddFriend = (android.widget.Button) findViewById(R.id.btn_info_user_search_add);
        btnUnFriend = (android.widget.Button) findViewById(R.id.btn_info_user_search_unfriend);
//
        tvName.setText(getIntent().getStringExtra("name"));
        tvSDT.setText(getIntent().getStringExtra("sdt"));
        tvEmail.setText(getIntent().getStringExtra("mail"));
        com.squareup.picasso.Picasso.with(this)
                .load(getIntent().getStringExtra("urlAvatar"))
                .into (imgAvatar);

//        presenter.get
        infoUserSearchPresenter = new InfoUserSearchPresenterImpl(this);
//       sharedPreferences
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        infoUserSearchPresenter.TestFriendedPresenter(sharedPreferences);
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
    //    onClick button
    public void OnButtonBackInfoSearch(View view){
        finish();
    }
}
