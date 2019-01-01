package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 12/05/2018.
 */
public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    OnDownloadImageFinishedListener listener;
    int id;
    public DownLoadImageTask(OnDownloadImageFinishedListener listener){
        this.listener=listener;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        Bitmap logo = null;
        try{
            id= Integer.parseInt(urls[0]);
            String urlOfImage = urls[1];

            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
            if(listener!=null)
                listener.onDownloadImageFailure(e);
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        if(listener!=null)
            listener.onDownloadImageSuccess(id,result);
        //Log.e( "onPostExecute: ", result.getByteCount()+"");
    }

}
