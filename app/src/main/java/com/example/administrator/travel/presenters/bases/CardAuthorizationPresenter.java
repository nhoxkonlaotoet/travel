package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Admin on 6/10/2019.
 */

public interface CardAuthorizationPresenter {
    void onViewCreated(Intent itent);

    void onEtxtCardNumberStopedTyping(String cardNumberText);

    void onEtxtCardHolderStopedTyping(String cardHolderText);

    void onEtxtExpirationMonthStopedTyping(String expirationMonthText);

    void onEtxtExpirationYearStopedTyping(String expirationYearText);

    void onEtxtCVCStopedTyping(String cvcText);

    void onButtonPayClicked();
}
