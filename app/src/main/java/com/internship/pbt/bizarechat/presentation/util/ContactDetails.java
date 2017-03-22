package com.internship.pbt.bizarechat.presentation.util;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import com.internship.pbt.bizarechat.logs.Logger;
import com.internship.pbt.bizarechat.presentation.model.ContactsFriends;

import java.util.ArrayList;
import java.util.HashSet;

public class ContactDetails {
    private Context context;

    public ContactDetails(Context context) {
        this.context = context;
    }

    public ArrayList<ContactsFriends> getContactsEmailDetails() {

        ArrayList<ContactsFriends> contactsFriendses = new ArrayList<>();
        HashSet<String> emlRecsHS = new HashSet<>();

        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[]{ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID};
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst()) {
            do {
                String name = cur.getString(1);
                if (emlRecsHS.add(name.toLowerCase())) {
                    ContactsFriends friend = new ContactsFriends();
                    friend.setName(cur.getString(1));
                    friend.setEmail(cur.getString(3));
                    friend.setPhotoId(cur.getString(2));
                    try {
                        friend.setUserPic(queryContactImage(Integer.parseInt(friend.getPhotoId())));
                    } catch (Exception e) {
                        Logger.logExceptionToFabric(e);
                    } finally {
                        contactsFriendses.add(friend);
                    }
                }

            } while (cur.moveToNext());
        }

        return contactsFriendses;
    }

    private Bitmap queryContactImage(int imageDataRow) {
        Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{
                ContactsContract.CommonDataKinds.Photo.PHOTO
        }, ContactsContract.Data._ID + "=?", new String[]{
                Integer.toString(imageDataRow)
        }, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }
    }

}
