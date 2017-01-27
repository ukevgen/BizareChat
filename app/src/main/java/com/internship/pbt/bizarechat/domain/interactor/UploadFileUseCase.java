package com.internship.pbt.bizarechat.domain.interactor;

import android.net.Uri;

import com.internship.pbt.bizarechat.data.repository.ContentDataRepository;

import java.io.File;

import retrofit2.Response;
import rx.Observable;

public class UploadFileUseCase extends UseCase<Response<Void>> {

    private ContentDataRepository contentDataRepository;
    private String contentType;
    private File file;
    private String name;

    public UploadFileUseCase(ContentDataRepository contentDataRepository,
                             String contentType,
                             File file,
                             String name){
        this.contentDataRepository = contentDataRepository;
        this.contentType = contentType;
        this.file = file;
    }

    @Override
    protected Observable<Response<Void>> buildUseCaseObservable() {
        return contentDataRepository.uploadFile(contentType, file, name);
    }
}
