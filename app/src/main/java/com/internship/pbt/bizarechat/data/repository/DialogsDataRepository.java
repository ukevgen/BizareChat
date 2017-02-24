package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateDialogResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.HashMap;

import retrofit2.Response;
import rx.Observable;

public class DialogsDataRepository implements DialogsRepository {
    private DialogsService dialogsService;

    public DialogsDataRepository(DialogsService dialogsService) {
        this.dialogsService = dialogsService;
    }

    @Override
    public Observable<AllDialogsResponse> getAllDialogs() {
        return dialogsService.getDialogs(UserToken.getInstance().getToken(), new HashMap<>());
    }

    @Override
    public Observable<DialogUpdateResponseModel> updateDialog(String dialogId, DialogUpdateRequestModel updateRequestModel) {
        return dialogsService.updateDialog(UserToken.getInstance().getToken(), dialogId, updateRequestModel);
    }

    @Override
    public Observable<Response<Void>> deleteDialogForCurrentUser(String dialogId) {
        return dialogsService.geleteDialog(UserToken.getInstance().getToken(), dialogId);
    }

    @Override
    public Observable<CreateDialogResponse> createDialog(String type, String name, String occupants_ids) {
        return dialogsService.createDialog(UserToken.getInstance().getToken(),
                type, name, occupants_ids);
    }
}
