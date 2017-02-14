package com.internship.pbt.bizarechat.domain.repository;


import android.graphics.Bitmap;

import java.io.File;

import retrofit2.Response;
import rx.Observable;

public interface ContentRepository {

    Observable<Response<Void>> uploadFile(String contentType, File file, String name);

    Observable<Bitmap> getPhoto(Integer blobId);
}
