package com.internship.pbt.bizarechat.service.messageservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;

import com.internship.pbt.bizarechat.data.datamodel.DaoSession;
import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.data.datamodel.MessageModelDao;
import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.domain.events.DisplayedEvent;
import com.internship.pbt.bizarechat.domain.events.PrivateMessageEvent;
import com.internship.pbt.bizarechat.domain.events.ReceivedEvent;
import com.internship.pbt.bizarechat.domain.model.chatroom.MessageState;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.view.activity.MainActivity;
import com.internship.pbt.bizarechat.service.util.NotificationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;


public class BizareChatMessageService extends Service {
    private static final String TAG = BizareChatMessageService.class.getSimpleName();
    private QuickbloxPrivateXmppConnection privateConnection;
    private QuickbloxGroupXmppConnection groupConnection;
    private NotificationUtils notificationUtils;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        privateConnection = QuickbloxPrivateXmppConnection.getInstance(this);
        groupConnection = QuickbloxGroupXmppConnection.getInstance(this);
        notificationUtils = new NotificationUtils(this);
        daoSession = BizareChatApp.getInstance().getDaoSession();
    }

    public Observable<Boolean> sendPrivateMessage(String body, String receiverJid, long timestamp, String stanzaId){
        return Observable.fromCallable(() -> {
            privateConnection.sendMessage(body, receiverJid, timestamp, stanzaId);
            return true;
        });
    }

    public void sendPrivateReadStatusMessage(String receiver, String stanzaId, String dialog_id){
        JobExecutor.getInstance().execute(() -> {
            try{
                privateConnection.sendDisplayedReceipt(receiver, stanzaId, dialog_id);
            } catch (SmackException ex){
                Log.d(TAG, ex.getMessage(), ex);
            }
        });
    }

    public void sendPrivateDeliveredStatusMessage(String receiver, String stanzaId, String dialog_id){
        JobExecutor.getInstance().execute(() -> {
            try{
                privateConnection.sendReceivedReceipt(receiver, stanzaId, dialog_id);
            } catch (SmackException ex){
                Log.d(TAG, ex.getMessage(), ex);
            }
        });
    }

    void processPrivateMessage(Message message){
        MessageModel messageModel = getMessageModel(message);
        saveMessageToDb(messageModel);
        if(NotificationUtils.isAppIsInBackground(getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            notificationUtils.showNotificationMessage(
                    message.getFrom(),
                    message.getBody(),
                    System.currentTimeMillis() + "",
                    intent);
        } else {
            EventBus.getDefault().post(new PrivateMessageEvent(messageModel));
        }
    }

    public Observable<Boolean> sendPublicMessage(String body, String chatJid, long timestamp){
        return Observable.fromCallable(() -> {
            groupConnection.sendMessage(body, chatJid, timestamp);
            return true;
        });
    }

    void processPublicMessage(Message message){
//        EventBus.getDefault().post(new PublicMessageEvent(message));
    }

    void processReceived(String fromJid, String toJid, String receiptId, Stanza receipt){
        JobExecutor.getInstance().execute(() -> {
            MessageModel messageModel = daoSession.getMessageModelDao()
                    .queryBuilder()
                    .where(MessageModelDao.Properties.MessageId.eq(receiptId))
                    .unique();

            QueryBuilder<MessageModel> builder = daoSession.getMessageModelDao()
                    .queryBuilder();

            List<MessageModel> messages = builder.where(
                    builder.and(MessageModelDao.Properties.DateSent.le(messageModel.getDateSent()),
                            MessageModelDao.Properties.ChatDialogId.eq(messageModel.getChatDialogId()),
                            MessageModelDao.Properties.SenderId.eq(CurrentUser.getInstance().getCurrentUserId().intValue()),
                            MessageModelDao.Properties.Read.eq(MessageState.DEFAULT)))
                    .list();

            for(MessageModel message : messages){
                message.setRead(MessageState.DELIVERED);
            }

            EventBus.getDefault().post(new ReceivedEvent(messages));

            daoSession.getMessageModelDao().updateInTx(messages);
        });
    }

    void processDisplayed(String fromJid, String toJid, String receiptId, Stanza receipt){
        JobExecutor.getInstance().execute(() -> {
            MessageModel messageModel = daoSession.getMessageModelDao()
                    .queryBuilder()
                    .where(MessageModelDao.Properties.MessageId.eq(receiptId))
                    .unique();

            QueryBuilder<MessageModel> builder = daoSession.getMessageModelDao()
                    .queryBuilder();

            List<MessageModel> messages = builder.where(
                    builder.and(MessageModelDao.Properties.DateSent.le(messageModel.getDateSent()),
                            MessageModelDao.Properties.SenderId.eq(CurrentUser.getInstance().getCurrentUserId().intValue()),
                            MessageModelDao.Properties.Read.notEq(MessageState.READ)))
                    .list();

            for(MessageModel message : messages){
                message.setRead(MessageState.READ);
            }

            EventBus.getDefault().post(new DisplayedEvent(messages));

            daoSession.getMessageModelDao().updateInTx(messages);
        });
    }

    private void saveMessageToDb(MessageModel message){
        JobExecutor.getInstance().execute(() -> {
            daoSession.getMessageModelDao().insert(message);
        });
    }

    private MessageModel getMessageModel(Message message){
        long timestamp = 0;
        String dialog_id = "";
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(message.getExtensions().get(0).toXML().toString()));
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("date_sent")){
                            timestamp = Long.valueOf(parser.nextText());
                        }
                        if(parser.getName().equals("dialog_id")){
                            dialog_id = parser.nextText();
                        }
                        break;
                    default:
                        break;
                }
                parser.next();
            }
        } catch (XmlPullParserException | IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }

        MessageModel messageModel = new MessageModel(
                message.getStanzaId(),
                "",
                "",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                dialog_id,
                timestamp,
                message.getBody(),
                Integer.valueOf(message.getTo().split("-")[0]),
                Integer.valueOf(message.getFrom().split("-")[0]),
                MessageState.DEFAULT);
        return messageModel;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MessageServiceBinder<>(this);
    }

    @Override
    public void onRebind(Intent intent) {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobExecutor.getInstance().execute(() -> {
            try {
                privateConnection.connect();
//                groupConnection.connect();
            }catch (XMPPException | SmackException | IOException ex){
                EventBus.getDefault().post(ex);
            }
        });
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        privateConnection = null;
        groupConnection = null;
    }
}
