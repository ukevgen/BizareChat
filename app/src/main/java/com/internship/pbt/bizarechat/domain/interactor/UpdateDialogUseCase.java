package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class UpdateDialogUseCase extends UseCase<DialogUpdateResponseModel>{

    private DialogsRepository dialogsRepository;
    private String dialogId;
    private DialogUpdateRequestModel requestModel;


    public UpdateDialogUseCase(DialogsRepository dialogsRepository, String dialogId, DialogUpdateRequestModel requestModel) {
        this.dialogsRepository = dialogsRepository;
        this.dialogId = dialogId;
        this.requestModel = requestModel;
    }

    @Override
    protected Observable<DialogUpdateResponseModel> buildUseCaseObservable() {
        return dialogsRepository.updateDialog(dialogId, requestModel);
    }
}
