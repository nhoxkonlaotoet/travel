package com.example.administrator.travel.presenters;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.travel.models.GetServerTimeInteractor;
import com.example.administrator.travel.models.OnGetMyToursFinishedListener;
import com.example.administrator.travel.models.OnGetServerTimeFinishedListener;
import com.example.administrator.travel.models.OnGetUserIdFinishedListener;
import com.example.administrator.travel.models.SelectMyTourInteractor;
import com.example.administrator.travel.models.SettingInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.SelectMyTourView;
import com.example.administrator.travel.views.fragments.SelectMyTourFragment;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public class SelectMyTourPresenter implements OnGetMyToursFinishedListener,OnGetUserIdFinishedListener{
    SelectMyTourView view;
    SelectMyTourInteractor myToursInteractor;
    SettingInteractor settingInteractor;
    String  tourStartId,userId;
    Boolean firstEnter=true;
    public SelectMyTourPresenter(SelectMyTourView view){
        this.view = view;
        myToursInteractor = new SelectMyTourInteractor();
        settingInteractor = new SettingInteractor();
    }
    public void onViewLoad()
    {
        Log.e( "viewload: ", "presenter");
        if(firstEnter) {
            myToursInteractor.checkJoiningTour(((SelectMyTourFragment) view).getActivity(), this);
            firstEnter=false;
        }
        settingInteractor.getUserId(this,((SelectMyTourFragment)view).getActivity());

    }
    public void onBtnScanQRClick()
    {
        view.gotoCamera();
    }
    public void onItemTourClicked(View item)
    {
        //tag= tourId+" "+tourStartId;
        String[] s=item.getTag().toString().split(" ");
        view.gotoTourActivity(s[0],s[1]);
    }
    public void onBtnLoginClicked()
    {
        view.gotoLoginActivity();
    }
    public void onViewScanned(String tourStartId)
    {
        this.tourStartId=tourStartId;
        myToursInteractor.joinTour(tourStartId,((SelectMyTourFragment)view).getActivity(),this);
    }

    @Override
    public void onJoinTourSuccess() {
        myToursInteractor.getMyTours(userId,this);
        myToursInteractor.rememberTour(tourStartId,((SelectMyTourFragment)view).getActivity(), this);
    }

    @Override
    public void onJoinTourFailure(Exception ex) {

    }

    @Override
    public void isJoiningTourTrue(String tourId, String tourStartId) {
        view.gotoTourActivity(tourId,tourStartId);
    }

    @Override
    public void isJoiningTourFalse() {
    }

    @Override
    public void onGetMyToursSuccess(List<Tour> lstTour, List<TourStartDate> lstTourStart) {
        view.showMyTours(lstTour,lstTourStart);
    }

    @Override
    public void onGetMyToursFailure(Exception ex) {
    }

    @Override
    public void onRememberTourSuccess(String tourId, String tourStartId){
        view.gotoTourActivity(tourId,tourStartId);
    }

    @Override
    public void onRememberTourFailure(Exception ex) {

    }

    @Override
    public void onGetUserIdSuccess(String userId) {
        this.userId=userId;
        myToursInteractor.getMyTours(userId,this);
        view.showLayoutMyTours();
        view.hideLayoutLogin();
    }

    @Override
    public void onGetUserIdFailure(Exception ex) {
        view.showLayoutLogin();
        view.hideLayoutMyTours();
    }
}
