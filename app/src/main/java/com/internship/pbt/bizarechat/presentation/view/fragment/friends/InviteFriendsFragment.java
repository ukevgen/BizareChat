package com.internship.pbt.bizarechat.presentation.view.fragment.friends;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.presenter.friends.InviteFriendsPresenterImpl;

public class InviteFriendsFragment extends MvpAppCompatFragment implements InviteFriendsView, View.OnClickListener {
    private static final String MAIL_BODY = "Install BizareChat";
    private static final String MAIL_SUBJECT = "Invite to chat";
    @InjectPresenter
    InviteFriendsPresenterImpl presenter;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private Button mSend;
    private TextView mText;

    @ProvidePresenter
    InviteFriendsPresenterImpl provaideInviteFriendsPresenter() {
        return new InviteFriendsPresenterImpl(getContext());
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

        mSend = (Button) view.findViewById(R.id.send);
        mText = (TextView) view.findViewById(R.id.sorry);
        mSend.setOnClickListener(this);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.friends_recycler);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(presenter.getAdapter());
        showEmptyList();
        return view;
    }

    private void showEmptyList() {
        if (recyclerView.getAdapter().getItemCount() == 0) {
            mText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getAdapter().setContext(getActivity());
        recyclerView.setAdapter(presenter.getAdapter());
        showEmptyList();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.send) {
            sendEmail();
        }
    }

    @Override
    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, presenter.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, MAIL_SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, MAIL_BODY);
        intent.setData(Uri.parse("mailto:"));

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), R.string.error_send, Toast.LENGTH_SHORT).show();
        }

    }
}
