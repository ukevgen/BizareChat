package com.internship.pbt.bizarechat.presentation.presenter.main;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.domain.interactor.SignOutUseCase;
import com.internship.pbt.bizarechat.presentation.view.activity.MainView;

import retrofit2.Response;
import rx.Subscriber;

@InjectViewState
public class MainPresenterImpl extends MvpPresenter<MainView> implements MainPresenter {

    private SignOutUseCase signOutUseCase;

    public MainPresenterImpl(SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    private void clearCurrentUserCache() {
        /*UserToken.getInstance().deleteToken();
        CurrentUser.getInstance().setAuthorized(false);
        CurrentUser.getInstance().clearCurrentUser();*/
    }

    @Override
    public void logout() {
        signOutUseCase.execute(new Subscriber<Response<Void>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", e.getLocalizedMessage());
            }

            @Override
            public void onNext(Response<Void> voidResponse) {
                Log.d("TAG", "ok");
            }
        });

    }

    public void navigateToUsers() {
        getViewState().startUsersView();
        getViewState().hideNavigationElements();
    }

    @Override
    public void navigateToNewChat() {
        getViewState().startNewChatView();
        getViewState().hideNavigationElements();
    }

    @Override
    public void addNewChat() {
        if (true) {
            navigateToNewChat();
        } else {
            getViewState().showLackOfFriends();
        }
    }

    @Override
    public void onLogoutSuccess() {
        getViewState().navigateToLoginScreen();
        clearCurrentUserCache();
    }

    public void confirmLogOut() {
        getViewState().confirmLogOut();
    }

    public void onBackPressed() {
        getViewState().startBackPressed();
    }

    @Override
    public void inviteFriends() {
        getViewState().showInviteFriendsScreen();
        getViewState().hideNavigationElements();
    }
}
