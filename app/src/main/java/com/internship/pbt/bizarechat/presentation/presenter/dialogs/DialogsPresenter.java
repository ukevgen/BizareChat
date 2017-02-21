package com.internship.pbt.bizarechat.presentation.presenter.dialogs;


public interface DialogsPresenter {

    void checkConnectionProblem();


    void openDialog();

    void loadDialogs();

    void deleteUserFromCurrentDialogOnServer();
}
