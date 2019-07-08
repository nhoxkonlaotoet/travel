package com.example.administrator.travel.views.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.bases.UpdateUserProfilePresenter;
import com.example.administrator.travel.presenters.impls.UpdateUserProfilePresenterImpl;
import com.example.administrator.travel.views.bases.UpdateUserProfileView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateUserProfileActivity extends AppCompatActivity
        implements UpdateUserProfileView, TextView.OnEditorActionListener, View.OnFocusChangeListener {

    Toolbar toolbar;
    EditText etxtUserName,etxtPhoneNumber;
    TextView txtUserDOB;
    RadioButton rbtnMale, rbtnFemale;
    CircleImageView imgvUserAvatar, imgvChangeUserAvatar;
    Button btnUpdateUserInfor;
    Dialog dialogChooseCameraOrGallery;
    UpdateUserProfilePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        mapping();
        setOnButtonUpdateUserInforClick();
        setOnEditTextStopTyping();
        setOnRadioButtonCheckedChange();
        setOnImageChangeAvatarClick();
        presenter=new UpdateUserProfilePresenterImpl(this);
        presenter.onViewCreated(null);
    }

    private void mapping(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        etxtUserName = findViewById(R.id.etxtUserName);
        txtUserDOB = findViewById(R.id.txtUserDOB);
        rbtnMale = findViewById(R.id.rbtnMale);
        rbtnFemale = findViewById(R.id.rbtnFemale);
        imgvUserAvatar = findViewById(R.id.imgvUserAvatar);
        imgvChangeUserAvatar = findViewById(R.id.imgvChangeAvatar);
        etxtPhoneNumber=findViewById(R.id.etxtPhoneNumber);
        btnUpdateUserInfor=findViewById(R.id.btnUpdateUserInfor);
    }
    private void setOnImageChangeAvatarClick(){
        imgvChangeUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onImageViewChangeAvatarClicked();
            }
        });
    }

    private void setOnButtonUpdateUserInforClick(){
        btnUpdateUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonUpdateUserInforClicked();
            }
        });
    }
    private void setOnEditTextStopTyping(){
        etxtUserName.setOnEditorActionListener(this);
        etxtUserName.setOnFocusChangeListener(this);
        etxtPhoneNumber.setOnEditorActionListener(this);
        etxtPhoneNumber.setOnFocusChangeListener(this);
    }
    private void setOnRadioButtonCheckedChange(){
        rbtnMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    presenter.onRadioButtonMaleChecked();
            }
        });
        rbtnFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    presenter.onRadioButtonFemaleChecked();
            }
        });
    }
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent != null &&
                keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (keyEvent == null || !keyEvent.isShiftPressed()) {
                switch (textView.getId()) {
                    case R.id.etxtUserName:
                        presenter.onEditTextUserNameTypingStoped(etxtUserName.getText().toString());
                        break;
                    case R.id.etxtPhoneNumber:
                        presenter.onEditTextPhoneNumberTypingStoped(etxtPhoneNumber.getText().toString());
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
            switch (view.getId()) {
                case R.id.etxtUserName:
                    presenter.onEditTextUserNameTypingStoped(etxtUserName.getText().toString());
                    break;
                case R.id.etxtPhoneNumber:
                    presenter.onEditTextPhoneNumberTypingStoped(etxtPhoneNumber.getText().toString());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onButtonBackClicked();
    }
    @Override
    public void showUserInfor(UserInformation user) {
        etxtUserName.setText(user.name);
        if(user.isMale)
            rbtnMale.setChecked(true);
        else
            rbtnFemale.setChecked(true);
        txtUserDOB.setText(user.dateOfBirth+"/"+user.monthOfBirth+"/"+user.yearOfBirth);
        etxtPhoneNumber.setText(user.sdt);
    }

    @Override
    public void showUserAvatar(Bitmap avatar) {
        imgvUserAvatar.setImageBitmap(avatar);
    }

    @Override
    public void setEditTextUserNameText(String userName) {
        etxtUserName.setText(userName);
    }

    @Override
    public void setEdittextPhoneNumberText(String phoneNumber) {
        etxtPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void setTextViewDOBText(String dob) {
        txtUserDOB.setText(dob);
    }

    @Override
    public void setImageViewAvatarBitmap(Bitmap avatar) {
        imgvUserAvatar.setImageBitmap(avatar);
    }

    @Override
    public void showDialogChooseCameraOrGallery() {
        dialogChooseCameraOrGallery = new Dialog(this);
        dialogChooseCameraOrGallery.setCancelable(true);
        dialogChooseCameraOrGallery.setTitle("Cập nhật ảnh đại diện");
        dialogChooseCameraOrGallery.setContentView(R.layout.dialog_open_camera_or_gallery);
        TextView txtOpenCamera = dialogChooseCameraOrGallery.findViewById(R.id.txtOpenCamera);
        TextView txtOpenGallery = dialogChooseCameraOrGallery.findViewById(R.id.txtOpenGallery);

        txtOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTextViewOpenCameraClicked();
            }
        });
        txtOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onTextViewOpenGalleryClicked();
            }
        });
        dialogChooseCameraOrGallery.show();
    }

    @Override
    public void closeDialogChooseCameraOrGallery() {
        try {
            dialogChooseCameraOrGallery.cancel();
        }
        catch (Exception ex){}
    }

    @Override
    public void openCamera(int cameraCode) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, cameraCode);
    }

    @Override
    public void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    @Override
    public void notify(String message) {
        Snackbar.make(imgvUserAvatar, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode,resultCode,data);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close(int resultCode) {
        Intent returnIntent = new Intent();
        setResult(resultCode, returnIntent);
        finish();
    }


}
