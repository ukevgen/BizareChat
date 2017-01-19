package com.internship.pbt.bizarechat.data.net;


import com.internship.pbt.bizarechat.data.net.services.SessionService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {
    private volatile static RetrofitApi INSTANCE;

    private SessionService sessionService;

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
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);

        return builder.build();
    }
}
