package com.internship.pbt.bizarechat.presentation.view.fragment.publicChat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.chats.PublicChatPresenter;

public class PublicChatFragment extends MvpAppCompatFragment implements PublicChatView {
    @InjectPresenter
    PublicChatPresenter presenter;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    @ProvidePresenter
    PublicChatPresenter provideChatPresenter() {
        return new PublicChatPresenter(BizareChatApp.getInstance().getDaoSession());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats_list, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.users_users_container);
        recyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static PublicChatFragment newInstance() {
        PublicChatFragment fragment = new PublicChatFragment();
        return fragment;
    }


}
