package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;


public interface DialogsView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showChatRoom(DialogModel dialogModel);

    void stopRefreshing();
}
