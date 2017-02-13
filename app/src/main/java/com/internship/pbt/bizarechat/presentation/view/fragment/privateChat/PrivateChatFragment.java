package com.internship.pbt.bizarechat.presentation.view.fragment.privateChat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.chats.PrivateChatPresenter;

public class PrivateChatFragment extends MvpAppCompatFragment implements PrivateChatView {
    @InjectPresenter
    PrivateChatPresenter presenter;

    @ProvidePresenter
    PrivateChatPresenter provideChatPresenter(){
        return new PrivateChatPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats_list, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static PrivateChatFragment newInstance() {
        return new PrivateChatFragment();
    }
}
