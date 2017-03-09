package com.internship.pbt.bizarechat.data.repository;


import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.data.datamodel.UserModelDao;
import com.internship.pbt.bizarechat.data.datamodel.response.AllUsersResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.UserByIdResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.domain.repository.UserRepository;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UserDataRepository implements UserRepository {
    private static final String TAG = UserDataRepository.class.getSimpleName();

    private UserService userService;

    public UserDataRepository(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Observable<Response<Void>> resetUserPassword(String email) {
        return userService.resetUserPassword(UserToken.getInstance().getToken(), email);
    }

    @Override
    public Observable<AllUsersResponse> getAllUsers(Integer page, String order) {
        return userService.getAllUsers(
                UserToken.getInstance().getToken(),
                order,
                page,
                ApiConstants.USERS_PER_PAGE);
    }

    @Override
    public Observable<AllUsersResponse> getUsersByFullName(Integer page, String query) {
        return userService.getUsersByFullName(
                UserToken.getInstance().getToken(),
                page,
                ApiConstants.USERS_PER_PAGE,
                query);
    }

    @Override
    public Observable<UserModel> getUserById(Integer id) {
        final DaoSession daoSession = BizareChatApp.getInstance().getDaoSession();
        UserModel user = daoSession.getUserModelDao().queryBuilder()
                .where(UserModelDao.Properties.UserId.eq(id.longValue()))
                .unique();

        if(user != null)
            return Observable.just(user);
        else
            return userService.getUserById(UserToken.getInstance().getToken(), id)
                    .flatMap(new Func1<UserByIdResponse, Observable<UserModel>>() {
                        @Override public Observable<UserModel> call(UserByIdResponse userModel) {
                            daoSession.getUserModelDao().insertInTx(userModel.getUser());
                            return Observable.just(userModel.getUser());
                        }
                    });
    }

    @Override
    public Observable<List<UserModel>> getUsersByIds(List<Integer> ids){
        final List<UserModel> users = new ArrayList<>();
        return Observable.fromCallable(() -> {
            Exception exception = new Exception();
            for(Integer id : ids){
                getUserById(id).subscribeOn(Schedulers.immediate())
                        .observeOn(Schedulers.immediate())
                        .subscribe(new Subscriber<UserModel>() {
                            @Override public void onCompleted() {

                            }

                            @Override public void onError(Throwable e) {
                                exception.initCause(e);
                            }

                            @Override public void onNext(UserModel userModel) {
                                users.add(userModel);
                            }
                        });
            }
            if(exception.getCause() != null)
                throw exception;
            return users;
        });
    }
}