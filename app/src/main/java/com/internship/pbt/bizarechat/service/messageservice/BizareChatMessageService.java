package com.internship.pbt.bizarechat.service.messageservice;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.domain.events.PrivateMessageEvent;
import com.internship.pbt.bizarechat.domain.events.PublicMessageEvent;
import com.internship.pbt.bizarechat.domain.events.ReceiptReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.io.IOException;

import rx.Observable;


public class BizareChatMessageService extends Service {
    private static ConnectivityManager connectivityManager;
    private QuickbloxPrivateXmppConnection privateConnection;
    private QuickbloxGroupXmppConnection groupConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        privateConnection = QuickbloxPrivateXmppConnection.getInstance(this);
        groupConnection = QuickbloxGroupXmppConnection.getInstance(this);
    }

    public Observable<Boolean> sendPrivateMessage(String body, String receiverJid, long timestamp){
        return Observable.fromCallable(() -> {
            privateConnection.sendMessage(body, receiverJid, timestamp);
            return true;
        });
    }

    public Observable<Boolean> sendPublicMessage(String body, String chatJid, long timestamp){
        return Observable.fromCallable(() -> {
            groupConnection.sendMessage(body, chatJid, timestamp);
            return true;
        });
    }

    void processPrivateMessage(Message message){
        EventBus.getDefault().post(new PrivateMessageEvent(message));
    }

    void processPublicMessage(Message message){
        EventBus.getDefault().post(new PublicMessageEvent(message));
    }

    void onReceiptReceived(){
        EventBus.getDefault().post(new ReceiptReceivedEvent());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        JobExecutor.getInstance().execute(() -> {
            try {
                privateConnection.connect();
                groupConnection.connect();
            }catch (XMPPException | SmackException | IOException ex){
                EventBus.getDefault().post(ex);
            }
        });
        return new MessageServiceBinder<>(this);
    }

    @Override
    public void onRebind(Intent intent) {
        JobExecutor.getInstance().execute(() -> {
            try {
                privateConnection.connect();
                groupConnection.connect();
            }catch (XMPPException | SmackException | IOException ex){
                EventBus.getDefault().post(ex);
            }
        });
    }

    @Override
    public boolean onUnbind(Intent intent) {
        JobExecutor.getInstance().execute(() -> {
            if(privateConnection != null) privateConnection.disconnect();
            if(groupConnection != null) groupConnection.disconnect();
        });
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public static boolean isNetworkAvailable(){
        return connectivityManager.getActiveNetworkInfo().isAvailable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        privateConnection = null;
        groupConnection = null;
    }
}
