package com.example.administrator.travel.views.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnSend;
    ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
//        setWidgets
        edtEmail = (EditText) findViewById(R.id.edt_resetpass_email);
        btnSend = (Button) findViewById(R.id.btn_resetpass_send);
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_resetpass_back);

    }//end onCreate

    //    methods
    public void SendResetPass(View view){
        String email = edtEmail.getText ().toString ().trim();
        if(email.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập địa chỉ email!", Toast.LENGTH_SHORT).show();
        }else {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassActivity.this,
                                        "Thông tin xác thực đã được gửi đi!\n Hãy kiểm tra Mail.",
                                        Toast.LENGTH_SHORT).show();
                                Intent info_intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(info_intent);
                                finish();
                            } else
                                Toast.makeText(ResetPassActivity.this, "Mail không chính xác!",
                                        Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }//end SendResetPass

    public void PressBackButton(View view){
        finish();
    }//end PressBackButton
}
