package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.travel.models.bases.CompanyContactInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.impls.CompanyContactInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CompanyContactPresenter;
import com.example.administrator.travel.views.CompanyContactView;
import com.example.administrator.travel.views.activities.MapsActivity;

/**
 * Created by Admin on 4/11/2019.
 */

public class CompanyContactPresenterImpl implements CompanyContactPresenter, Listener.OnGetCompanyContactFinishedListener {
    CompanyContactView view;
    CompanyContactInteractor companyContactInteractor;
    Company company;

    public CompanyContactPresenterImpl(CompanyContactView view) {
        this.view = view;
        companyContactInteractor = new CompanyContactInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        String companyId = bundle.getString("owner");
        companyContactInteractor.getCompanyContact(companyId, this);
    }

    @Override
    public void onButtonWebsiteClicked() {
        if (company != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(company.website));
            view.gotoWebsite(intent);
        }
    }

    @Override
    public void onButtonPhoneClicked() {
        if (company != null) {
            String phonenumber = company.phoneNumber.toString();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenumber));
            view.gotoCall(intent);
        }
    }

    @Override
    public void onButtonAddressClicked() {
        if (company != null) {
            Intent intent = new Intent(view.getContext(), MapsActivity.class);
            intent.putExtra("action", "nearby");
            String destination = company.location.latitude + "," + company.location.longitude;
            intent.putExtra("destination", destination);
            view.gotoMap(intent);
        }
    }

    @Override
    public void onGetCompanyContactSuccess(Company company) {
        this.company = company;
        view.showContact(company);
        view.hideProgressBarCompanyName();
        view.hideProgressBarAddress();
        view.hideProgressBarPhoneNumber();
        view.hideProgressBarWeb();
    }

    @Override
    public void onGetCompanyContactFail(Exception ex) {
        view.notifyGetContactFailure(ex);
    }
}
