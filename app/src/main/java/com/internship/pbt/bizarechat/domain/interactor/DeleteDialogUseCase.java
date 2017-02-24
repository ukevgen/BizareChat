package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class DeleteDialogUseCase extends UseCase<retrofit2.Response<Void>> {

    private DialogsRepository dialogsRepository;
    private String dialogId;


    public DeleteDialogUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    protected Observable<retrofit2.Response<Void>> buildUseCaseObservable() {
        return dialogsRepository.deleteDialogForCurrentUser(dialogId);
    }
}
