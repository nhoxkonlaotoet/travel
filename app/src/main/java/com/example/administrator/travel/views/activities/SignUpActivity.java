package com.example.administrator.travel.views.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.SignUpInteractorImpl;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.SignUpPresenter;
import com.example.administrator.travel.presenters.SignUpPresenterImpl;
import com.example.administrator.travel.views.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements SignUpView{

    EditText edtName, edtEmail, edtPass, edtAgainPass;
    RadioButton rbtnMale;
    Button btnSignUp;
    TextView tvLogin;
    FirebaseAuth mAuth;
    String urlAvatarDefault="https://firebasestorage.googleapis.com/v0/b/travel-76809.appspot.com/o/default_image.png?alt=media&token=8b803a01-ac08-4c09-b167-1fc3b4c7eca8";

    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//
        mAuth =FirebaseAuth.getInstance();
//      Link to LogIn
        ClickableSpan clickLogIn = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity((new Intent(SignUpActivity.this, LoginActivity.class))
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                finish();
            }
        };

        SpannableString stringLogIn = new SpannableString(getString(R.string.move_login));
        stringLogIn.setSpan(clickLogIn, stringLogIn.length()-17, stringLogIn.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvLogin = (TextView) findViewById(R.id.tv_link_login);
        tvLogin.setText(stringLogIn, TextView.BufferType.SPANNABLE);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
//        setWidgets
        edtName = (EditText) findViewById(R.id.edt_signup_name);
        edtEmail = (EditText) findViewById(R.id.edt_signup_email);
        edtPass = (EditText) findViewById(R.id.edt_signup_pass);
        edtAgainPass = (EditText) findViewById(R.id.edt_signup_againpass);
        rbtnMale = (RadioButton) findViewById(R.id.rbtn_signup_male);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
//
        presenter = new SignUpPresenterImpl(this);

    }//end onCreate

//    override Methods
    public void SignUpClick(View view){
        final String mUser = edtEmail.getText ().toString ();
        String mPassword = edtPass.getText ().toString ();
        String mCheckPass = edtAgainPass.getText ().toString ();
        final String mName = edtName.getText ().toString ();
        int checkRegister =presenter.CheckRegister (mUser, mPassword, mCheckPass);
        if ( checkRegister == 0) {
            String mSex = new String ();
            if (rbtnMale.isChecked ())
                mSex = "Nam";
            else
                mSex = "Nữ";

            final String finalMSex = mSex;
            mAuth.createUserWithEmailAndPassword (mUser, mPassword)
                    .addOnCompleteListener (this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful ()) {

                                //
                                FirebaseUser user = mAuth.getCurrentUser ();
                                UserInformation info = new UserInformation (mName, finalMSex, mUser, "none",urlAvatarDefault);

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance ().getReference ();
                                databaseReference.child("users").child (user.getUid ()).setValue (info);
                                //
                                Toast.makeText (SignUpActivity.this,
                                        "Đăng ký thành công! \nMời bạn đăng nhập lại", Toast.LENGTH_SHORT).show ();

                                startActivity((new Intent(SignUpActivity.this,LoginActivity.class))
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }else if(task.getException().toString().trim()
                                    .equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.")) {
                                Toast.makeText(SignUpActivity.this, "Vui lòng kiểm tra lại email hợp lệ!", Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(SignUpActivity.this, "Lỗi tạo tài khoản!\nCó thể do email đã tồn tại!", Toast.LENGTH_SHORT).show();
                            // ... End complete Register
                        }
                    });
        }else if(checkRegister == 1){
            Toast.makeText (this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show ();
        }else if(checkRegister == 2){
            Toast.makeText (this, "Mật khẩu phải ít nhất 6 kí tự!", Toast.LENGTH_SHORT).show ();
        }else if(checkRegister == 3){
            Toast.makeText (this, "Mật khẩu không trùng!", Toast.LENGTH_SHORT).show ();
        }


    }//end SignUpClick
}
