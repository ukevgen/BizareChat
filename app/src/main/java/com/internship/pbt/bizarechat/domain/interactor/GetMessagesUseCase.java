package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.List;

import rx.Observable;

public class GetMessagesUseCase  extends UseCase<List<MessageModel>> {
    private DialogsRepository repository;
    private String chatDialogId;
    private int dialogType;

    public GetMessagesUseCase(DialogsRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<List<MessageModel>> buildUseCaseObservable() {
        return repository.getMessages(chatDialogId, dialogType);
    }

    public void setChatDialogId(String chatDialogId) {
        this.chatDialogId = chatDialogId;
    }

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }
}
