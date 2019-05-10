package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.administrator.travel.models.listeners.Listener;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 12/05/2018.
 */
public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    Listener.OnDownloadImageFinishedListener listener;
    int pos;
    public DownLoadImageTask(Listener.OnDownloadImageFinishedListener listener){
        this.listener=listener;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        Bitmap logo = null;
        try{
            pos=Integer.parseInt(urls[0]);
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
                listener.onDownloadImageFail(e);
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        if(listener!=null)
            listener.onDownloadImageSuccess(pos,result);
        //Log.e( "onPostExecute: ", result.getByteCount()+"");
    }

}
