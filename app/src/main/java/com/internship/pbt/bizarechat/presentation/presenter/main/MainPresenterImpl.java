package com.internship.pbt.bizarechat.presentation.presenter.main;


import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.data.datamodel.DialogModelDao;
import com.internship.pbt.bizarechat.data.datamodel.response.AllDialogsResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateSubscriptionResponse;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.repository.PushNotificationsRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.events.DialogsUpdatedEvent;
import com.internship.pbt.bizarechat.domain.interactor.CreateSubscriptionUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetAllDialogsUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.domain.interactor.SignOutUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.util.Converter;
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
    private GetPhotoUseCase photoUseCase;
    private long dialogsCount;
    private boolean launched = false;
    private boolean privateDialogsOnScreen = false;

    public MainPresenterImpl(SignOutUseCase signOutUseCase,
                             GetAllDialogsUseCase dialogsUseCase,
                             DaoSession daoSession,
                             GetPhotoUseCase photoUseCase) {
        this.signOutUseCase = signOutUseCase;
        this.dialogsUseCase = dialogsUseCase;
        this.daoSession = daoSession;
        this.photoUseCase = photoUseCase;
    }

    private void clearCurrentUserCache() {
        UserToken.getInstance().deleteToken();
        CurrentUser.getInstance().setAuthorized(false);
        CurrentUser.getInstance().clearCurrentUser();
    }

    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public boolean isPrivateDialogsOnScreen() {
        return privateDialogsOnScreen;
    }

    public void setPrivateDialogsOnScreen(boolean privateDialogsOnScreen) {
        this.privateDialogsOnScreen = privateDialogsOnScreen;
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

    public void showCurrentUserInfo(){
        hideNavigationElements();
        getViewState().closeDrawer();
        getViewState().showUserInfo();
    }

    public void loadUserAvatar(){
        photoUseCase.setBlobId(CurrentUser.getInstance().getAvatarBlobId().intValue());
        photoUseCase.execute(new Subscriber<Bitmap>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                Log.e(TAG, e.getMessage(), e);
            }

            @Override public void onNext(Bitmap image) {
                getViewState().setAvatarImage(image);
                CurrentUser.getInstance().setStringAvatar(Converter.imageToString(image));
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
        hideNavigationElements();
        getViewState().startUsersView();
    }

    @Override
    public void navigateToNewChat() {
        hideNavigationElements();
        getViewState().startNewChatView();
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
        hideNavigationElements();
        getViewState().showInviteFriendsScreen();
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
                    if (isDialogDaoEmpty())
                        getViewState().showEmptyScreen();
                    EventBus.getDefault().post(new DialogsUpdatedEvent());
                }
            });
        }
    }

    public void navigateToPrivateChatRoom(DialogModel dialogModel) {
        hideNavigationElements();
        getViewState().showPrivateChatRoom(dialogModel);
    }

    public void navigateToPublicChatRoom(DialogModel dialogModel) {
        hideNavigationElements();
        getViewState().showPublicChatRoom(dialogModel);
    }

    public void navigateToSettingsScreen(){
        hideNavigationElements();
        getViewState().showSettingsScreen();
    }

    public void hideNavigationElements(){
        getViewState().hideNavigationElements();
    }

    public void showNavigationElements(){
        getViewState().showNavigationElements();
    }
}
