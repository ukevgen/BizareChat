package com.internship.pbt.bizarechat.domain.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;

import rx.Observable;

public interface DialogsRepository {
    Observable<AllDialogsResponse> getAllDialogs();

    Observable<DialogUpdateResponseModel> updateDialog(String dialogId,
                                                       DialogUpdateRequestModel updateRequestModel);
}
