package com.example.administrator.travel.presenters.bases;

/**
 * Created by Admin on 4/26/2019.
 */

public interface LoginPresenter {
    void onViewCreated();
    void onBtnLoginClicked(String email, String password);
    void onTextSignUpClicked();
    void onTextResetPasswordClicked();
}
