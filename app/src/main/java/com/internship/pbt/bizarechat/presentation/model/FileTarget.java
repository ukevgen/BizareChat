/*package com.internship.pbt.bizarechat.presentation.model;

import android.graphics.Bitmap;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileTarget extends SimpleTarget<Bitmap> {

    private File fileName;
    private Bitmap.CompressFormat format;
    private int quality;

    public FileTarget (File fileName, int width, int height) {
        this(fileName, width, height, Bitmap.CompressFormat.JPEG, 70);
    }
    public FileTarget(File fileName, int width, int height, Bitmap.CompressFormat format, int quality) {
        super(width, height);
        this.fileName = fileName;
        this.format = format;
        this.quality = quality;
    }

    @Override
    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            bitmap.compress(format, quality, fos);
            fos.flush();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/

// TODO UNDERSTAND WHY IT`S NOW WORKING...
