package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.net.services.DialogsService;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class DialogsDataRepository implements DialogsRepository{
    private DialogsService dialogsService;

    public DialogsDataRepository(DialogsService dialogsService){
        this.dialogsService = dialogsService;
    }

    @Override
    public Observable<AllDialogsResponse> getAllDialogs() {
        return dialogsService.getDialogs(UserToken.getInstance().getToken(), null);
    }
}
