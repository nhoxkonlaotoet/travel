package com.example.administrator.travel.views.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.administrator.travel.R;


import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.presenters.bases.LoginPresenter;
import com.example.administrator.travel.presenters.impls.LoginPresenterImpl;
import com.example.administrator.travel.views.bases.LoginView;


import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements LoginView {
    EditText edtEmail, edtPassword;
    TextView tvSignUp, tvResetPass;
    ProgressDialog mProgress;
    LoginPresenter presenter;

    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    public static final int MULTIPLE_PERMISSIONS = 2019;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //requestCode = getIntent().getIntExtra("requestCode", 0);

        checkPermissions();
        mapping();
        setResetPassClick();
        setSignUpClick();
        presenter= new LoginPresenterImpl(this);
        presenter.onViewCreated();
    }

    void mapping() {

        tvResetPass = findViewById(R.id.tv_link_resetpass);
        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword = findViewById(R.id.edt_login_password);
        tvSignUp = findViewById(R.id.tv_link_signup);
    }

    void setSignUpClick() {
        ClickableSpan clickSignUp = new ClickableSpan() {
            @Override
            public void onClick(View view) {
               presenter.onTextSignUpClicked();
            }
        };
        SpannableString stringSignUp = new SpannableString(getString(R.string.move_signup));
        stringSignUp.setSpan(clickSignUp, stringSignUp.length() - 15, stringSignUp.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignUp.setText(stringSignUp, TextView.BufferType.SPANNABLE);
        tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    void setResetPassClick() {
        ClickableSpan clickResetPass = new ClickableSpan() {
            @Override
            public void onClick(View view) {
               presenter.onTextResetPasswordClicked();
            }
        };
        SpannableString stringResetPass = new SpannableString(getString(R.string.move_resetPass));
        stringResetPass.setSpan(clickResetPass, stringResetPass.length() - 18, stringResetPass.length(),
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvResetPass.setText(stringResetPass, TextView.BufferType.SPANNABLE);
        tvResetPass.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void LoginClick(View view) {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString();
        presenter.onBtnLoginClicked(email,pass);

    }

//    public void SignUp(final String mEmail, final String mPass) {
//        mAuth.signInWithEmailAndPassword(mEmail, mPass)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                            if (requestCode == LOGIN_CODE) {
//                                Log.e("onComplete: ", requestCode + "");
//                                finish();
//                            }
//                            startActivity((new Intent(LoginActivity.this, PreLoadingActivity.class))
//                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//                            finish();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại Email và mật khẩu...",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void gotoResetPassActivity() {
        Intent intent = new Intent(getContext(), ResetPassActivity.class);
        startActivity(intent);

    }

    @Override
    public void gotoSignUpActivity() {
        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void gotoPreLoadingActivity() {
        Intent intent = new Intent(getContext(), PreLoadingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showLoginDialog() {
            mProgress = ProgressDialog.show(LoginActivity.this, "Đăng nhập", "Vui lòng đợi...");
    }

    @Override
    public void closeLoginDialog() {
        if (mProgress.isShowing())
            mProgress.dismiss();

    }

    @Override
    public void notifyLoginFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext(){
        return this;
    }
}
