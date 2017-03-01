package com.internship.pbt.bizarechat.domain.interactor;


import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.NewDialog;
import com.internship.pbt.bizarechat.domain.repository.DialogsRepository;

import rx.Observable;

public class CreateDialogUseCase extends UseCase<DialogModel> {
    private DialogsRepository dialogsRepository;
    private NewDialog dialog;

    public CreateDialogUseCase(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
    }

    @Override
    protected Observable<DialogModel> buildUseCaseObservable() {
        return dialogsRepository.createDialog(dialog);
    }

    public void setDialog(NewDialog dialog) {
        this.dialog = dialog;
    }
}
