package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

public interface SearchFriendPresenter {
    void onViewCreated(Bundle bundle);

    void onEditTextSearchFriendTypingStoped(String keyword);

    void onItemFriendClicked(String friendId);

    void onBackButtonPressed();

    void onButtonClearSearchFriendResultClicked();
}
