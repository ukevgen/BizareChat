package com.internship.pbt.bizarechat.domain.interactor;

import com.internship.pbt.bizarechat.domain.repository.ContentRepository;

import java.io.File;

import rx.Observable;

public class UploadFileUseCase extends UseCase<Integer> {

    private ContentRepository contentDataRepository;
    private String contentType;
    private File file;
    private String name;

    public UploadFileUseCase(ContentRepository contentDataRepository,
                             String contentType,
                             File file,
                             String name) {
        this.contentDataRepository = contentDataRepository;
        this.contentType = contentType;
        this.file = file;
        this.name = name;
    }

    @Override
    protected Observable<Integer> buildUseCaseObservable() {
        return contentDataRepository.uploadFile(contentType, file, name);
    }
}
