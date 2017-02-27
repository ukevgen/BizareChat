package com.internship.pbt.bizarechat.domain.repository;


import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateDialogResponse;

import retrofit2.Response;
import rx.Observable;

public interface DialogsRepository {
    Observable<AllDialogsResponse> getAllDialogs();

    Observable<DialogUpdateResponseModel> updateDialog(String dialogId,
                                                       DialogUpdateRequestModel updateRequestModel);

    Observable<Response<Void>> deleteDialogForCurrentUser(String dialogId);

    Observable<DialogModel> createDialog(NewDialog dialog);
}
