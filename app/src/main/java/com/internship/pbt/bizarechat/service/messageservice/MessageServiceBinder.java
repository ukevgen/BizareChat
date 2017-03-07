package com.internship.pbt.bizarechat.service.messageservice;

import android.app.Service;
import android.os.Binder;

import java.lang.ref.WeakReference;


public class MessageServiceBinder<S extends Service> extends Binder {
    private final WeakReference<S> mService;

    public MessageServiceBinder(final S service) {
        mService = new WeakReference<>(service);
    }

    public S getService() {
        return mService.get();
    }
}