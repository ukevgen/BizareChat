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

public class NewChatFragment extends MvpAppCompatFragment implements NewChatView, View.OnClickListener {
    @InjectPresenter
    NewChatPresenterImpl presenter;

    @ProvidePresenter
    NewChatPresenterImpl provideNewChatPresenter() {
        return new NewChatPresenterImpl();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
