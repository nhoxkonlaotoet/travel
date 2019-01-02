package com.example.administrator.travel.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.UpdateInfoAccountPresenter;
import com.example.administrator.travel.presenters.UpdateInfoAccountPresenterImpl;
import com.example.administrator.travel.views.UpdateInfoAccountView;

import java.io.IOException;

/**
 * Created by Henry
 */

public class UpdateInfoAccountActivity extends AppCompatActivity implements UpdateInfoAccountView{

    de.hdodenhof.circleimageview.CircleImageView imgAvatar;
    Button btnSave,btnPhotos;
    EditText edtName, edtSDT;
    RadioButton rbtnMale, rbtnFemale;
    UserInformation userInfo;
    android.content.SharedPreferences sharedPreferences;
    Bitmap bitmapAvatar;
    UpdateInfoAccountPresenter updateInfoAccountPresenter;

    private static final int PICK_IMAGE_REQUEST = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_account);

        imgAvatar = (de.hdodenhof.circleimageview.CircleImageView)findViewById (R.id.img_update_info_avatar);
        btnPhotos=(Button)findViewById (R.id.btn_update_info_photos);
        btnSave=(Button)findViewById (R.id.btn_update_info_save);
        edtName = (EditText) findViewById (R.id.edt_update_info_name);
        edtSDT=(EditText) findViewById (R.id.edt_update_info_phone);
        rbtnMale=(RadioButton) findViewById (R.id.rbtn_update_info_male);
        rbtnFemale=(RadioButton) findViewById (R.id.rbtn_update_info_female);

        Intent edit_intent = getIntent();
        userInfo=new UserInformation();
        userInfo.setName(edit_intent.getStringExtra("name"));
        userInfo.setSdt(edit_intent.getStringExtra("sdt"));
        userInfo.setMail(edit_intent.getStringExtra("email"));
        userInfo.setSex(edit_intent.getStringExtra("sex"));
        userInfo.setUrlAvatar(edit_intent.getStringExtra("urlAvatar"));

        edtName.setText(userInfo.getName());
        if(userInfo.getSdt().equals("none"))
            edtSDT.setText("");
        else
            edtSDT.setText(userInfo.getSdt());

        if ( userInfo.getSex().equals("Nam") )
            rbtnMale.setChecked (true);
        else
            rbtnFemale.setChecked (true);


        com.squareup.picasso.Picasso.with(getBaseContext())
                .load(userInfo.getUrlAvatar())
                .into (imgAvatar);

        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(Intent.createChooser(new Intent()
                        .setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT), "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        //presenter.get
        updateInfoAccountPresenter = new UpdateInfoAccountPresenterImpl(this);
//       sharedPreferences
        sharedPreferences = getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbtnMale.isChecked())
                    userInfo.setSex("Nam");
                else
                    userInfo.setSex("Nu");
                userInfo.setSdt(edtSDT.getText().toString().trim());
                userInfo.setName(edtName.getText().toString().trim());
                if(bitmapAvatar == null) {
                    // Get the data from an ImageView
                    imgAvatar.setDrawingCacheEnabled(true);
                    imgAvatar.buildDrawingCache();

                    bitmapAvatar = imgAvatar.getDrawingCache();
                }

                updateInfoAccountPresenter.updateInfoAccountPresenter(sharedPreferences,bitmapAvatar,userInfo);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                bitmapAvatar = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgAvatar.setImageBitmap(bitmapAvatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showResultUpdate(int statusCode) {
        if(statusCode == 0){//fail upload image
            Toast.makeText (this, "Lỗi!\n Hãy sử dụng hình ảnh khác!!", Toast.LENGTH_SHORT).show ();
        }
        if(statusCode == 1){//success
            Toast.makeText (this, "Thành công! \n Thông tin của bạn đã được cập nhất.", Toast.LENGTH_SHORT).show ();
            finish();
        }
    }
    //    onClick button
    public void OnFinishBackPress(View view){
        finish();
    }
}

