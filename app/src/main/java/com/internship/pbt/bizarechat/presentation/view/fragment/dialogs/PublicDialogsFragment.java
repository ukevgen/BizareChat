package com.internship.pbt.bizarechat.presentation.view.fragment.dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.adapter.DialogsRecyclerViewAdapter;
import com.internship.pbt.bizarechat.constans.DialogsType;
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.DialogsDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.DeleteDialogUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetUserByIdUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.dialogs.DialogsPresenterImp;

public class PublicDialogsFragment extends MvpAppCompatFragment
        implements DialogsView, DialogsRecyclerViewAdapter.OnDialogDeleteCallback {
    private final int menuSearchId = 100;

    @InjectPresenter
    DialogsPresenterImp presenter;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    @ProvidePresenter
    DialogsPresenterImp provideNewDialogsPresenter() {
        return new DialogsPresenterImp(
                new DeleteDialogUseCase(new DialogsDataRepository(BizareChatApp.getInstance()
                        .getDialogsService())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheSharedPreferences.getInstance(BizareChatApp.getInstance()),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))),
                new GetUserByIdUseCase(new UserDataRepository(
                        BizareChatApp.getInstance().getUserService())),
                BizareChatApp.getInstance().getDaoSession(),
                CacheSharedPreferences.getInstance(getContext()),

                DialogsType.ONE);
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
        setHasOptionsMenu(true);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, menuSearchId, 0, "Search").setIcon(R.drawable.search_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == menuSearchId) {
            //TODO search logic
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDialogs() {
        if (presenter.getAdapter().getItemCount() == 0)
            getActivity().findViewById(R.id.layout_chat_empty).setVisibility(View.VISIBLE);
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void updateDialogs() {

    }


    @Override
    public void onDialogDelete(int position) {
        //presenter.deleteDialog(position);
    }
}
