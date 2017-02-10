package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenter;

public class NewChatFragment extends MvpAppCompatFragment implements NewChatView {
    @InjectPresenter
    private NewChatPresenter presenter;
}
