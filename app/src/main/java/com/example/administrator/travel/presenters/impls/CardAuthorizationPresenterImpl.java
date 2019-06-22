package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.models.bases.BankInteractor;
import com.example.administrator.travel.models.bases.BookTourInteractor;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.BankCard;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.models.entities.TourBookingRequest;
import com.example.administrator.travel.models.impls.BankInteractorImpl;
import com.example.administrator.travel.models.impls.BookTourInteractorImpl;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CardAuthorizationPresenter;
import com.example.administrator.travel.views.bases.CardAuthorizationView;

import java.util.ArrayList;

/**
 * Created by Admin on 6/10/2019.
 */

public class CardAuthorizationPresenterImpl implements CardAuthorizationPresenter, Listener.OnCheckValidCardFinishedListener, Listener.OnBookTourFinishedListener, Listener.OnGetCompanyFinishedListener {
    private CardAuthorizationView view;
    private BankInteractor bankInteractor;

    private final static int AUTHORIZE_MAX_TIMES = 3;
    private final static int CARD_NUMBER_LENGTH = 16;
    private final static int CVC_LENGTH = 3;
    private final static int EXPIRATION_MONTH_MIN_LENGTH = 1;
    private final static int EXPIRATION_MONTH_MAX_LENGTH = 2;
    private final static int EXPIRATION_YEAR_LENGTH = 2;

    private UserInteractor userInteractor;
    private BookTourInteractor bookTourInteractor;
    private CompanyInteractor companyInteractor;

    private String cardNumber, cardHolder, companyBankAccountNumber;
    private Integer cvc, expirationMonth, expirationYear, amount;
    private boolean[] validFields = new boolean[5]; // card number : 0, holder name : 1, month : 2, year : 3, cvc : 4
    private boolean authorized = false;
    private int authorizeCount = 0;

    private TourBooking tourBooking;
    private ArrayList<TourBookingDetail> tourBookingDetailList;

