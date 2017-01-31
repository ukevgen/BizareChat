package com.internship.pbt.bizarechat.domain.repository;


import java.io.File;

import retrofit2.Response;
import rx.Observable;

public interface ContentRepository {

    Observable<Response<Void>> uploadFile(String contentType, File file, String name);
}
