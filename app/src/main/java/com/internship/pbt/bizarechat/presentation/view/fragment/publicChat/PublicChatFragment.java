package com.internship.pbt.bizarechat.presentation.view.fragment.publicChat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.chats.ChatPresenter;

public class PublicChatFragment extends MvpAppCompatFragment implements PublicChatView {
    @InjectPresenter
    private ChatPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public_chats, container, false);
    }


}
