package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.StoragePictureAdapter;
import com.example.administrator.travel.presenters.bases.PostPresenter;
import com.example.administrator.travel.presenters.impls.PostPresenterImpl;
import com.example.administrator.travel.views.bases.PostView;

import java.io.File;
import java.util.List;

public class PostActivity extends AppCompatActivity implements PostView, StoragePictureAdapter.PictureClickListener {
    RelativeLayout btnOpenPicture,btnMarkLocation;
    TextView txtFileCount;
    PostPresenter presenter;
    EditText edittxtContent;
    Button btnPost;
    ImageButton btnBack;
    RecyclerView recyclerViewPicture;
    StoragePictureAdapter storagePictureAdapter;
    int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mapping();
        Bundle bundle = getIntent().getExtras();
        setOnBtnOpenPictureClick();
        setOnBtnPostClick();
        setOnBtnBackClick();
        setOnBtnMarkLocationClick();
        presenter = new PostPresenterImpl(this);
        presenter.onViewCreated(bundle);
    }

    void mapping(){
        edittxtContent= findViewById(R.id.edittxtContent);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);
        btnOpenPicture = findViewById(R.id.btnOpenPicture);
        txtFileCount = findViewById(R.id.txtFileCount);
        btnMarkLocation = findViewById(R.id.btnMarkLocation);
        recyclerViewPicture=findViewById(R.id.recyclerviewPicture);
        edittxtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEditTextContentClicked();
            }
        });
    }


    void setOnBtnMarkLocationClick(){
        btnMarkLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonMarkLocationClicked();
            }
        });
    }
    void setOnBtnOpenPictureClick(){
        btnOpenPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonPictureClicked();
            }
        });
    }
    void setOnBtnPostClick(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonSendClicked(edittxtContent.getText().toString());
            }
        });
    }
    void setOnBtnBackClick(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonBackClicked();
            }
        });
    }



    @Override
    public void showLayoutPicture() {
        recyclerViewPicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutPicture() {
        recyclerViewPicture.setVisibility(View.GONE);
    }

    @Override
    public void showFramePictures(int length, File[] filenameList) {
        storagePictureAdapter = new StoragePictureAdapter(this, length, filenameList);
        storagePictureAdapter.setClickListener(this);
        recyclerViewPicture.setAdapter(storagePictureAdapter);
        recyclerViewPicture.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void addPicture(String name, Bitmap bitmap) {
      storagePictureAdapter.updateImage(name, bitmap);
    }

    @Override
    public void showFileCount(int count) {
        if(count!=0)
            txtFileCount.setText(count+" ảnh");
        else
            txtFileCount.setText("");
    }

    @Override
    public void gotoMapActivity(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void viewOnPost() {
        edittxtContent.setHint("Bạn sẽ làm gì trong hôm nay?");
    }

    @Override
    public void viewOnReview() {
        edittxtContent.setHint("Bạn nghĩ gì sau chuyến đi?");

    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void finishViewReturnResult(int resultCode){
        Log.e( "finishViewReturnOK: ","_________________" );
        Intent returnIntent = new Intent();
        setResult(resultCode,returnIntent);
        finish();
    }
    @Override
    public void notifyFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    public void onPictureClick(View view, Bitmap image) {
        presenter.onPictureItemClicked(view, image);
    }
}