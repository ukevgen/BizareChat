package com.internship.pbt.bizarechat.domain.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;

import rx.Observable;

public interface DialogsRepository {
    Observable<AllDialogsResponse> getAllDialogs();
}
