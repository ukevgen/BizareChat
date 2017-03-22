package com.internship.pbt.bizarechat.service.gcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;


public class BizareChatFirebaseIdService extends FirebaseInstanceIdService {

    private static final String TAG = BizareChatFirebaseIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        storeRegIdInPref(refreshedToken);

    }

    private void storeRegIdInPref(String token) {
        CurrentUser.getInstance().setFirebaseToken(token);
    }
}
