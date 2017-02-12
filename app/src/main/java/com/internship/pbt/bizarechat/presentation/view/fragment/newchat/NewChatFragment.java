package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl;

public class NewChatFragment extends MvpAppCompatFragment implements NewChatView {
    @InjectPresenter
    NewChatPresenterImpl presenter;

    @ProvidePresenter
    NewChatPresenterImpl provideNewChatPresenter(){
        return new NewChatPresenterImpl();
    }
}
