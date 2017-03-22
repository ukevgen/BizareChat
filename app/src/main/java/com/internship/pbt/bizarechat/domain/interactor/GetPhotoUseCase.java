package com.internship.pbt.bizarechat.domain.interactor;

import android.graphics.Bitmap;

import com.internship.pbt.bizarechat.domain.repository.ContentRepository;

import rx.Observable;


public class GetPhotoUseCase extends UseCase<Bitmap> {
    private ContentRepository contentRepository;
    private int blobId;

    public GetPhotoUseCase(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public void setBlobId(int blobId) {
        this.blobId = blobId;
    }

    @Override
    protected Observable<Bitmap> buildUseCaseObservable() {
        return contentRepository.getPhoto(blobId);
    }
}
