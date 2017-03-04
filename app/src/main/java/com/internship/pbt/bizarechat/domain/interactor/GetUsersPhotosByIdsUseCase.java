package com.internship.pbt.bizarechat.domain.interactor;


import android.graphics.Bitmap;

import com.internship.pbt.bizarechat.data.datamodel.UserModel;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;

import java.util.List;
import java.util.Map;

import rx.Observable;

public class GetUsersPhotosByIdsUseCase extends UseCase<Map<Long, Bitmap>> {
    private ContentRepository contentRepository;
    private List<UserModel> users;

    public GetUsersPhotosByIdsUseCase(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    @Override
    protected Observable<Map<Long, Bitmap>> buildUseCaseObservable() {
        return contentRepository.getUsersPhotos(users);
    }
}
