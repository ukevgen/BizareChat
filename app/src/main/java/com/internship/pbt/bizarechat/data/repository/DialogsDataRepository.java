package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.Map;

import retrofit2.Response;
import rx.Observable;

public class DialogsDataRepository implements DialogsRepository {
    private DialogsService dialogsService;

    public DialogsDataRepository(DialogsService dialogsService) {
        this.dialogsService = dialogsService;
    }

    @Override
    public Observable<AllDialogsResponse> getAllDialogs(Map<String, String> parameters) {
        return dialogsService.getDialogs(UserToken.getInstance().getToken(), parameters);
    }

    @Override
    public Observable<DialogUpdateResponseModel> updateDialog(String dialogId, DialogUpdateRequestModel updateRequestModel) {
        return dialogsService.updateDialog(UserToken.getInstance().getToken(), dialogId, updateRequestModel);
    }

    @Override
    public Observable<Response<Void>> deleteDialogForCurrentUser(String dialogId) {
        return dialogsService.deleteDialog(UserToken.getInstance().getToken(), dialogId);
    }

    @Override
    public Observable<DialogModel> createDialog(NewDialog model) {
        return dialogsService.createDialog(UserToken.getInstance().getToken(), model);
    }

    @Override
    public Observable<Map<String, Integer>> getUnreadMessagesCount(){
        return dialogsService.getUnreadMessagesCount(UserToken.getInstance().getToken());
    }
}
