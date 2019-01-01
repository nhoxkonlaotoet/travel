package com.example.administrator.travel.views.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.PictureItem;
import com.example.administrator.travel.models.LoadImageTask;
import com.example.administrator.travel.models.OnDownloadImageFinishedListener;
import com.example.administrator.travel.presenters.PostPresenter;
import com.example.administrator.travel.views.PostView;

import java.io.File;

public class PostActivity extends AppCompatActivity implements PostView {
    RelativeLayout btnOpenPicture;
    GridLayout layoutPictures;
    PictureItem pictureItem;
    View.OnClickListener clickListener;
    TextView txtFileCount;
    PostPresenter presenter;
    ScrollView scrollviewPicture;
        String tourStartId;
    EditText edittxtContent;
    Button btnPost;
    ImageButton btnBack;
    int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mapping();

        Bundle bundle = getIntent().getExtras();
        tourStartId= bundle.getString("tourStartId");


        layoutPictures.setColumnCount(3);
        createOnPictureClick();
        setOnBtnOpenPictureClick();
        setOnBtnPostClick();
        setOnBtnBackClick();

        presenter = new PostPresenter(this);
        presenter.onViewLoad();
    }

    void mapping(){
        edittxtContent= findViewById(R.id.edittxtContent);
        btnPost = findViewById(R.id.btnPost);
        btnBack = findViewById(R.id.btnBack);
        btnOpenPicture = findViewById(R.id.btnOpenPicture);
        layoutPictures = findViewById(R.id.layoutPictures);
        scrollviewPicture = findViewById(R.id.scrollviewPicture);
        txtFileCount = findViewById(R.id.txtFileCount);
        edittxtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideLayoutPicture();
            }
        });
    }
    void createOnPictureClick(){
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PictureItem)view).onClicked();
                presenter.onPitureItemClicked((PictureItem)view);
            }
        };
    }
    void setOnPictureClick(View view){
        view.setOnClickListener(clickListener);
    }

    void setOnBtnOpenPictureClick(){
        btnOpenPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnPictureClicked();
            }
        });
    }
    void setOnBtnPostClick(){
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnPostClicked(tourStartId,edittxtContent.getText().toString());
            }
        });
    }
    void setOnBtnBackClick(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnBackClicked();
            }
        });
    }
    @Override
    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public void showLayoutPicture() {
        scrollviewPicture.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutPicture() {
        scrollviewPicture.setVisibility(View.GONE);
    }

    @Override
    public void showFramePictures(int length) {
        layoutPictures.setRowCount(length/3);
        ViewGroup.LayoutParams params =new ViewGroup.LayoutParams(getScreenWidth()/3,getScreenWidth()/3);

        for(int i=0;i<length;i++) {
            pictureItem = new PictureItem(this);
            pictureItem.setLayoutParams(params);
            layoutPictures.addView(pictureItem,i);
            setOnPictureClick(pictureItem);
        }
    }

    @Override
    public void addPicture(int index, Bitmap bitmap, String path) {
        ((PictureItem)layoutPictures.getChildAt(index)).setImage(bitmap);
        ((PictureItem)layoutPictures.getChildAt(index)).setImagePath(path);

    }

    @Override
    public void showFileCount(int count) {
        if(count!=0)
            txtFileCount.setText(count+" ảnh");
        else
            txtFileCount.setText("");
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
    public void close() {
        finish();
    }
}