package com.internship.pbt.bizarechat.presentation.view.fragment.friends;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.friends.InviteFriendsPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.friends.InviteFriendsPresenterImpl;

public class InviteFriendsFragment extends MvpAppCompatFragment implements InviteFriendsView {
    @InjectPresenter
    InviteFriendsPresenterImpl presenter;

    @ProvidePresenter
    InviteFriendsPresenterImpl provaideInviteFriendsPresenter() {
        return new InviteFriendsPresenterImpl();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invite_friends, container, false);

        return view;
    }


    @Override
    public void showContacts() {

    }
}
