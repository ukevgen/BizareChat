package com.internship.pbt.bizarechat.domain.repository;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ukevgen on 03.02.2017.
 */

public interface SignOutRepository {
    Observable<Response<Void>> signOutUser();
}
