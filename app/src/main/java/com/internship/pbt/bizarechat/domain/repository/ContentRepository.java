package com.internship.pbt.bizarechat.domain.repository;


import android.graphics.Bitmap;

import com.internship.pbt.bizarechat.data.datamodel.UserModel;

import java.io.File;
import java.util.List;
import java.util.Map;

import rx.Observable;

public interface ContentRepository {

    Observable<Integer> uploadFile(String contentType, File file, String name);

    Observable<Map<Long, Bitmap>> getUsersPhotos(List<UserModel> users);

    Observable<Bitmap> getPhoto(Integer blobId);
}
