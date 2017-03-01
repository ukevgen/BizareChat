package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;

import com.arellomobile.mvp.MvpView;

import java.io.File;


public interface EditChatView extends MvpView {

    void showNoPermissionsToEdit();

    void showOnSaveChangesSuccessfully();

    void loadAvatarToImageView(File file);

    void showTooLargePicture();

}
