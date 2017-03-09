package com.internship.pbt.bizarechat.presentation.presenter.userinfo;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.data.datamodel.DialogModel;
import com.internship.pbt.bizarechat.domain.interactor.GetPrivateDialogByUserId;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.userinfo.UserInfoView;

import rx.Subscriber;


@InjectViewState
public class UserInfoPresenter extends MvpPresenter<UserInfoView> {
    private final static String TAG = UserInfoPresenter.class.getSimpleName();
    private long userId;
    private GetPrivateDialogByUserId getDialog;

    public UserInfoPresenter(GetPrivateDialogByUserId getDialog) {
        this.getDialog = getDialog;
    }

    public boolean isCurrentUser(){
        return userId == CurrentUser.getInstance().getCurrentUserId();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void sendEmail(String email){
        if(!isCurrentUser() && email != null && !email.isEmpty()){
            getViewState().startSendEmail(email);
        }
    }

    public void dialPhoneNumber(String phone){
        if(!isCurrentUser() && phone != null && !phone.isEmpty()){
            getViewState().startDialPhoneNumber(phone);
        }
    }

    public void openWebsite(String website){
        if(!isCurrentUser() && website != null && !website.isEmpty()){
            getViewState().startOpenWebsite(website);
        }
    }

    public void startUserChat(){
        if(isCurrentUser()) return;

        getViewState().showLoading();
        getDialog.setUserId(userId);
        getDialog.execute(new Subscriber<DialogModel>() {
            @Override public void onCompleted() {

            }

            @Override public void onError(Throwable e) {
                getViewState().hideLoading();
                Log.d(TAG, e.getCause().getMessage(), e.getCause());
            }

            @Override public void onNext(DialogModel dialog) {
                getViewState().hideLoading();
                getViewState().showChatRoom(dialog);
            }
        });
    }
}
