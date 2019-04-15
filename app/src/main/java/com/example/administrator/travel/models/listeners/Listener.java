package com.example.administrator.travel.models.listeners;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public interface Listener {
    interface OnGetToursFinishedListener {
        void onGetToursSuccess(List<Tour> tours);

        void onGetToursFail(Exception ex);
    }

    interface OnGetTourImagesFinishedListener {
        void onGetTourImagesSuccess(Bitmap[] images);

        void onGetTourImagesFail(Exception ex);
    }

    interface OnGetCitiesFinishedListener {
        void onGetCitiesSuccess(List<City> cities);

        void onGetCitiesFail(Exception ex);
    }

    interface OnGetFirstImageFinishedListener {
        void onGetFirstImageSuccess(String tourId, Bitmap image);

        void onGetFirstImageFail(Exception ex);
    }

    interface OnGetMyTourIdsFinishedListener {
        void onGetMyTourIdsSuccess(List<String> listMyTourId);

        void onGetMyTourIdsFail(Exception ex);
    }

    interface OnGetMyTourInfoFinishedListener {
        void onGetMyTourInfoSuccess(int pos, Tour tour, TourStartDate tourStartDate);

        void onGetMyTourInfoFail(Exception ex);
    }

    interface OnJoinTourFinishedListener {
        void onJoinTourSuccess(String tourId, String tourStartId);

        void onJoinTourFail(Exception ex);
    }

    interface OnRememberTourFinishedListener {
        void onRememberTourSuccess();

        void onRememberTourFail(Exception ex);
    }

    interface OnGetDaysFinishedListener {
        void onGetDaysSuccess(List<Day> dayList);

        void onGetDaysFail(Exception ex);
    }

    interface OnGetTourFinishedListener {
        void onGetTourSuccess(Tour tour);

        void onGetTourFail(Exception ex);
    }

    interface OnGetScheduleFinishedListener {
        void onGetScheduleSuccess(List<Schedule> scheduleList);

        void onGetScheduleFail(Exception ex);
    }

    interface OnGetTourStartFinishedListener {
        void onGetTourStartSuccess(List<TourStartDate> tourStartDateList);

        void onGetTourStartFail(Exception ex);
    }

    interface OnGetCompanyContactFinishedListener {
        void onGetCompanyContactSuccess(Company company);

        void onGetCompanyContactFail(Exception ex);
    }

    interface OnGetReviewsTourFinishedListener {
        void onGetReviewTourSuccess(List<Rating> ratingList);

        void onGetReviewTourFail(Exception ex);
    }

    interface OnGetRatingTourFinishedListener {
        void onGetRatingTourSuccess(float value, long count);

        void onGetRatingTourFail(Exception ex);
    }

    interface OnCheckRatedFinishedListener {
        void onCheckRatedTrue();

        void onCheckRatedFalse();

        void onCheckRatedError(Exception ex);
    }

    interface OnRateTourFinishedListener {
        void onRateTourSuccess();

        void onRateTourFail(Exception ex);
    }

    interface OnGetUserNameFinishedListener {
        void onGetUserNameSuccess(String userId, String name, int pos);
    }

    interface OnGetUserAvatarFinishedListener {
        void onGetUserAvatarFinishedListener(String userId, Bitmap avatar, int pos);
    }

    interface OnFinishTourFinishedListener {
        void onTourFinished();
    }
    interface OnCheckIsCompanyFinishedListener{
        void onCheckIsCompanySuccess(boolean isCompany);
    }

    interface OnPostActivityFinishedListener{
        void onPostActivitySuccess();
        void onPostActivityFail(Exception ex);
    }
    interface OnLoadImageFinishedListener{
        void onLoadImageSuccess(int pos, Bitmap image);
    }
}
