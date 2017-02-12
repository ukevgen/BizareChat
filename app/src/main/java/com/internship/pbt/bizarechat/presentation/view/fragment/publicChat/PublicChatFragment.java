package com.internship.pbt.bizarechat.presentation.view.fragment.publicChat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.chats.PublicChatPresenter;

public class PublicChatFragment extends MvpAppCompatFragment implements PublicChatView {
    @InjectPresenter
    PublicChatPresenter presenter;

    @ProvidePresenter
    PublicChatPresenter provideChatPresenter(){
        return new PublicChatPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats_list, container, false);
    }

    public static PublicChatFragment newInstance() {
        PublicChatFragment fragment = new PublicChatFragment();
        return fragment;
    }


}
