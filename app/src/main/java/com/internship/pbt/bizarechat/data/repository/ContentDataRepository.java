package com.internship.pbt.bizarechat.data.repository;

import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.internship.pbt.bizarechat.data.cache.CacheUsersPhotos;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateFileResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.UploadFileResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.requests.FileCreateRequest;
import com.internship.pbt.bizarechat.data.net.requests.FileUploadConfirmRequest;
import com.internship.pbt.bizarechat.data.net.requests.UserUpdateBlobId;
import com.internship.pbt.bizarechat.data.net.services.ContentService;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;


public class ContentDataRepository implements ContentRepository {

    private ContentService contentService;
    private String name;
    private volatile String blobId = "";
    private CacheUsersPhotos cacheUsersPhotos;

    public ContentDataRepository(ContentService retrofitApi,
                                 CacheUsersPhotos cacheUsersPhotos) {
        this.cacheUsersPhotos = cacheUsersPhotos;
        contentService = retrofitApi;
    }

    public Observable<Bitmap> getPhoto(final Integer blobId) {
        return Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return cacheUsersPhotos.getPhoto(blobId);
            }
        })
                .flatMap(new Func1<Bitmap, Observable<Bitmap>>() {
                    @Override
                    public Observable<Bitmap> call(Bitmap bitmap) {
                        if (bitmap != null)
                            return Observable.just(bitmap);
                        else {
                            return contentService.downloadFile(UserToken.getInstance().getToken(), blobId)
                                    .flatMap(new Func1<ResponseBody, Observable<Bitmap>>() {
                                        @Override
                                        public Observable<Bitmap> call(ResponseBody responseBody) {
                                            return Observable.just(cacheUsersPhotos.savePhoto(responseBody, blobId));
                                        }
                                    });
                        }
                    }
                });
    }


    @Override
    public Observable<Integer> uploadFile(String contentType, File file, String name) {
        FileCreateRequest.Blob createBlob = new FileCreateRequest.Blob();
        createBlob.setContentType(contentType);
        createBlob.setName(file.getName());

        FileCreateRequest fileCreateRequest = new FileCreateRequest();
        fileCreateRequest.setBlob(createBlob);

        return contentService.createFile(UserToken.getInstance().getToken(), fileCreateRequest)
                .flatMap(new Func1<CreateFileResponse, Observable<UploadFileResponse>>() {
                    @Override
                    public Observable<UploadFileResponse> call(CreateFileResponse createFileResponse) {

                        blobId = createFileResponse.getBlob().getId();
                        String params = createFileResponse.getBlob().getBlobObjectAccess().getParams();
                        params = params.replaceAll("&amp;", "&");

                        Map<String, RequestBody> paramsMap = composeFormParamsMap(params);
                        MultipartBody.Part filePart = prepareFilePart(file, contentType, name);

                        return contentService.uploadFile(ApiConstants.AMAZON_END_POINT, paramsMap, filePart);
                    }
                })
                .flatMap(new Func1<UploadFileResponse, Observable<Response<Void>>>() {
                    @Override
                    public Observable<Response<Void>> call(UploadFileResponse uploadFileResponse) {
                        FileUploadConfirmRequest.Blob confirmBlob = new FileUploadConfirmRequest.Blob();
                        String size = String.valueOf(file.getTotalSpace());
                        confirmBlob.setSize(size);

                        FileUploadConfirmRequest confirmRequest = new FileUploadConfirmRequest();
                        confirmRequest.setBlob(confirmBlob);

                        return contentService.confirmFileUploaded(
                                UserToken.getInstance().getToken(),
                                blobId,
                                confirmRequest).flatMap(new Func1<Response<Void>, Observable<Response<Void>>>() {
                            @Override
                            public Observable<Response<Void>> call(Response<Void> response) {
                                Log.d("uploadAvatar", "updateUserID");
                                return contentService.updateUserBlobId(UserToken.getInstance().getToken(),
                                        CurrentUser.getInstance().getCurrentUserId(),
                                        new UserUpdateBlobId(Integer.parseInt(blobId)));
                            }
                        });
                    }
                }).flatMap(new Func1<Response<Void>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Response<Void> response) {
                        Integer blobIds = Integer.parseInt(blobId);
                        return Observable.just(blobIds);
                    }
                });
    }

    private RequestBody createPartFromString(String source) {
        return RequestBody.create(MediaType.parse("text/plain"), source);
    }

    private Map<String, RequestBody> composeFormParamsMap(String source) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.registerParameter(ApiConstants.AMAZON_EXPIRES, UrlQuerySanitizer.getSpaceLegal());
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(source);
        Map<String, RequestBody> result = new HashMap<>();

        result.put(ApiConstants.AMAZON_CONTENT_TYPE,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_CONTENT_TYPE)));
        result.put(ApiConstants.AMAZON_EXPIRES,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_EXPIRES)));
        result.put(ApiConstants.AMAZON_ACL,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_ACL)));
        result.put(ApiConstants.AMAZON_KEY,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_KEY)));
        result.put(ApiConstants.AMAZON_POLICY,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_POLICY)));
        result.put(ApiConstants.AMAZON_ACTION_STATUS,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_ACTION_STATUS)));
        result.put(ApiConstants.AMAZON_ALGORITHM,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_ALGORITHM)));
        result.put(ApiConstants.AMAZON_CREDENTIAL,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_CREDENTIAL)));
        result.put(ApiConstants.AMAZON_DATE,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_DATE)));
        result.put(ApiConstants.AMAZON_SIGNATURE,
                createPartFromString(sanitizer.getValue(ApiConstants.AMAZON_SIGNATURE)));

        return result;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(File file, String contentType, String name) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        if (name == null)
            this.name = file.getName();
        else
            this.name = name;
        return MultipartBody.Part.createFormData(
                ApiConstants.AMAZON_FILE,
                this.name,
                requestFile);
    }
}
