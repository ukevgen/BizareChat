package com.internship.pbt.bizarechat.data.repository;

import android.net.UrlQuerySanitizer;

import com.internship.pbt.bizarechat.data.datamodel.response.CreateFileResponse;
import com.internship.pbt.bizarechat.data.datamodel.response.UploadFileResponse;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.requests.FileCreateRequest;
import com.internship.pbt.bizarechat.data.net.requests.FileUploadConfirmRequest;
import com.internship.pbt.bizarechat.data.net.services.ContentService;
import com.internship.pbt.bizarechat.domain.repository.ContentRepository;

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
    private volatile String blobId = "";

    public ContentDataRepository(){
        contentService = RetrofitApi.getRetrofitApi().getContentService();
    }

    public String getBlobId() {
        return blobId;
    }

    public Observable<Response<Void>> uploadFile(String contentType, File file){
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
                MultipartBody.Part filePart = prepareFilePart(file, contentType);

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
                                confirmRequest);
                    }
                });
    }

    private RequestBody createPartFromString(String source){
        return RequestBody.create(MediaType.parse("text/plain"), source);
    }

    private Map<String, RequestBody> composeFormParamsMap(String source){
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

    private MultipartBody.Part prepareFilePart(File file, String contentType) {
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);

        return MultipartBody.Part.createFormData(
                ApiConstants.AMAZON_FILE,
                file.getName(),
                requestFile);
    }
}
