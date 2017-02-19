package com.internship.pbt.bizarechat.presentation.presenter.editchat;


import com.internship.pbt.bizarechat.presentation.presenter.Presenter;

public interface EditChatPresenter extends Presenter{

    void saveChanges();

    void onSaveChanges();

    void onSaveChangesFailed();

    void editChatTitle(String newTitle);

    void onCheckBoxClickPush(Long id);

    void onCheckBoxClickPull(Long id);
}
