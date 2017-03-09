package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import retrofit2.Response;
import rx.Observable;

public class MarkMessagesAsReadUseCase extends UseCase<Response<Void>> {
    private DialogsRepository dialogsRepository;
    private String dialogId;

    public MarkMessagesAsReadUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<Response<Void>> buildUseCaseObservable() {
        return dialogsRepository.markMessagesAsRead(dialogId);
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }
}
