package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.ContactCompanyInteractor;
import com.example.administrator.travel.models.OnGetCompanyContactFinishedListener;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.views.ContactCompanyView;

/**
 * Created by Administrator on 22/12/2018.
 */

public class ContactCompanyPresenter implements OnGetCompanyContactFinishedListener{
    ContactCompanyView view;
    ContactCompanyInteractor contactCompanyInteractor;
    Company company;
    public ContactCompanyPresenter(ContactCompanyView view)
    {
        this.view=view;
        contactCompanyInteractor = new ContactCompanyInteractor();
    }
    public void onViewLoad(String ownerId)
    {
        contactCompanyInteractor.getCompanyContact(ownerId,this);
    }
    public void onBtnWebsiteClicked()
    {
        view.gotoWebsite();
    }
    public void onBtnPhoneNumberClicked()
    {
        view.gotoCall();
    }
    public void onBtnAddressClicked()
    {
        view.gotoMap(company.location.getLatLng());
    }

    @Override
    public void getCompanyContactSuccess(Company company) {
        if(!isValidPhoneNumber(company.phoneNumber))
            view.hideBtnPhoneNumber();
        view.showContact(company);
        this.company=company;
    }

    @Override
    public void getOnwerContactSuccess(UserInformation user) {
        if(!isValidPhoneNumber(user.getSdt()))
            view.hideBtnPhoneNumber();
        view.showContact(user);
        view.hideBtnWebsite();
        view.hideBtnAddress();
    }

    @Override
    public void getCompanyContactFailure(Exception ex) {
        view.notifyGetContactFailure(ex);
    }
    Boolean isValidPhoneNumber(String phonenumber)
    {
        for(int i=0;i<phonenumber.length();i++)
        {
            if(!(phonenumber.charAt(i)==' ') && !Character.isDigit(phonenumber.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
