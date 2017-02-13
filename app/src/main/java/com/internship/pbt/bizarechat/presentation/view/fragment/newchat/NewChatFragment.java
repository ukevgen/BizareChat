package com.internship.pbt.bizarechat.presentation.view.fragment.newchat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.newchat.NewChatPresenterImpl;

public class NewChatFragment extends MvpAppCompatFragment implements NewChatView {
    @InjectPresenter
    NewChatPresenterImpl presenter;

    @ProvidePresenter
    NewChatPresenterImpl provideNewChatPresenter(){
        return new NewChatPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_chat, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
