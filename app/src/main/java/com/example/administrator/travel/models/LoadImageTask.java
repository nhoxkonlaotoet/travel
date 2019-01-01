package com.example.administrator.travel.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 31/12/2018.
 */

public class LoadImageTask extends AsyncTask<String,Void,Bitmap> {
    OnLoadImageFinishedListener listener;
    int index;
    String path;
    public LoadImageTask(OnLoadImageFinishedListener listener){
        this.listener=listener;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        Bitmap logo = null;
        try{
            index= Integer.parseInt(urls[0]);
            path = urls[1];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=15;

            logo = BitmapFactory.decodeFile(path,options);

        }catch(Exception e){
            e.printStackTrace();
            if(listener!=null)
                listener.onGetImageFailure(e);
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        if(listener!=null)
            listener.onGetImageSuccess(index,result,path);
        //Log.e( "onPostExecute: ", result.getByteCount()+"");
    }

}