    public CardAuthorizationPresenterImpl(CardAuthorizationView view) {
        this.view = view;
        bankInteractor = new BankInteractorImpl();
        userInteractor = new UserInteractorImpl();
        bookTourInteractor = new BookTourInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewCreated(Intent intent) {
        tourBookingDetailList = intent.getParcelableArrayListExtra("bookingDetailList");
        for (TourBookingDetail tourBookingDetail : tourBookingDetailList)
            Log.e("list: ", tourBookingDetail.touristName + "," + tourBookingDetail.touristEmail);
        Bundle bundle = intent.getExtras();
        String tourStartId = bundle.getString("tourStartId");
        Integer numberOfAdult = bundle.getInt("numberOfAdult");
        Integer numberOfChildren = bundle.getInt("numberOfChildren");
        Integer numberOfBaby = bundle.getInt("numberOfBaby");
        amount = bundle.getInt("money");
        view.showAmount(amount);
        String companyId = bundle.getString("owner");
        String userId = userInteractor.getUserId();
        tourBooking = new TourBooking(userId, tourStartId, numberOfAdult, numberOfChildren, numberOfBaby, amount);

        view.hideLineNotifyAuthorizedCard();
        view.hideLineNotifyUnauthorizedCard();
        view.hideButtonPay();
        companyInteractor.getCompany(companyId, this);
        view.showDialog();
    }

    @Override
    public void onEtxtCardNumberStopedTyping(String cardNumberText) {
        if (cardNumberText.length() == CARD_NUMBER_LENGTH) {
            if (cardNumber != null && cardNumber.equals(cardNumberText))
                return;
            validFields[0] = true;
            cardNumber = cardNumberText;
            if (allFieldsIsValid())
                onAllFieldsIsValid();
        } else {
            validFields[0] = false;
            cardNumber = null;
            if (authorized)
                authorized = false;
            view.hideLineNotifyAuthorizedCard();
            view.hideLineNotifyUnauthorizedCard();
        }
    }

    @Override
    public void onEtxtCardHolderStopedTyping(String cardHolderText) {
        if (cardHolder != null && cardHolder.equals(cardHolderText))
            return;
        validFields[1] = true;
        cardHolder = cardHolderText.trim();
        if (allFieldsIsValid())
            onAllFieldsIsValid();
    }

    @Override
    public void onEtxtExpirationMonthStopedTyping(String expirationMonthText) {
        if (expirationMonthText.length() >= EXPIRATION_MONTH_MIN_LENGTH && expirationMonthText.length() <= EXPIRATION_MONTH_MAX_LENGTH) {
            if (expirationMonth != null && expirationMonth == Integer.parseInt(expirationMonthText))
                return;
            validFields[2] = true;
            expirationMonth = Integer.parseInt(expirationMonthText);
            if (allFieldsIsValid())
                onAllFieldsIsValid();
        } else {
            validFields[2] = false;
            expirationMonth = null;
            if (authorized)
                authorized = false;
            view.hideLineNotifyAuthorizedCard();
            view.hideLineNotifyUnauthorizedCard();
        }
    }

    @Override
    public void onEtxtExpirationYearStopedTyping(String expirationYearText) {
        if (expirationYearText.length() == EXPIRATION_YEAR_LENGTH) {
            if (expirationYear != null && expirationYear == Integer.parseInt(expirationYearText))
                return;
            validFields[3] = true;
            expirationYear = Integer.parseInt(expirationYearText);
            if (allFieldsIsValid())
                onAllFieldsIsValid();
        } else {
            validFields[3] = false;
            expirationYear = null;
            if (authorized)
                authorized = false;
            view.hideLineNotifyAuthorizedCard();
            view.hideLineNotifyUnauthorizedCard();
        }
    }

    @Override
    public void onEtxtCVCStopedTyping(String cvcText) {
        if (cvcText.length() == CVC_LENGTH) {
            if (cvc != null && cvc == Integer.parseInt(cvcText))
                return;
            validFields[4] = true;
            cvc = Integer.parseInt(cvcText);
            if (allFieldsIsValid())
                onAllFieldsIsValid();
        } else {
            validFields[4] = false;
            cvc = null;
            if (authorized)
                authorized = false;
            view.hideLineNotifyAuthorizedCard();
            view.hideLineNotifyUnauthorizedCard();
        }
    }

    @Override
    public void onButtonPayClicked() {
        if (authorized && cardNumber != null && companyBankAccountNumber != null) {
            TourBookingRequest tourBookingRequest = new TourBookingRequest(cardNumber, companyBankAccountNumber, amount,
                    tourBooking, tourBookingDetailList);
            bookTourInteractor.bookTour(tourBookingRequest, view.getContext(), this);
        }
    }
    private void onAllFieldsIsValid() {
        if (authorizeCount >= AUTHORIZE_MAX_TIMES)
            view.notify("Bạn đã nhập thẻ sai " + authorizeCount + " lần");
        else {
            bankInteractor.checkValidCard(new BankCard(cardNumber, cardHolder, expirationMonth, expirationYear, cvc), view.getContext(), this);
            view.disableEtxtCardNumber();
            view.disableEtxtCardHolder();
            view.disableEtxtExpirationMonth();
            view.disableEtxtExpirationYear();
            view.disableEtxtCVC();
        }
    }
    private boolean allFieldsIsValid() {
        for (int i = 0; i < validFields.length; i++)
            if (!validFields[i])
                return false;
        return true;
    }

    @Override
    public void onCheckValidCardSuccess(Boolean isValid) {
        if (isValid) {
            view.showLineNotifyAuthorizedCard();
            view.hideLineNotifyUnauthorizedCard();
            view.showButtonPay();
            authorizeCount = 0;
        } else {
            view.showLineNotifyUnauthorizedCard();
            view.hideLineNotifyAuthorizedCard();
            authorizeCount++;
        }
        authorized = isValid;
        view.enableEtxtCardNumber();
        view.enableEtxtCardHolder();
        view.enableEtxtExpirationMonth();
        view.enableEtxtExpirationYear();
        view.enableEtxtCVC();
    }

    @Override
    public void onCheckValidCardFail(Exception ex) {
        Toast.makeText(view.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        view.hideLineNotifyUnauthorizedCard();
        view.hideLineNotifyAuthorizedCard();
        view.enableEtxtCardNumber();
        view.enableEtxtCardHolder();
        view.enableEtxtExpirationMonth();
        view.enableEtxtExpirationYear();
        view.enableEtxtCVC();
    }

    @Override
    public void onBookTourSuccess() {
        view.notify("Đặt tour thành công");
        view.closeForResult(true);
    }

    @Override
    public void onBookTourFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetCompanySuccess(Company company) {
        companyBankAccountNumber = company.bankAccountNumber;
        Log.e("onGetCompanySuccess: ", companyBankAccountNumber + "");
        view.closeDialog();
    }

    @Override
    public void onGetCompanyFail(Exception ex) {
        view.notify("Đã có lỗi xảy ra vui lòng thử lại");
        view.close();
    }
}
