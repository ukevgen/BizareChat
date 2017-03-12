package com.internship.pbt.bizarechat.service.gcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.domain.events.GcmMessageReceivedEvent;
import com.internship.pbt.bizarechat.logs.Logger;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.service.util.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;


public class BizareChatFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = BizareChatFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Logger.logExceptionToFabric(e);
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            EventBus.getDefault().post(new GcmMessageReceivedEvent(message));
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            String message = json.getString("message");

            Log.e(TAG, "message: " + message);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                EventBus.getDefault().post(new GcmMessageReceivedEvent(message));
            } else {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                showNotificationMessage(getApplicationContext(), message, resultIntent);
            }
        } catch (JSONException e) {
            Logger.logExceptionToFabric(e);
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Logger.logExceptionToFabric(e);
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String timestamp = String.valueOf(System.currentTimeMillis());
        notificationUtils.showNotificationMessage(context.getString(R.string.app_name), message, timestamp, intent);
    }

}
