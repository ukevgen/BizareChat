package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class GetPrivateDialogByUserId extends UseCase<DialogModel> {
    private DialogsRepository repository;
    private long userId;

    public GetPrivateDialogByUserId(DialogsRepository repository) {
        this.repository = repository;
    }

    @Override protected Observable<DialogModel> buildUseCaseObservable() {
        return repository.getPrivateDialogByUserId(userId);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
