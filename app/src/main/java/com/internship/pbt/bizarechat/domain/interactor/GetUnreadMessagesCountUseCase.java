package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.Map;

import rx.Observable;

public class GetUnreadMessagesCountUseCase extends UseCase<Map<String, Integer>>{
    private DialogsRepository dialogsRepository;

    public GetUnreadMessagesCountUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<Map<String, Integer>> buildUseCaseObservable() {
        return dialogsRepository.getUnreadMessagesCount();
    }
}
