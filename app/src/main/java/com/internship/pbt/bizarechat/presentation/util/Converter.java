package com.internship.pbt.bizarechat.presentation.util;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import id.zelory.compressor.Compressor;

public class Converter {

    private Context context;

    public Converter(Context context){
        this.context = context;
    }

    public File convertUriToFile(Uri uri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();

        return new File(s);
    }

    public File compressPhoto(File src) {
         return Compressor.getDefault(context).compressToFile(src);
    }

}
