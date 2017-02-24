package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.response.CreateDialogResponse;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class CreateDialogUseCase extends UseCase<CreateDialogResponse> {
    DialogsRepository dialogsRepository;
    String type,
            name,
            occupants_ids;

    public CreateDialogUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<CreateDialogResponse> buildUseCaseObservable() {
        return dialogsRepository.createDialog(type, name, occupants_ids);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOccupants_ids(String occupants_ids) {
        this.occupants_ids = occupants_ids;
    }
}
