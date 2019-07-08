package com.example.administrator.travel.views.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ActivitySuggestionAdapter;
import com.example.administrator.travel.adapter.CityAdapter;
import com.example.administrator.travel.adapter.NearbyAdapter;
import com.example.administrator.travel.models.entities.ActivitySuggestion;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.presenters.bases.CreateActivityPresenter;
import com.example.administrator.travel.presenters.impls.CreateActivityPresenterImpl;
import com.example.administrator.travel.views.bases.CreateActivityView;

import java.util.List;

public class CreateActivityActivity extends AppCompatActivity
        implements CreateActivityView, ActivitySuggestionAdapter.ActivtySuggestionListener, NearbyAdapter.NearbyClickListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {
    private RecyclerView recyclerViewActivitySuggest, recyclerViewPlaceSuggest;
    private TextView txtPlaceSuggestAddress;
    private EditText etxtActivityContent;
    private LinearLayout layoutPlaceAddress, layoutPlaceSuggest;
    private Toolbar toolbar;
    private Button btnPost;
    private NearbyAdapter nearbyAdapter;
    private CreateActivityPresenter presenter;
    private ProgressDialog waitForLocationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_activity);
        mapping();
        setActionbar();
        setMultilinesActionDoneEditTextContent();
        setOnEditTextContentStopTyping();
        setOnEditTextContentClick();
        Bundle bundle = getIntent().getExtras();
        presenter = new CreateActivityPresenterImpl(this);
        presenter.onViewCreated(bundle);
    }

    private void mapping() {
        recyclerViewActivitySuggest = findViewById(R.id.recyclerViewActivitySuggest);
        recyclerViewPlaceSuggest = findViewById(R.id.recyclerViewPlaceSuggest);
        layoutPlaceAddress = findViewById(R.id.layoutPlaceAddress);
        layoutPlaceSuggest = findViewById(R.id.layoutPlaceSuggest);
        etxtActivityContent = findViewById(R.id.etxtActivityContent);
        btnPost = findViewById(R.id.btnPost);
        txtPlaceSuggestAddress = findViewById(R.id.txtPlaceSuggestAddress);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setActionbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
    }

    private void setMultilinesActionDoneEditTextContent() {
        etxtActivityContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etxtActivityContent.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void setOnEditTextContentClick() {
        etxtActivityContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onEditTextContentClicked();
            }
        });
    }
    private void setOnEditTextContentStopTyping(){
        etxtActivityContent.setOnFocusChangeListener(this);
        etxtActivityContent.setOnEditorActionListener(this);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
            presenter.onEditTextContentTypingStoped();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent != null &&
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&(
                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                presenter.onEditTextContentTypingStoped();
                Log.e( "onEditorAction: ","stop" );
            }
        }
        return false;
    }

    @Override
    public void showActivitySuggestions(List<ActivitySuggestion> activitySuggestionList) {
        ActivitySuggestionAdapter activitySuggestionAdapter = new ActivitySuggestionAdapter(this, activitySuggestionList);
        activitySuggestionAdapter.setClickListener(this);
        recyclerViewActivitySuggest.setAdapter(activitySuggestionAdapter);
    }


    @Override
    public void onActivtySuggestionClick(View view, ActivitySuggestion activitySuggestion) {
        presenter.onItemActivitySuggestClicked(activitySuggestion);
    }

    @Override
    public void showNearby(List<Nearby> nearbyList) {
        nearbyAdapter = new NearbyAdapter(this, nearbyList, null, false);
        nearbyAdapter.setClickListener(this);
        recyclerViewPlaceSuggest.setAdapter(nearbyAdapter);
    }

    @Override
    public void appendNearby(List<Nearby> nearbyList) {
        nearbyAdapter.appendItems(nearbyList);
    }

    @Override
    public void setTextEditTextContent(String text) {
        etxtActivityContent.setText(text);
    }

    @Override
    public void showWaitForLocationDialog() {
        waitForLocationDialog = ProgressDialog.show(this, "", getResources().getString(R.string.wait_for_location)
                , true);
    }

    @Override
    public void closeWaitForLocationDialog() {
        if (waitForLocationDialog != null && waitForLocationDialog.isShowing())
            waitForLocationDialog.cancel();
    }

    @Override
    public void gotoMapsActivity(String placeId) {
        Intent intent = new Intent(CreateActivityActivity.this, MapsActivity.class);
        intent.putExtra("placeId", placeId);
        startActivity(intent);
    }

    @Override
    public void gotoPlaceSearchActivity(int searchCode) {
        Intent intent = new Intent(CreateActivityActivity.this, MapsActivity.class);
        startActivityForResult(intent, searchCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode, resultCode, data);
    }

    @Override
    public void showPlaceAddress(String address) {
        SpannableString content = new SpannableString(address);
        content.setSpan(new UnderlineSpan(), 0, address.length(), 0);
        txtPlaceSuggestAddress.setText(content);
    }

    @Override
    public void showLayoutPlaceAddress() {
        layoutPlaceAddress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutPlaceAddress() {
        layoutPlaceAddress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLayoutPlaceSuggest() {
        layoutPlaceSuggest.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutPlaceSuggest() {
        layoutPlaceSuggest.setVisibility(View.GONE);
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onNearbyClick(View view, Nearby nearby) {
        presenter.onItemPlaceSuggestClicked(nearby);
    }
}
