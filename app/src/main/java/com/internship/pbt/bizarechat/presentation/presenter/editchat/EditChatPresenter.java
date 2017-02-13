package com.internship.pbt.bizarechat.presentation.presenter.editchat;


import android.net.Uri;

public interface EditChatPresenter {

    void saveChanges();

    void onSaveChanges();

    void onSaveChangesFailed();

    void editChatTitle(String newTitle);

    void removeUsersFromPrivateGroupChat();

    void editChatImage(Uri newImage);
}
