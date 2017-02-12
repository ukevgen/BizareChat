package com.internship.pbt.bizarechat.presentation.view.fragment.editchat;


import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.presentation.presenter.editchat.EditChatPresenterImpl;


public class EditChatFragment extends MvpAppCompatFragment implements EditChatView {
    @InjectPresenter
    EditChatPresenterImpl presenter;

    @ProvidePresenter
    EditChatPresenterImpl provideEditChatPresenter(){
        return new EditChatPresenterImpl();
    }
}
