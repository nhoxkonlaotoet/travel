package com.example.administrator.travel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 31/12/2018.
 */
import com.example.administrator.travel.R;
public class PictureItem extends RelativeLayout {
    public ImageView imageView;
    CheckBox checkBox;
    String imagePath="";
    Bitmap bitmap;
    @SuppressLint("ResourceAsColor")
    public PictureItem(Context context) {
        super(context);
        imageView = new ImageView(context);
        checkBox = new CheckBox(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(imageView);
        this.addView(checkBox);
        checkBox.setVisibility(INVISIBLE);
        checkBox.setEnabled(false);
    }
    public void onClicked()
    {
        if (checkBox.isChecked())
            checkBox.setChecked(false);
        else
            checkBox.setChecked(true);
    }

    public void setImage(Bitmap bitmap){
        if(imageView!=null) {
            this.bitmap=bitmap;
            imageView.setImageBitmap(bitmap);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height=this.getHeight();
            params.width=this.getWidth();
            imageView.setLayoutParams(params);
            checkBox.setX(getHeight()-60);
            checkBox.setVisibility(VISIBLE);
        }
    }

    public boolean isChosen()
    {
        return checkBox.isChecked();
    }
    public void setImagePath(String path){
        imagePath=path;
    }
    public String getImagePath()
    {
        return imagePath;
    }
    public Bitmap getImageBitmap(){
        return bitmap;
    }
}
