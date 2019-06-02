package com.example.administrator.travel.models.impls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.listeners.Listener;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Admin on 5/30/2019.
 */

public class ExternalStorageInteractorImpl implements ExternalStorageInteractor {
    final static String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    @Override
    public boolean isExistFile(String path, String fileName) {
        File file = new File(ROOT+path, fileName+".jpg");
        return file.exists();
    }

    @Override
    public void saveBitmapToExternalFile(final String path, final String fileName, final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File folder = new File(ROOT+path);
                if (!folder.exists())
                    folder.mkdirs();
                File file = new File(ROOT+path, fileName+".jpg");
                try {
                    file.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void loadBitmapFromExternalFile(final String path, final String fileName, final Listener.OnLoadImageFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder photoFilePathBuilder = new StringBuilder()
                            .append(ROOT)
                            .append(path)
                            .append(fileName)
                            .append(".jpg");
                    Bitmap photo = BitmapFactory.decodeFile(photoFilePathBuilder.toString());
                    listener.onLoadImageSuccess(fileName, photo);

                } catch (Exception ex) {

                }
            }
        }).start();

    }
}
