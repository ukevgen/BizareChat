package com.internship.pbt.bizarechat.presentation.presenter.settings;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.settings.SettingsView;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    public void setNotificationsState(boolean notificationsOn) {
        CurrentUser.getInstance().setNotificationsState(notificationsOn);
    }
}
