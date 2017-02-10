package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenter;


public class EditChatFragment extends MvpAppCompatFragment implements EditChatView {
    @InjectPresenter
    private EditChatPresenter presenter;
}
