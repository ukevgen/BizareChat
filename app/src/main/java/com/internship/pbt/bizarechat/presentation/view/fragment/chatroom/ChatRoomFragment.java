package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenter;


public class ChatRoomFragment extends MvpAppCompatFragment implements ChatRoomView {
    @InjectPresenter
    private ChatRoomPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}
