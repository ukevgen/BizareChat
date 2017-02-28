package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class GetAllDialogsUseCase extends UseCase<AllDialogsResponse>{
    private DialogsRepository dialogsRepository;

    public GetAllDialogsUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<AllDialogsResponse> buildUseCaseObservable() {
        return dialogsRepository.getAllDialogs();
    }
}
