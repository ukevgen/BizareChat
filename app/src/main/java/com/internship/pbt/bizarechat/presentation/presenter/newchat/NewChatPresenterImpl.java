package com.internship.pbt.bizarechat.presentation.presenter.newchat;


import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.NewChatUsersRecyclerAdapter;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.newchat.NewChatView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscriber;

@InjectViewState
public class NewChatPresenterImpl extends MvpPresenter<NewChatView> implements NewChatPresenter {
    private Long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private Integer currentUsersPage = 0;
    private Integer usersCount = 0;
    private GetAllUsersUseCase allUsersUseCase;
    private GetPhotoUseCase photoUseCase;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    //use this field to get all checked users and add them to "create chat request"
    private Set<Long> checkedUsers;
    private NewChatUsersRecyclerAdapter adapter;
    private boolean isPublicButtonChecked = true;

    public NewChatPresenterImpl(GetAllUsersUseCase allUsersUseCase, GetPhotoUseCase photoUseCase){
        this.allUsersUseCase = allUsersUseCase;
        this.photoUseCase = photoUseCase;
        users = new ArrayList<>();
        usersPhotos = new HashMap<>();
        checkedUsers = new HashSet<>();
        adapter = new NewChatUsersRecyclerAdapter(users, usersPhotos, checkedUsers);
    }

    public void getAllUsers(){
        if(usersCount != 0 && currentUsersPage * ApiConstants.USERS_PER_PAGE >= usersCount) return;

        allUsersUseCase.setPage(++currentUsersPage);
        allUsersUseCase.execute(new Subscriber<AllUsersResponse>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(AllUsersResponse response) {
                UserModel user;
                int insertCounter = 0;
                for(AllUsersResponse.Item item : response.getItems()){
                    user = item.getUser();

                    if(user.getUserId().equals(currentUserId))
                        continue;

                    users.add(user);
                    insertCounter++;

                    if(user.getBlobId() != null){
                        getAndAddPhoto(users.size()-1, user.getUserId(), user.getBlobId());
                    } else{
                        usersPhotos.put(user.getUserId(), null);
                    }
                }
                if(usersCount == 0) {
                    usersCount = response.getTotalEntries();
                }
                else{
                    adapter.notifyItemRangeInserted(users.size()-insertCounter, insertCounter);
                }
            }
        });
    }

    private void getAndAddPhoto(int position, Long userId, Integer blobId){
        photoUseCase.setBlobId(blobId);
        photoUseCase.execute(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                usersPhotos.put(userId, bitmap);
                adapter.notifyItemChanged(position);
            }
        });
    }

    public void onPrivateClick(){
        isPublicButtonChecked = false;
        setChatPhotoVisibility();
        getViewState().showUsersView();
    }

    public void onPublicClick(){
        isPublicButtonChecked = true;
        setChatPhotoVisibility();
        getViewState().hideUsersView();
    }

    public void setChatPhotoVisibility(){
        if(!isPublicButtonChecked && checkedUsers.size() > 1)
            getViewState().showChatPhoto();
        else if(isPublicButtonChecked)
            getViewState().showChatPhoto();
        else{
            getViewState().hideChatPhoto();
        }
    }

    public NewChatUsersRecyclerAdapter getAdapter() {
        return adapter;
    }
}
