package com.example.administrator.travel.presenters.impls;

import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.AddTourStartDateRequest;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.AddTourStartDatePresenter;
import com.example.administrator.travel.views.activities.AddTourStartDateActivity;
import com.example.administrator.travel.views.bases.AddTourStartDateView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.vo.DateData;

public class AddTourStartDatePresenterImpl implements AddTourStartDatePresenter, Listener.OnGetTourStartsFinishedListener, Listener.OnGetTourFinishedListener, Listener.OnAddTourStartDateRequest {
    AddTourStartDateView view;
    TourStartInteractor tourStartInteractor;
    TourInteractor tourInteractor;
    UserInteractor userInteractor;
    private Long selectedDate;
    Long today;
    String tourId;

    public AddTourStartDatePresenterImpl(AddTourStartDateView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        tourStartInteractor = new TourStartInteractorImpl();
        tourInteractor = new TourInteractorImpl();
        today = (Calendar.getInstance().getTimeInMillis());
        Log.e("today", today + "");
    }


    @Override
    public void onViewCreated(Bundle bundle) {
        tourId = bundle.getString("tourId");
        tourStartInteractor.getTourStarts(tourId, this);
        tourInteractor.getTour(tourId, this);
    }

    @Override
    public void onButtonAddTourStartDateClick() {
        if (userInteractor.isLogged()) {
            String userId = userInteractor.getUserId();
            try {
                int numberOfPeople = Integer.parseInt(view.getEditTextNumberOfAdultValue());
                numberOfPeople += Integer.parseInt(view.getEditTextNumberOfChildrenValue());
                numberOfPeople += Integer.parseInt(view.getEditTextNumberOfBabyValue());

                AddTourStartDateRequest addTourStart = new AddTourStartDateRequest(userId, selectedDate, numberOfPeople);
                tourStartInteractor.addTourStartRequest(tourId, addTourStart, this);

            } catch (Exception ex) {
                view.notify("Số người tham gia không hợp lệ");
            }

        }
    }

    @Override
    public void onDateClick(Long time, int year, int month, int day) {
        for (int i = 0; i < view.getListDate().size(); i++) {
            if (view.getListDate().get(i).equals(new DateData(year, month, day))) {
                if (time != selectedDate) {
                    view.notify("Ngày khởi hành này đã tồn tại. Bạn có thể đặt tour ngay");
                    return;
                }
            }
        }
        if (time < this.today + 86400000 * 10) {
            view.notify("Vui lòng chọn sau hôm nay 10 ngày");
            return;
        }
        if (selectedDate != null)
            removeDate(selectedDate);
        selectedDate = time;
        view.addDate(year, month, day);
    }

    void removeDate(Long time) {
        Date date = new Date(time);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);
        int day = Integer.parseInt(dateString.substring(0, 2));
        int month = Integer.parseInt(dateString.substring(3, 5));
        int year = Integer.parseInt(dateString.substring(6, 10));
        view.removeDate(year, month, day);
    }

    @Override
    public void onGetTourStartsSuccess(List<TourStartDate> tourStartDateList) {
        view.showExistDate(tourStartDateList);
    }

    @Override
    public void onGetTourStartsFail(Exception ex) {

    }

    @Override
    public void onGetTourSuccess(Tour tour) {
        view.showPrices(tour.adultPrice, tour.childrenPrice, tour.babyPrice);
    }

    @Override
    public void onGetTourFail(Exception ex) {

    }

    @Override
    public void onAddTourStartDateSuccess() {
        view.notify("Cám ơn bạn đã đề xuất. Chúng tôi sẽ sớm mở tour");
        view.close();
    }

    @Override
    public void onAddTourStartDateFail(Exception ex) {
        view.notify(ex.getMessage());

    }
}
