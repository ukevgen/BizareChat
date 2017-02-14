package com.internship.pbt.bizarechat.presentation.view.fragment.users;


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
import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserDataRepository;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.presenter.users.UsersPresenter;

public class UsersFragment extends MvpAppCompatFragment implements UsersView{
    @InjectPresenter
    UsersPresenter presenter;

    @ProvidePresenter
    UsersPresenter provideUsersPresenter(){
        return new UsersPresenter(
                new GetAllUsersUseCase(
                        new UserDataRepository(BizareChatApp.getInstance().getUserService())),
                new GetPhotoUseCase(new ContentDataRepository(
                        BizareChatApp.getInstance().getContentService(),
                        CacheSharedPreferences.getInstance(BizareChatApp.getInstance()),
                        CacheUsersPhotos.getInstance(BizareChatApp.getInstance()))));
    }

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private boolean loading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.users_users_container);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            loading = false;
                            presenter.getAllUsers();
                        }
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView.setAdapter(presenter.getAdapter().setContext(getActivity()));
        presenter.getAllUsers();
    }

    @Override
    public void showAloneMessage() {

    }
}
