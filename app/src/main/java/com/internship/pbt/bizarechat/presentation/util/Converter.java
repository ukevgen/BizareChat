package com.internship.pbt.bizarechat.presentation.util;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import id.zelory.compressor.Compressor;

public class Converter {
    private static final String YESTERDAY = "Yesterday";
    private static final String TODAY = "Today";
    private static SimpleDateFormat messageTimeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat messageDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Context context;

    public Converter(Context context) {
        this.context = context;
    }

    public static String imageToString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String longToTime(long timestamp) {
        return messageTimeFormat.format(new Date(timestamp));
    }

    public static String getLastMessageDay(long lastMessageDateSent) {
        Date dt = new Date(lastMessageDateSent * 1000);
        return messageDateFormat.format(dt);
    }

    public static String getPartOfTheWeek(long currentTime) {
        Date dt = new Date(currentTime * 1000);
        String now = messageDateFormat.format(new Date());
        String messageDate = messageDateFormat.format(dt);
        if (now.equals(messageDate)) {
            return TODAY;
        } else if (yesterday().equals(messageDate)) {
            return YESTERDAY;
        } else {
            return messageDate;
        }


    }

    private static String yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return messageDateFormat.format(cal.getTime());
    }

    public File convertUriToFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();

        return new File(s);
    }

    public File compressPhoto(File src) {
        return Compressor.getDefault(context).compressToFile(src);
    }

    public String encodeAvatarTobase64(File file) {
        Bitmap bitmap = null;
        String imageEncoded = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(file));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageEncoded;
    }

    public Bitmap decodeBase64(String stringAvatar) {
        byte[] decodedByte = new byte[0];
        if (stringAvatar != null) {
            decodedByte = Base64.decode(stringAvatar, 0);
        }
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

    }

    public String getOccupantsArray(Set<Long> users) {
        StringBuilder builder = new StringBuilder();
        for (long value : users) {
            builder.append(value).append(",");
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }

        return builder.toString();
    }
}
