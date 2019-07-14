package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

public interface ReviewDetailPresenter {
    void onViewCreated(Intent intent);

    void onButtonLikeClicked();

    void onButtonDislikeClicked();
}
