package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CompanyContactPresenter;
import com.example.administrator.travel.views.bases.CompanyContactView;
import com.example.administrator.travel.views.activities.MapsActivity;

/**
 * Created by Admin on 4/11/2019.
 */

public class CompanyPresenterImpl implements CompanyContactPresenter, Listener.OnGetCompanyFinishedListener {
    CompanyContactView view;
    CompanyInteractor companyInteractor;
    Company company;

    public CompanyPresenterImpl(CompanyContactView view) {
        this.view = view;
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        String companyId = bundle.getString("owner");
        companyInteractor.getCompany(companyId, this);
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
            intent.putExtra("openFrom", "contact");
            String destination = company.location.latitude + "," + company.location.longitude;
            intent.putExtra("destination", destination);
            view.gotoMap(intent);
        }
    }

    @Override
    public void onGetCompanySuccess(Company company) {
        this.company = company;
        view.showContact(company);
    }

    @Override
    public void onGetCompanyFail(Exception ex) {
        view.notifyGetContactFailure(ex);
    }
}
