package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CustomRecyclerSearchAdapter;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.bases.SearchFriendPresenter;
import com.example.administrator.travel.presenters.impls.SearchFriendPresenterImpl;
import com.example.administrator.travel.views.bases.SearchFriendView;

import java.util.List;

/**
 * Created by Henry
 */

public class SearchFriendsActivity extends AppCompatActivity implements SearchFriendView, View.OnFocusChangeListener, TextView.OnEditorActionListener, CustomRecyclerSearchAdapter.OnItemClickedListener {


    EditText etxtSearchFriend;
    RecyclerView recyclerViewSearchFriend;
    Toolbar toolbar;
    TextView txtNoResult;
    ImageButton btnClearSearchFriendResult;
    SearchFriendPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat_search_friends);
        mapping();
        etxtSearchFriend.setOnFocusChangeListener(this);
        etxtSearchFriend.setOnEditorActionListener(this);
        setOnButtonClearSearchFriendResultClick();

        Bundle bundle = getIntent().getExtras();
        presenter = new SearchFriendPresenterImpl(this);
        presenter.onViewCreated(bundle);


////                InfoUserSearchFragment infoUserSearchFragment = new InfoUserSearchFragment();
////
////                infoUserSearchFragment.setArguments(bundle);
////
////                android.app.FragmentManager manager = getFragmentManager();
////                manager.beginTransaction().replace(R.id.conten_layout_chatlistfriend, infoUserSearchFragment,
////                        infoUserSearchFragment.getTag()).addToBackStack("ChatSearch").commit();
//                startActivity(new Intent(SearchFriendsActivity.this, InfoUserSearchActivity.class)
//                        .putExtra("name", user.getName())
//                        .putExtra("sex", user.getSex())
//                        .putExtra("sdt", user.getSdt())
//                        .putExtra("mail", user.getMail())
//                        .putExtra("urlAvatar", user.getUrlAvatar())
//                        .putExtra("UserID", keyUser.get(index)));

    }

    private void mapping() {
        etxtSearchFriend = findViewById(R.id.edt_chat_contact_search);
        recyclerViewSearchFriend = findViewById(R.id.rv_chat_contact_friendearch);
        txtNoResult = findViewById(R.id.txtHaveNoResult);
        btnClearSearchFriendResult = findViewById(R.id.btnClearSearchFriendResult);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private void setOnButtonClearSearchFriendResultClick(){
        btnClearSearchFriendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonClearSearchFriendResultClicked();
            }
        });
    }

    @Override
    public void showListUserSearch(List<UserInformation> userInformationList) {
        CustomRecyclerSearchAdapter adapter = new CustomRecyclerSearchAdapter(userInformationList);
        adapter.setOnItemClickedListener(this);
        recyclerViewSearchFriend.setAdapter(adapter);
    }

    @Override
    public void showFriendInvitation(List<UserInformation> listUserSearch) {

    }

    @Override
    public void clearRecyclerViewSearchFriend() {
        recyclerViewSearchFriend.setAdapter(null);
    }

    @Override
    public void clearEditTextSearchFriend() {
        etxtSearchFriend.setText(null);
    }

    @Override
    public void showHaveNoSearchResult() {
        txtNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHaveNoSearchResult() {
        txtNoResult.setVisibility(View.GONE);
    }

    @Override
    public void gotoProfile(String friendId) {
        Intent intent = new Intent(SearchFriendsActivity.this, UserProfileActivity.class);
        intent.putExtra("userId", friendId);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            presenter.onEditTextSearchFriendTypingStoped(etxtSearchFriend.getText().toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || keyEvent != null &&
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                presenter.onEditTextSearchFriendTypingStoped(etxtSearchFriend.getText().toString());
            }
        }
        return false;
    }

    @Override
    public void onItemFriendClick(String friendId) {
        presenter.onItemFriendClicked(friendId);
    }
}

