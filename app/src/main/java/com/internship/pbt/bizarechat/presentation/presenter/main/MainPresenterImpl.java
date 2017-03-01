package com.internship.pbt.bizarechat.presentation.presenter.main;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.repository.PushNotificationsRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.events.DialogsUpdatedEvent;
import com.internship.pbt.bizarechat.domain.interactor.CreateSubscriptionUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllDialogsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.SignOutUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.MainView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import retrofit2.Response;
import rx.Subscriber;

@InjectViewState
public class MainPresenterImpl extends MvpPresenter<MainView> implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private SignOutUseCase signOutUseCase;
    private GetAllDialogsUseCase dialogsUseCase;
    private DaoSession daoSession;
    private long dialogsCount;

    public MainPresenterImpl(SignOutUseCase signOutUseCase,
                             GetAllDialogsUseCase dialogsUseCase,
                             DaoSession daoSession) {
        this.signOutUseCase = signOutUseCase;
        this.dialogsUseCase = dialogsUseCase;
        this.daoSession = daoSession;
    }

    private void clearCurrentUserCache() {
        UserToken.getInstance().deleteToken();
        CurrentUser.getInstance().setAuthorized(false);
        CurrentUser.getInstance().clearCurrentUser();
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
                getViewState().navigateToLoginScreen();
                clearCurrentUserCache();
            }
        });

    }

    public void sendSubscriptionToServer() {
        CreateSubscriptionUseCase useCase = new CreateSubscriptionUseCase(
                new PushNotificationsRepository(RetrofitApi.getRetrofitApi().getNotificationService()));
        useCase.setFirebaseToken(CurrentUser.getInstance().getFirebaseToken());

        useCase.execute(new Subscriber<CreateSubscriptionResponse[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage(), e);
            }

            @Override
            public void onNext(CreateSubscriptionResponse[] response) {
                CurrentUser.getInstance().setSubscribed(true);
                Log.d(TAG, "sendRegistrationToServer: " + CurrentUser.getInstance().getFirebaseToken());
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

    private boolean isDialogDaoEmpty() {
        dialogsCount = daoSession.getDialogModelDao().count();
        return dialogsCount == 0;
    }

    public void updateDialogsDao() {
        if (BizareChatApp.getInstance().getDaoSession() == null)
            daoSession = BizareChatApp.getInstance().getDaoSession();

        if (BizareChatApp.getInstance().isNetworkConnected()) {
            dialogsUseCase.setParameters(new HashMap<>());
            dialogsUseCase.execute(new Subscriber<AllDialogsResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.toString(), e);
                }

                @Override
                public void onNext(AllDialogsResponse response) {
                    DialogModelDao modelDao = daoSession.getDialogModelDao();
                    modelDao.insertOrReplaceInTx(response.getDialogModels());
                    if (!isDialogDaoEmpty())
                        getViewState().hideEmptyScreen();
                    EventBus.getDefault().post(new DialogsUpdatedEvent());
                }
            });
        }
    }


}

