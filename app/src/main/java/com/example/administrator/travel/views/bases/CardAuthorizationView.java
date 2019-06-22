package com.example.administrator.travel.views.bases;

import android.content.Context;

/**
 * Created by Admin on 6/10/2019.
 */

public interface CardAuthorizationView {
    void showLineNotifyAuthorizedCard();

    void hideLineNotifyAuthorizedCard();

    void showLineNotifyUnauthorizedCard();

    void hideLineNotifyUnauthorizedCard();

    void showButtonPay();

    void hideButtonPay();

    void enableEtxtCardNumber();

    void disableEtxtCardNumber();

    void enableEtxtCardHolder();

    void disableEtxtCardHolder();

    void enableEtxtExpirationMonth();

    void disableEtxtExpirationMonth();

    void enableEtxtExpirationYear();

    void disableEtxtExpirationYear();

    void enableEtxtCVC();

    void disableEtxtCVC();

    void showDialog();

    void closeDialog();

    void showAmount(Integer amount);

    void notify(String message);

    Context getContext();

    void closeForResult(Boolean result);

    void close();
}
