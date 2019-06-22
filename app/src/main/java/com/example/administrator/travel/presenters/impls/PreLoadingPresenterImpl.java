package com.example.administrator.travel.presenters.impls;

import android.widget.Toast;

import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.PreLoadingPresenter;
import com.example.administrator.travel.views.bases.PreLoadingView;

/**
 * Created by Admin on 4/25/2019.
 */

public class PreLoadingPresenterImpl implements PreLoadingPresenter, Listener.OnCheckJoiningTourFinishedListener {
    PreLoadingView view;
    String userId;
    ParticipantInteractor participantInteractor;
    UserInteractor userInteractor;


    public PreLoadingPresenterImpl(PreLoadingView view) {
        this.view = view;
        participantInteractor = new ParticipantInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }


    @Override
    public void onViewCreated() {
        if (userInteractor.isLogged()) {
            userId = userInteractor.getUserId();
            participantInteractor.checkJoiningTour(userId, this);
        } else
            view.gotoHomeActivity();
    }



    @Override
    public void onCheckJoiningTourTrue(String tourId, String tourStartId, String tourGuide) {
        participantInteractor.rememberTour(userId, tourStartId, tourId, tourGuide, view.getContext());
        view.gotoHomeActivity();
    }

    @Override
    public void onCheckJoiningTourFalse() {
        String userId = userInteractor.getUserId();
        participantInteractor.removeparticipatingTour(userId, view.getContext());
        //Log.e("JoiingTourFalse: ","____________________" );
        view.gotoHomeActivity();
    }

    @Override
    public void onCheckJoingTourFail(Exception ex) {
        Toast.makeText(view.getContext(), "Tải dữ liệu không thành công, vui lòng mở lại ứng dụng", Toast.LENGTH_SHORT).show();
    }


}
