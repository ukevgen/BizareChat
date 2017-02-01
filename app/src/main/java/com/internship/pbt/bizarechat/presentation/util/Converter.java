package com.internship.pbt.bizarechat.presentation.util;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class Converter {

    private Converter(){}

    public static File convertUriToFile(Context context, Uri uri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();

        return new File(s);
    }

    public static File compressPhoto(Context context, File src) throws IOException{
         return Compressor.getDefault(context).compressToFile(src);
    }


    /* String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
        File createDir = new File (root + "BizareChat" + File.separator);
        if(!createDir.exists())
            createDir.mkdir();
        File file = new File(root + "BizareChat" + File.separator + new File(s).getName());

        Log.d("file1", "Absolute Path " +file.getAbsolutePath() + " " + " Name " + new File(s).getName());
        try {
            file.createNewFile();
            makeFileCopy(new File(s), file);
        }catch (IOException ignored){
            Log.d("file1", "IoException " + ignored.toString());
        }*/
}
