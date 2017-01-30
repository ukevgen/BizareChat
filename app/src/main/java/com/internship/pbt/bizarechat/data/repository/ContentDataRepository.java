package com.internship.pbt.bizarechat.data.repository;

import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.util.Log;

import com.internship.pbt.bizarechat.data.cache.CacheSharedPreferences;
import com.internship.pbt.bizarechat.data.datamodel.response.CreateFileResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.UploadFileResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.requests.FileCreateRequest;
import com.internship.pbt.bizarechat.data.net.requests.FileUploadConfirmRequest;
import com.internship.pbt.bizarechat.data.net.requests.UserUpdateBlobId;
import com.internship.pbt.bizarechat.data.net.services.ContentService;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;


public class ContentDataRepository implements ContentRepository {

    private ContentService contentService;
    private String name;
    private volatile String blobId = "";
    private CacheSharedPreferences cache;

    public ContentDataRepository(Context context) {
        contentService = RetrofitApi.getRetrofitApi().getContentService();
        cache = CacheSharedPreferences.getInstance(context);
    }

    public Observable<Response<Void>> uploadFile(String contentType, File file, String name) {
        FileCreateRequest.Blob createBlob = new FileCreateRequest.Blob();
        createBlob.setContentType(contentType);
        createBlob.setName(file.getName());

        FileCreateRequest fileCreateRequest = new FileCreateRequest();
        fileCreateRequest.setBlob(createBlob);
        Log.d("uploadAvatar", "create file " + file.getName());

        return contentService.createFile(UserToken.getInstance().getToken(), fileCreateRequest)
                .flatMap(new Func1<CreateFileResponse, Observable<UploadFileResponse>>() {
                    @Override
                    public Observable<UploadFileResponse> call(CreateFileResponse createFileResponse) {
                        Log.d("uploadAvatar", "-1.5 confirm " + blobId);

                        blobId = createFileResponse.getBlob().getId();
                        String params = createFileResponse.getBlob().getBlobObjectAccess().getParams();
                        params = params.replaceAll("&amp;", "&");

                        Map<String, RequestBody> paramsMap = composeFormParamsMap(params);
                        MultipartBody.Part filePart = prepareFilePart(file, contentType, name);
                        Log.d("uploadAvatar", "-1 confirm " + blobId);

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

                        if (name.equals(CurrentUser.CURRENT_AVATAR) )
                            cache.putAccountAvatarBlobId(Long.parseLong(blobId));
                        Log.d("uploadAvatar", "confirm " + blobId);
                        return contentService.confirmFileUploaded(
                                UserToken.getInstance().getToken(),
                                blobId,
                                confirmRequest).flatMap(new Func1<Response<Void>, Observable<Response<Void>>>() {
                            @Override
                            public Observable<Response<Void>> call(Response<Void> response) {
                                Log.d("uploadAvatar", "updateUserID");
                                return contentService.updateUserBlobId(CurrentUser.getInstance().getCurrentUserId(),
                                        new UserUpdateBlobId(Integer.parseInt(blobId)));
                            }
                        });
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
