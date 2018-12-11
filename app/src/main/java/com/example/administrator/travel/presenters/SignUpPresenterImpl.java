package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.SignUpInteractor;
import com.example.administrator.travel.models.SignUpInteractorImpl;
import com.example.administrator.travel.views.SignUpView;

/**
 * Created by Henry on 12/11/2018.
 */

public class SignUpPresenterImpl implements SignUpPresenter {

    SignUpView signUpView;
    SignUpInteractor signUpInteractor;

    public SignUpPresenterImpl(SignUpView signUpView){
        this.signUpView = signUpView;
        signUpInteractor = new SignUpInteractorImpl();
    }

    @Override
    public int CheckRegister(String Email, String Password, String CheckPass) {
        return signUpInteractor.CheckRegister(Email,Password,CheckPass);
    }
}
