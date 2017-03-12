package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.response.DialogUpdateResponseModel;

import java.io.File;

@StateStrategyType(SkipStrategy.class)
public interface EditChatView extends MvpView {

    void showNoPermissionsToEdit();

    void showOnSaveChangesSuccessfully();

    void loadAvatarToImageView(File file);

    void showTooLargePicture();

    void showChatRoom(DialogUpdateResponseModel dialogModel);

}
