package com.example.administrator.travel.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.administrator.travel.R;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;

    EditText edtPhone, edtCode;
    Button btnSend;
    TextView tvWaitTime;

    String mVerificationId;
    Timer timer;
    Boolean mVerified = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPhone = (EditText) findViewById(R.id.edt_login_number);
        edtCode = (EditText) findViewById(R.id.edt_login_code);
        btnSend = (Button) findViewById(R.id.btn_login);
        tvWaitTime = (TextView) findViewById(R.id.tv_login_waittime);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

//                Log.d("TAG", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login), "Verification Failed !! Invalied verification Code", Snackbar.LENGTH_LONG).show();
                }
                else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login), "Verification Failed !! Too many request. Try after some time. ", Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSend.getTag().equals(getResources().getString(R.string.tag_send))) {
                    if (!edtPhone.getText().toString().trim().isEmpty() && edtPhone.getText().toString().trim().length() >= 10) {
                        startPhoneNumberVerification(edtPhone.getText().toString().trim());
                        mVerified = false;
                        starttimer();
                        btnSend.setTag(getResources().getString(R.string.tag_verify));
                        btnSend.setText("Verify");
                        tvWaitTime.setVisibility(View.VISIBLE);
                        btnSend.setEnabled(false);
                    }
                    else {
                        edtPhone.setError("Please enter valid mobile number");
                    }
                }

                if (btnSend.getTag().equals(getResources().getString(R.string.tag_verify))) {
                    if (!edtCode.getText().toString().trim().isEmpty() && !mVerified) {
                        Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login), "Please wait...",
                                Snackbar.LENGTH_LONG).show();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,
                                edtCode.getText().toString().trim());

                        signInWithPhoneAuthCredential(credential);
                    }
                    if (mVerified) {

//                            startActivity(new Intent(MainActivity.this, SuccessActivity.class));

                    }

                }


            }
        });

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edtCode.getText().toString().trim().isEmpty()){
                    btnSend.setEnabled(true);
                }
            }
        });

        tvWaitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPhone.getText().toString().trim().isEmpty() && edtPhone.getText().toString().trim().length() >= 10) {
                    resendVerificationCode(edtPhone.getText().toString().trim(), mResendToken);
                    mVerified = false;
                    starttimer();

                    btnSend.setTag(getResources().getString(R.string.tag_verify));
                    Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login), "Resending verification code...",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            mVerified = true;
                            timer.cancel();

                            Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login),
                                    "Successfully Verified", Snackbar.LENGTH_LONG).show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Snackbar.make((RelativeLayout) findViewById(R.id.parentlayout_login),
                                                "Invalid OTP ! Please enter correct OTP", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    public void starttimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 60;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvWaitTime.setText("RESEND CODE");
                            timer.cancel();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvWaitTime.setText("Please wait 00:" + second--);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

}
