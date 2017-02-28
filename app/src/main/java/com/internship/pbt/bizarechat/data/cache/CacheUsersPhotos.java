package com.internship.pbt.bizarechat.data.cache;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

public final class CacheUsersPhotos {
    private static volatile CacheUsersPhotos INSTANCE;
    private String pathToPhotos;

    private CacheUsersPhotos(Context context) {
        pathToPhotos = context.getCacheDir().getAbsolutePath()
                + File.separator + "bizarechat" + File.separator;
        new File(pathToPhotos).mkdirs();
    }

    public static CacheUsersPhotos getInstance(Context context) {
        CacheUsersPhotos local = INSTANCE;
        if (local == null) {
            synchronized (CacheUsersPhotos.class) {
                local = INSTANCE;
                if (local == null) {
                    INSTANCE = local = new CacheUsersPhotos(context);
                }
            }
        }
        return local;
    }

    public Bitmap getPhoto(Integer blobId){
        if(new File(pathToPhotos + blobId + ".jpg").exists())
            return BitmapFactory.decodeFile(pathToPhotos + blobId + ".jpg");
        return null;
    }

    public Bitmap savePhoto(ResponseBody responseBody, Integer blobId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inSampleSize = 8;
        Bitmap result = BitmapFactory.decodeStream(responseBody.byteStream(), null, opt);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        result.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        saveToFile(new ByteArrayInputStream(outputStream.toByteArray()), blobId);
        return result;
    }

    private void saveToFile(InputStream inputStream, Integer blobId){
        File fileToWrite = new File(pathToPhotos + blobId + ".jpg");
        byte[] buffer = new byte[4096];
        OutputStream outputStream = null;
        int byteRead;
        try {

            try {
                outputStream = new FileOutputStream(fileToWrite);
                while((byteRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, byteRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(outputStream != null)
                outputStream.close();
            if(inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
