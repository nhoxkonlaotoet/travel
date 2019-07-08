package com.example.administrator.travel.presenters.impls;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.LoginPresenter;
import com.example.administrator.travel.views.bases.LoginView;

/**
 * Created by Admin on 4/26/2019.
 */

public class LoginPresenterImpl implements LoginPresenter,
        Listener.OnLoginFinishedListener, Listener.OnCheckJoiningTourFinishedListener, Listener.OnCheckIsCompanyFinishedListener {
    LoginView view;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    CompanyInteractor companyInteractor;
    private final static int CHECK_JOINING_TOUR_INDEX = 0;
    private final static int CHECK_IS_COMPANY_INDEX = 1;
    boolean[] finishFlags = new boolean[2];

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewCreated() {
        if (userInteractor.isLogged()) {//    view.gotoPreLoadingActivity();
            String userId = userInteractor.getUserId();
            participantInteractor.checkJoiningTour(userId, this);
            companyInteractor.checkIsCompany(userId, this);
            for (int i = 0; i < finishFlags.length; i++)
                finishFlags[i] = false;
        }
    }

    @Override
    public void onBtnLoginClicked(String email, String password) {
        userInteractor.login(email, password, view.getContext(), this);
        view.showLoginDialog();
    }

    @Override
    public void onTextSignUpClicked() {
        view.gotoSignUpActivity();
    }

    @Override
    public void onTextResetPasswordClicked() {
        view.gotoResetPassActivity();
    }

    @Override
    public void onLoginSuccess(String userId) {
        Log.e( "onLoginSuccess: ","_____________________" );
        participantInteractor.checkJoiningTour(userId, this);
        companyInteractor.checkIsCompany(userId, this);
    }

    @Override
    public void onLoginFail(Exception ex) {
        view.notifyLoginFail(ex.getMessage());
        view.closeLoginDialog();
    }

    @Override
    public void onCheckJoiningTourTrue(String tourId, String tourStartId, String tourGuideId) {
        Log.e( "onCheckJoining: ","_____________________" );

        String userId = userInteractor.getUserId();
        participantInteractor.rememberTour(userId, tourStartId, tourId, tourGuideId, view.getContext());
        finishFlags[CHECK_JOINING_TOUR_INDEX] = true;
        if (checkLoadFinished())
        {
            view.closeLoginDialog();
            view.close();
        }
    }

    @Override
    public void onCheckJoiningTourFalse() {
        finishFlags[CHECK_JOINING_TOUR_INDEX] = true;
        if (checkLoadFinished())
        {
            view.closeLoginDialog();
            view.close();
        }
    }

    @Override
    public void onCheckJoingTourFail(Exception ex) {
        view.notifyLoginFail("Tải dữ liệu thất bại " + ex.getMessage());
        if (userInteractor.isLogged()) {
            userInteractor.logout();
            view.closeLoginDialog();
        }
    }

    @Override
    public void onCheckIsCompanySuccess(boolean isCompany) {
        Log.e( "IsCompanySuccess: ","_____________________" );
        String userId = userInteractor.getUserId();
        companyInteractor.setIsCompany(userId, isCompany, view.getContext());
        Toast.makeText(view.getContext(), isCompany+"___________", Toast.LENGTH_SHORT).show();
        finishFlags[CHECK_IS_COMPANY_INDEX] = true;
        if (checkLoadFinished())
        {
            view.closeLoginDialog();
            view.close();
        }
    }

    @Override
    public void onCheckIsCompanyFail(Exception ex) {
        view.notifyLoginFail("Tải dữ liệu thất bại " + ex.getMessage());
        if (userInteractor.isLogged()) {
            userInteractor.logout();
            view.closeLoginDialog();
        }

    }

    boolean checkLoadFinished() {
        for (int i = 0; i < finishFlags.length; i++)
            if (!finishFlags[i])
                return false;
        return true;
    }
}
