package com.example.administrator.travel.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CustomRecyclerSearchAdapter;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.ChatSearchFriendPresenter;
import com.example.administrator.travel.presenters.ChatSearchFriendPresenterImpl;
import com.example.administrator.travel.views.ChatSearchFriendView;
import com.example.administrator.travel.views.fragments.InfoUserSearchFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Henry
 */

public class ChatSearchFriendsActivity extends AppCompatActivity  implements ChatSearchFriendView {


    SharedPreferences sharedPreferences;
    EditText edtSearch;
    CustomRecyclerSearchAdapter adapter;
    RecyclerView mResultList;
    public List<UserInformation> listUserQuery = new ArrayList<>();
    public List<String> keyUser = new ArrayList<>();

    ChatSearchFriendPresenter chatSearchFriendPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_search_friends);

        edtSearch = (EditText) findViewById(R.id.edt_chat_contact_search);
        mResultList = (RecyclerView) findViewById(R.id.rv_chat_contact_friendearch);

        //set adapter
        adapter = new CustomRecyclerSearchAdapter(listUserQuery);
        mResultList.setAdapter(adapter);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
//       presenter
        chatSearchFriendPresenter = new ChatSearchFriendPresenterImpl(this);

//       sharedPreferences
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        chatSearchFriendPresenter.getFriendInvitationPresenter(sharedPreferences);
//
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        listUserQuery.clear();
                        keyUser.clear();
                        chatSearchFriendPresenter.getSearchFriendPresenter(edtSearch.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });

        adapter.setOnItemClickedListener(new CustomRecyclerSearchAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int index) {

                UserInformation user = listUserQuery.get(index);

//                Bundle bundle = new Bundle();
//                bundle.putString("name",user.getName());
//                bundle.putString("sex",user.getSex());
//                bundle.putString("sdt",user.getSdt());
//                bundle.putString("mail",user.getMail());
//                bundle.putString("urlAvatar",user.getUrlAvatar());
//
//                bundle.putString("UserID",keyUser.get(index));

//                InfoUserSearchFragment infoUserSearchFragment = new InfoUserSearchFragment();
//
//                infoUserSearchFragment.setArguments(bundle);
//
//                android.app.FragmentManager manager = getFragmentManager();
//                manager.beginTransaction().replace(R.id.conten_layout_chatlistfriend, infoUserSearchFragment,
//                        infoUserSearchFragment.getTag()).addToBackStack("ChatSearch").commit();
                startActivity(new Intent(ChatSearchFriendsActivity.this,InfoUserSearchActivity.class)
                        .putExtra("name",user.getName())
                        .putExtra("sex",user.getSex())
                        .putExtra("sdt",user.getSdt())
                        .putExtra("mail",user.getMail())
                        .putExtra("urlAvatar",user.getUrlAvatar())
                        .putExtra("UserID",keyUser.get(index)));
            }
        });

    }

    @Override
    public void ShowListUserSearch(List<UserInformation> listUserSearch, List<String> UID) {

        listUserQuery.clear();
        keyUser.clear();
        for (int i = 0; i < listUserSearch.size(); ++i) {
            listUserQuery.add(listUserSearch.get(i));
            keyUser.add(UID.get(i));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ShowFriendInvitation(List<UserInformation> listUserSearch, List<String> UID) {

        listUserQuery.clear();
        keyUser.clear();
        for (int i = 0; i < listUserSearch.size(); ++i) {
            listUserQuery.add(listUserSearch.get(i));
            keyUser.add(UID.get(i));
            adapter.notifyDataSetChanged();
        }
    }
    //    onClick button
    public void OnClickBackContactSearch(View view){
        finish();
    }
}

