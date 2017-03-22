package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import java.util.Map;

import rx.Observable;

public class GetAllDialogsUseCase extends UseCase<AllDialogsResponse>{
    private DialogsRepository dialogsRepository;
    private Map<String, String> parameters;

    public GetAllDialogsUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<AllDialogsResponse> buildUseCaseObservable() {
        return dialogsRepository.getAllDialogs(parameters);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
