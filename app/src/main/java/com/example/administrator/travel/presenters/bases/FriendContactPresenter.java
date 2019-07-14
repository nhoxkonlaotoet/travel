package com.example.administrator.travel.presenters.bases;

import android.content.SharedPreferences;
import android.os.Bundle;


public interface FriendContactPresenter {
    void onViewCreated(Bundle bundle);

    void onItemFriendClick(String friendId);
}
