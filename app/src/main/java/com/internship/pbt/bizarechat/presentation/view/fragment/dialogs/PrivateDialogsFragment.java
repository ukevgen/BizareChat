package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;


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
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp;

public class PrivateDialogsFragment extends MvpAppCompatFragment implements DialogsView {

    @InjectPresenter
    DialogsPresenterImp presenter;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    @ProvidePresenter
    DialogsPresenterImp provideNewDialogsPresenter() {
        return new DialogsPresenterImp(
                new DeleteDialogUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService())),
                BizareChatApp.getInstance().getDaoSession(),
                DialogsType.TRHEE);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.dialogs_recycler);
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void showDialogs() {
        if (presenter.getAdapter().getItemCount() == 0)
            getActivity().findViewById(R.id.layout_chat_empty).setVisibility(View.VISIBLE);
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(presenter.getAdapter()
                    .setContext(getActivity()));
            presenter.loadDialogs();
        }
    }

    @Override
    public void updateDialogs() {

    }

}
