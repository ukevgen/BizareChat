package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;
import com.internship.pbt.bizarechat.data.net.requests.dialog.DialogUpdateRequestModel;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class UpdateDialogUseCase extends UseCase<DialogUpdateResponseModel>{

    private DialogsRepository dialogsRepository;
    private String dialogId;
    private DialogUpdateRequestModel requestModel;


    public UpdateDialogUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<DialogUpdateResponseModel> buildUseCaseObservable() {
        return dialogsRepository.updateDialog(dialogId, requestModel);
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public void setRequestModel(DialogUpdateRequestModel requestModel) {
        this.requestModel = requestModel;
    }
}
