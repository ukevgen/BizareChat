package com.internship.pbt.bizarechat.presentation.presenter.users;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.internship.pbt.bizarechat.adapter.UsersRecyclerAdapter;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.domain.interactor.GetAllUsersUseCase;
import com.internship.pbt.bizarechat.domain.interactor.GetPhotoUseCase;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.fragment.users.UsersView;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;


@InjectViewState
public class UsersPresenter extends MvpPresenter<UsersView>
        implements UsersRecyclerAdapter.OnUserClickListener{
    private boolean filtering = false;
    private String currentSortOrder;
    private String currentFilterQuery;
    private Long currentUserId = CurrentUser.getInstance().getCurrentUserId();
    private Integer currentUsersPage = 0;
    private Integer usersCount = 0;
    private List<UserModel> users;
    private Map<Long, Bitmap> usersPhotos;
    private GetAllUsersUseCase allUsersUseCase;
    private GetPhotoUseCase photoUseCase;
    private UsersRecyclerAdapter adapter;
    private boolean allUsersLoaded;

    public UsersPresenter(GetAllUsersUseCase allUsersUseCase,
                          GetPhotoUseCase photoUseCase) {
        this.allUsersUseCase = allUsersUseCase;
        this.photoUseCase = photoUseCase;
        users = new ArrayList<>();
        usersPhotos = new HashMap<>();
        adapter = new UsersRecyclerAdapter(users, usersPhotos);
        adapter.setUserClickListener(this);
        currentSortOrder = ApiConstants.ORDER_DEFAULT;
        allUsersLoaded = false;
    }

    public UsersRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void getAllUsers() {
        if(filtering) return;

        if (!BizareChatApp.getInstance().isNetworkConnected()) {
            getViewState().showNetworkError();
            return;
        }

        if (usersCount != 0 && currentUsersPage * ApiConstants.USERS_PER_PAGE >= usersCount) {
            allUsersLoaded = true;
            return;
        }

        getViewState().showLoading();

        allUsersUseCase.setPage(++currentUsersPage);
        allUsersUseCase.setOrder(currentSortOrder);
        allUsersUseCase.execute(new Subscriber<AllUsersResponse>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                getViewState().hideLoading();
                if (e instanceof SocketTimeoutException)
                    getViewState().showNetworkError();
                e.printStackTrace();
            }

            @Override
            public void onNext(AllUsersResponse response) {
                UserModel user;
                int insertCounter = 0;
                for (AllUsersResponse.Item item : response.getItems()) {
                    user = item.getUser();

                    if (user.getUserId().equals(currentUserId))
                        continue;

                    if(user.getFullName() == null){
                        user.setFullName("");
                    }

                    users.add(user);
                    insertCounter++;

                    if (user.getBlobId() != null) {
                        getAndAddPhoto(users.size() - 1, user.getUserId(), user.getBlobId());
                    } else {
                        usersPhotos.put(user.getUserId(), null);
                    }
                }
                if (usersCount == 0)
                    usersCount = response.getTotalEntries();
                if (usersCount == 1) {
                    getViewState().showAloneMessage();
                } else {
                    adapter.notifyItemRangeInserted(users.size() - insertCounter, insertCounter);
                }
                getViewState().hideLoading();
            }
        });
    }

    private void getAndAddPhoto(int position, Long userId, Integer blobId) {
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

    public void performFilter(String newText) {
        currentFilterQuery = newText;
        filtering = true;
        adapter.filterList(newText);
    }

    public void onFilterClose(){
        filtering = false;
    }

    public void sortByNameAsc(){
        if(currentSortOrder.equals(ApiConstants.ORDER_ASC_FULL_NAME))
            return;
        currentSortOrder = ApiConstants.ORDER_ASC_FULL_NAME;

        if(allUsersLoaded) {
            Collections.sort(users, new ComparatorNameAsc());
            adapter.notifyDataSetChanged();
            return;
        }

        currentUsersPage = 0;
        users.clear();
        adapter.notifyDataSetChanged();
        getAllUsers();
    }

    public void sortByNameDesc(){
        if(currentSortOrder.equals(ApiConstants.ORDER_DESC_FULL_NAME))
            return;
        currentSortOrder = ApiConstants.ORDER_DESC_FULL_NAME;

        if(allUsersLoaded) {
            Collections.sort(users, new ComparatorNameDesc());
            adapter.notifyDataSetChanged();
            return;
        }

        currentUsersPage = 0;
        users.clear();
        adapter.notifyDataSetChanged();
        getAllUsers();
    }

    public void sortDefault(){
        if(currentSortOrder.equals(ApiConstants.ORDER_DEFAULT))
            return;
        currentSortOrder = ApiConstants.ORDER_DEFAULT;

        if(allUsersLoaded) {
            Collections.sort(users, new ComparatorDefault());
            adapter.notifyDataSetChanged();
            return;
        }

        currentUsersPage = 0;
        users.clear();
        adapter.notifyDataSetChanged();
        getAllUsers();
    }

    public static class ComparatorNameAsc implements Comparator<UserModel> {
        @Override
        public int compare(UserModel user1, UserModel user2) {
            return user1.getFullName().compareToIgnoreCase(user2.getFullName());
        }
    }

    public static class ComparatorNameDesc implements Comparator<UserModel>{
        @Override
        public int compare(UserModel user1, UserModel user2) {
            return user2.getFullName().compareToIgnoreCase(user1.getFullName());
        }
    }

    public static class ComparatorDefault implements Comparator<UserModel>{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        @Override
        public int compare(UserModel user1, UserModel user2) {
            try {
                return format.parse(user2.getCreatedAt()).compareTo(format.parse(user1.getCreatedAt()));
            } catch (ParseException ex){
                throw new IllegalArgumentException(ex);
            }
        }
    }

    @Override
    public void onUserClick(int position) {
        getViewState().showUserInfo(users.get(position));
    }

    public String getCurrentFilterQuery() {
        return currentFilterQuery;
    }
}
