package com.internship.pbt.bizarechat.domain.repository;


import android.graphics.Bitmap;

import java.io.File;

import rx.Observable;

public interface ContentRepository {

    Observable<Integer> uploadFile(String contentType, File file, String name);

    Observable<Bitmap> getPhoto(Integer blobId);
}
