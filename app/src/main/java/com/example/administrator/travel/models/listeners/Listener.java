package com.example.administrator.travel.models.listeners;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.entities.map.direction.Route;
import com.example.administrator.travel.models.entities.place.detail.PlaceDetail;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.firebase.database.DatabaseException;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public interface Listener {
    interface OnGetAboutToDepartToursFinishedListener {
        void onGetAboutToDepartToursSuccess(List<Tour> tours, HashMap<String, TourStartDate> tourStartMap);
        void onGetAboutToDepartToursFail(Exception ex);
    }

    interface OnGetTourImageFinishedListener {
        void onGetTourImageSuccess(int pos, String tourId, Bitmap tourImage);
    }

    interface OnGetCitiesFinishedListener {
        void onGetCitiesSuccess(List<City> cities);

        void onGetCitiesFail(Exception ex);
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
        void onJoinTourSuccess(String tourId, String tourStartId, String tourGuideId);

        void onJoinTourFail(Exception ex);
    }

    interface OnGetDaysFinishedListener {
        void onGetDaysSuccess(List<Day> dayList);

        void onGetDaysFail(Exception ex);
    }

    interface OnGetTourFinishedListener {
        void onGetTourSuccess(Tour tour);

        void onGetTourFail(Exception ex);
    }

    interface OnGetSchedulesFinishedListener {
        void onGetSchedulesSuccess(List<Schedule> scheduleList);

        void onGetSchedulesFail(Exception ex);
    }

    interface OnGetTourStartsFinishedListener {
        void onGetTourStartsSuccess(List<TourStartDate> tourStartDateList);

        void onGetTourStartsFail(Exception ex);
    }

    interface OnGetTourStartFinishedListener {
        void onGetTourStartSuccess(TourStartDate tourStartDate);

        void onGetTourStartFail(Exception ex);
    }

    interface OnGetCompanyFinishedListener {
        void onGetCompanySuccess(Company company);

        void onGetCompanyFail(Exception ex);
    }

    interface OnGetCompanyLogoFinishedListener{
        void onGetCompanyLogoSuccess(String companyId, Bitmap companyLogo);

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

    interface OnGetUserInforFinishedListener {
        void onGetUserInforSuccess(UserInformation user);
    }

    interface OnGetUserAvatarFinishedListener {
        void onGetUserAvatarSuccess(String userId, Bitmap avatar);
    }

    interface OnFinishTourFinishedListener {
        void onTourFinished();
    }

    interface OnGetCompaniesFinishedListener{
        void onGetCompaniesSuccess(List<Company> companyList);

        void onGetCompaniesFail(Exception ex);
    }

    interface OnCheckIsCompanyFinishedListener {
        void onCheckIsCompanySuccess(boolean isCompany);

        void onCheckIsCompanyFail(Exception ex);
    }

    interface OnPostActivityFinishedListener {
        void onPostActivitySuccess();

        void onPostActivityFail(Exception ex);
    }

    interface OnLoadImageFinishedListener {
        void onLoadImageSuccess(String fileName, Bitmap image);
    }

    interface OnGetNearbyFinishedListener {
        void onGetNearbySuccess(List<Nearby> nearbyList, String nextPageToken);

        void onGetNearbyFail(Exception ex);
    }

    interface OnGetPlaceTypeFinishedListener {
        void onGetPlaceTypeSuccess(List<NearbyType> placeTypeList);

        void onGetPlaceTypeFail(Exception ex);
    }

    interface OnDownloadImageFinishedListener {
        void onDownloadImageFail(Exception ex);

        void onDownloadImageSuccess(int pos, Bitmap result);
    }

    interface OnCheckJoiningTourFinishedListener {
        void onCheckJoiningTourTrue(String tourId, String tourStartId, String tourGuideId);

        void onCheckJoiningTourFalse();

        void onCheckJoingTourFail(Exception ex);
    }

    interface OnLoginFinishedListener {
        void onLoginSuccess(String userId);

        void onLoginFail(Exception ex);
    }

    interface OnSignUpFinishedListener {
        void onSignUpFinishedListener(String userId);

        void onSignUpFail(Exception ex);
    }

    interface OnCheckShareLocationFinishedListener {
        void onCheckLocationSuccess(boolean isShareLocation);

        void onCheckLocationFail(Exception ex);
    }

    interface OnSetShareLocationFinishedListener {
        void onSetShareLocationSuccess(boolean isShareLocation);

        void onSetShareLocationFail(Exception ex);
    }

    interface OnGetMyOwnedTourIdsFinishedListener {
        void onGetMyOwnedToursSuccess(List<Tour> listMyOwnedTour);

        void onGetMyOwnedToursFail(Exception ex);
    }

    interface OnGetPlaceDetailFinishedListener {
        void onGetPlaceDetailSuccess(PlaceDetail placeDetailList);

        void onGetPlaceDetailFail(Exception ex);
    }


    interface OnGetPeopleLocationFinishedListener {
        void onGetPeopleLocationSuccess(List<Participant> participantList);

        void onGetPeopleLocationFailure(Exception ex);
    }

    interface OnGetDirectionFinishedListener {
        void onGetDirectionSuccess(List<Route> routeList);

        void onGetDirectionFail(Exception ex);
    }

    interface OnPicassoLoadFinishedListener {
        void onPicassoLoadSuccess(int pos, Bitmap photo);

        void onPicassoLoadFail(Exception ex);
    }

    interface OnGetActivitiesFinishedListener {
        void onGetActivitiesSuccess(List<Activity> activityList);

        void onGetActivitiesFailure(DatabaseException ex);
    }

    interface OnGetActivityPhotosFinishedListener {
        void onGetActivityPhotosSuccess(String activityId, Bitmap photo);

    }

    interface OnGetBookingFinishedListener {
        void onGetMyBookTourSuccess(TourBooking tourBooking);

        void onGetMyBookTourFailure(DatabaseException ex);
    }

    interface OnBookTourFinishedListener {
        void onBookTourSuccess();

        void onBookTourFail(Exception ex);
    }

    interface OnLoadCityPhotoFinishedListener {
        void onLoadCityPhotoSuccess(String cityId, Bitmap cityPhoto);
    }


    interface OnGetToursFinishedListener {
        void onGetToursSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap);
        void onGetToursFail(Exception ex);
    }
    interface OnGetLikedToursFinishedListener {
        void onGetLikedToursSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap);
        void onGetLikedToursFail(Exception ex);
    }

    interface OnGetToursByDestinationFinishedListener {
        void onGetToursByDestinationSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap);
        void onGetToursByDestinationFail(Exception ex);
    }

    interface OnGetToursByOnwerFinishedListener {
        void onGetToursByOwnerSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap);
        void onGetToursByOwnerFail(Exception ex);
    }
}
