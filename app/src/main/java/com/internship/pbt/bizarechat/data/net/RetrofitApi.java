package com.internship.pbt.bizarechat.data.net;


import android.util.Log;

import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.data.net.services.FileManagerService;
import com.internship.pbt.bizarechat.data.net.services.SessionService;
import com.internship.pbt.bizarechat.data.net.services.UserService;
import com.internship.pbt.bizarechat.data.repository.SessionDataRepository;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.domain.model.Session;
import com.internship.pbt.bizarechat.domain.repository.SessionRepository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class RetrofitApi {
    private volatile static RetrofitApi INSTANCE;

    private SessionService sessionService;
    private UserService userService;
    private FileManagerService fileManagerService;

    private RetrofitApi(){
        OkHttpClient okHttpClient = createClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.API_END_POINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        buildServices(retrofit);

    }

    public static RetrofitApi getRetrofitApi() {
        if (INSTANCE == null) {
            synchronized (RetrofitApi.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitApi();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * All API services should be implemented here
     */
    private void buildServices(Retrofit retrofit){
        sessionService = retrofit.create(SessionService.class);
        userService = retrofit.create(UserService.class);
        fileManagerService = retrofit.create(FileManagerService.class);
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public UserService getUserService(){ return userService; }

    public FileManagerService getFileManagerService(){
        return fileManagerService;
    }

    private OkHttpClient createClient() {
        SessionTokenAuthenticator authenticator = new SessionTokenAuthenticator();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
        builder.authenticator(authenticator);
        return builder.build();
    }

    private class SessionTokenAuthenticator implements Authenticator{
        private final String LOG_TAG = SessionTokenAuthenticator.class.getSimpleName();
        private String newToken;

        @Override
        public Request authenticate(Route route, Response response) throws IOException{
            if(response.body().string().contains("Unauthorized")){
                // Here we force the server return 422 code to us in next response.
                // This code we will catch in our LoginPresenter to show correct message.
                return response.request().newBuilder()
                        .post(RequestBody.create(null, new byte[0]))
                        .build();
            }

            SessionRepository sessionRepository = new SessionDataRepository();
            sessionRepository.getSession()
                    .subscribeOn(Schedulers.from(JobExecutor.getInstance()))
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<Session>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(LOG_TAG, e.getMessage(), e);
                        }

                        @Override
                        public void onNext(Session session) {
                            newToken = session.getToken();
                            UserToken.getInstance().saveToken(newToken);
                        }
                    });

            return response.request().newBuilder()
                    .addHeader(ApiConstants.TOKEN_HEADER_NAME, newToken)
                    .build();
        }
    }
}
