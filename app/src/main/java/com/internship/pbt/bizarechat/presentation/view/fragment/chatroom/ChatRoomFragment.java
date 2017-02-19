package com.internship.pbt.bizarechat.presentation.view.fragment.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.domain.events.MessageSentEvent;
import com.internship.pbt.bizarechat.domain.events.PrivateMessageEvent;
import com.internship.pbt.bizarechat.domain.events.PublicMessageEvent;
import com.internship.pbt.bizarechat.domain.events.ReceiptReceivedEvent;
import com.internship.pbt.bizarechat.presentation.presenter.chatroom.ChatRoomPresenterImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ChatRoomFragment extends MvpAppCompatFragment implements ChatRoomView {
    @InjectPresenter
    ChatRoomPresenterImpl presenter;

    @ProvidePresenter
    ChatRoomPresenterImpl provideChatRoomPresenter(){
        return new ChatRoomPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPrivateMessageEvent(PrivateMessageEvent event){
        //TODO handle privateMessage
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onPublicMessageEvent(PublicMessageEvent event){
        //TODO handle publicMessage
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onReceiptReceivedEvent(ReceiptReceivedEvent event){
        //TODO handle message delivered
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageSentEvent(MessageSentEvent event){
        //TODO handle message sent
    }
}
