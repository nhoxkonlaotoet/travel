package com.example.administrator.travel.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.bases.CardAuthorizationPresenter;
import com.example.administrator.travel.presenters.impls.CardAuthorizationPresenterImpl;
import com.example.administrator.travel.views.bases.CardAuthorizationView;

public class CardAuthorizationActivity extends AppCompatActivity implements CardAuthorizationView, TextView.OnEditorActionListener, View.OnFocusChangeListener {
    private ViewPager vPageCard;
    private LinearLayout lineNotifyAuthorizedCard, lineNotifyUnauthorizedCard;
    private EditText etxtCardNumber, etxtCardHolder, etxtExpirationMonth, etxtExpirationYear, etxtCVC, etxtAmount;
    private Button btnPay;
    private ProgressDialog progressDialog;

    private CardAuthorizationPresenter presenter;
    private int currentFocusViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_authorization);
        mapping();
        setOnEditTextStopTyping();
        setOnButtonPayClick();
        presenter = new CardAuthorizationPresenterImpl(this);
        presenter.onViewCreated(getIntent());
    }

    private void mapping() {
        vPageCard = findViewById(R.id.vPageCard);
        etxtCardNumber = findViewById(R.id.etxtCardNumber);
        etxtCardHolder = findViewById(R.id.etxtCardHolder);
        etxtExpirationMonth = findViewById(R.id.etxtExpirationMonth);
        etxtExpirationYear = findViewById(R.id.etxtExpirationYear);
        etxtCVC = findViewById(R.id.etxtCVC);
        etxtAmount = findViewById(R.id.etxtAmount);
        btnPay = findViewById(R.id.btnPay);
        lineNotifyAuthorizedCard = findViewById(R.id.lineNotifyAuthorizedCard);
        lineNotifyUnauthorizedCard = findViewById(R.id.lineNotifyUnauthorizedCard);
    }

    private void setOnButtonPayClick() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonPayClicked();
            }
        });
    }

    private void setOnEditTextStopTyping() {
        etxtCardNumber.setOnEditorActionListener(this);
        etxtCardNumber.setOnFocusChangeListener(this);
        etxtCardHolder.setOnEditorActionListener(this);
        etxtCardHolder.setOnFocusChangeListener(this);
        etxtExpirationMonth.setOnEditorActionListener(this);
        etxtExpirationMonth.setOnFocusChangeListener(this);
        etxtExpirationYear.setOnEditorActionListener(this);
        etxtExpirationYear.setOnFocusChangeListener(this);
        etxtCVC.setOnEditorActionListener(this);
        etxtCVC.setOnFocusChangeListener(this);
    }

    @Override
    public void showLineNotifyAuthorizedCard() {
        lineNotifyAuthorizedCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLineNotifyAuthorizedCard() {
        lineNotifyAuthorizedCard.setVisibility(View.GONE);
    }

    @Override
    public void showLineNotifyUnauthorizedCard() {
        lineNotifyUnauthorizedCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLineNotifyUnauthorizedCard() {
        lineNotifyUnauthorizedCard.setVisibility(View.GONE);
    }

    @Override
    public void showButtonPay() {
        btnPay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtonPay() {
        btnPay.setVisibility(View.GONE);
    }

    @Override
    public void enableEtxtCardNumber() {
        etxtCardNumber.setEnabled(true);
    }

    @Override
    public void disableEtxtCardNumber() {
        etxtCardNumber.setEnabled(false);
    }

    @Override
    public void enableEtxtCardHolder() {
        etxtCardHolder.setEnabled(true);
    }

    @Override
    public void disableEtxtCardHolder() {
        etxtCardHolder.setEnabled(false);
    }

    @Override
    public void enableEtxtExpirationMonth() {
        etxtExpirationMonth.setEnabled(true);
    }

    @Override
    public void disableEtxtExpirationMonth() {
        etxtExpirationMonth.setEnabled(false);
    }

    @Override
    public void enableEtxtExpirationYear() {
        etxtExpirationYear.setEnabled(true);
    }

    @Override
    public void disableEtxtExpirationYear() {
        etxtExpirationYear.setEnabled(false);
    }

    @Override
    public void enableEtxtCVC() {
        etxtCVC.setEnabled(true);
    }

    @Override
    public void disableEtxtCVC() {
        etxtCVC.setEnabled(false);
    }

    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(this, "",
                "", true);
    }

    @Override
    public void closeDialog() {
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

    @Override
    public void showAmount(Integer amount) {
        etxtAmount.setText(String.valueOf(amount)+" đồng");
    }

    @Override
    public void notify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closeForResult(Boolean result) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("authorized", result);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void close() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                if (currentFocusViewId == textView.getId())
                    return false;
                currentFocusViewId = textView.getId();
                switch (textView.getId()) {
                    case R.id.etxtCardNumber:
                        presenter.onEtxtCardNumberStopedTyping(etxtCardNumber.getText().toString());
                        break;
                    case R.id.etxtCardHolder:
                        presenter.onEtxtCardHolderStopedTyping(etxtCardHolder.getText().toString());
                        break;
                    case R.id.etxtExpirationMonth:
                        presenter.onEtxtExpirationMonthStopedTyping(etxtExpirationMonth.getText().toString());
                        break;
                    case R.id.etxtExpirationYear:
                        presenter.onEtxtExpirationYearStopedTyping(etxtExpirationYear.getText().toString());
                        break;
                    case R.id.etxtCVC:
                        presenter.onEtxtCVCStopedTyping(etxtCVC.getText().toString());
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            if (currentFocusViewId == view.getId())
                return;
            currentFocusViewId = view.getId();
            switch (view.getId()) {
                case R.id.etxtCardNumber:
                    presenter.onEtxtCardNumberStopedTyping(etxtCardNumber.getText().toString());
                    break;
                case R.id.etxtCardHolder:
                    presenter.onEtxtCardHolderStopedTyping(etxtCardHolder.getText().toString());
                    break;
                case R.id.etxtExpirationMonth:
                    presenter.onEtxtExpirationMonthStopedTyping(etxtExpirationMonth.getText().toString());
                    break;
                case R.id.etxtExpirationYear:
                    presenter.onEtxtExpirationYearStopedTyping(etxtExpirationYear.getText().toString());
                    break;
                case R.id.etxtCVC:
                    presenter.onEtxtCVCStopedTyping(etxtCVC.getText().toString());
                    break;
                default:
                    break;
            }
        }
    }
}
