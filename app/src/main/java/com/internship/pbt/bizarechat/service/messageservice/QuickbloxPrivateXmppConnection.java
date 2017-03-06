package com.internship.pbt.bizarechat.service.messageservice;


import android.util.Log;

import com.internship.pbt.bizarechat.data.datamodel.MessageModel;
import com.internship.pbt.bizarechat.data.datamodel.MessageModelDao;
import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.domain.events.PublicMessageSentEvent;
import com.internship.pbt.bizarechat.domain.model.chatroom.MessageState;
import com.internship.pbt.bizarechat.presentation.BizareChatApp;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Displayed;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.DisplayedManager;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Markable;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Received;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.ReceivedManager;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class QuickbloxPrivateXmppConnection
        implements ConnectionListener, ChatMessageListener, ChatManagerListener {
    private volatile boolean connected = false;
    private static final String TAG = QuickbloxPrivateXmppConnection.class.getSimpleName();
    private static volatile QuickbloxPrivateXmppConnection INSTANCE;

    private XMPPTCPConnection privateChatConnection;
    private WeakReference<BizareChatMessageService> messageService;
    private ConcurrentLinkedQueue<Message> offlineMessages;
    private Map<String, Chat> privateChats;

    private QuickbloxPrivateXmppConnection(BizareChatMessageService messageService) {
        this.messageService = new WeakReference<>(messageService);
        offlineMessages = new ConcurrentLinkedQueue<>();
        privateChats = new HashMap<>();
    }

    public static QuickbloxPrivateXmppConnection getInstance(BizareChatMessageService messageService) {
        if (INSTANCE == null) {
            synchronized (QuickbloxPrivateXmppConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QuickbloxPrivateXmppConnection(messageService);
                }
            }
        }
        return INSTANCE;
    }

    public void sendDisplayedReceipt(String receiverJid, String stanzaId, String dialog_id) {
        Chat chat;
        if((chat = privateChats.get(receiverJid)) == null) {
            chat = ChatManager.getInstanceFor(privateChatConnection).createChat(receiverJid, this);
            privateChats.put(receiverJid, chat);
        }

        Message message = new Message(receiverJid);
        Displayed read = new Displayed(stanzaId);
        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("dialog_id", dialog_id);

        message.setStanzaId(StanzaIdUtil.newStanzaId());
        message.setType(Message.Type.chat);
        message.addExtension(read);
        message.addExtension(extension);

        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException ex) {
            offlineMessages.add(message);
        }
    }

    public void sendReceivedReceipt(String receiverJid, String stanzaId, String dialog_id) {
        Chat chat;
        if((chat = privateChats.get(receiverJid)) == null) {
            chat = ChatManager.getInstanceFor(privateChatConnection).createChat(receiverJid, this);
            privateChats.put(receiverJid, chat);
        }

        Message message = new Message(receiverJid);
        Received delivered = new Received(stanzaId);
        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("dialog_id", dialog_id);

        message.setStanzaId(StanzaIdUtil.newStanzaId());
        message.setType(Message.Type.chat);
        message.addExtension(delivered);
        message.addExtension(extension);

        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException ex) {
            offlineMessages.add(message);
        }
    }

    public void sendPrivateMessage(String body, String receiverJid, long timestamp, String stanzaId) {
        Log.d(TAG, "Sending message to : " + receiverJid);

        Chat chat;
        if((chat = privateChats.get(receiverJid)) == null) {
            chat = ChatManager.getInstanceFor(privateChatConnection).createChat(receiverJid, this);
            privateChats.put(receiverJid, chat);
        }

        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");
        extension.setProperty("save_to_history", "1");

        Message message = new Message(receiverJid);
        message.setStanzaId(stanzaId);
        message.setBody(body);
        message.setType(Message.Type.chat);
        message.addExtension(new Markable());
        message.addExtension(extension);

        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException ex) {
            offlineMessages.add(message);
        }
    }

    public void sendPublicMessage(String body, String chatJid, long timestamp, String stanzaId) {
        Log.d(TAG, "Sending message to : " + chatJid);

        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");
        extension.setProperty("save_to_history", "1");

        Message message = new Message(chatJid);
        message.setStanzaId(stanzaId);
        message.setBody(body);
        message.setType(Message.Type.groupchat);
        message.addExtension(extension);

        try {
            privateChatConnection.sendStanza(message);
        } catch (SmackException.NotConnectedException ex) {
            offlineMessages.add(message);
        }
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        if(message.getBody() == null) return;

        if (message.getType() == Message.Type.groupchat) {
            messageService.get().processPublicMessage(message);
            return;
        }

        messageService.get().processPrivateMessage(message);
    }

    @Override public void chatCreated(Chat chat, boolean createdLocally) {
        if(!privateChats.containsKey(chat.getParticipant())){
            chat.addMessageListener(this);
            privateChats.put(chat.getParticipant().split("/")[0], chat);
        }
    }

    public void connect() throws IOException, XMPPException, SmackException {
        initPrivateConnection();
        privateChatConnection.connect();
        privateChatConnection.login();
    }

    public void disconnect() {
        if (privateChatConnection != null)
            privateChatConnection.disconnect();
        privateChatConnection = null;
    }

    @Override
    public void connected(XMPPConnection xmppConnection) {
        Log.d(TAG, "Connected Successfully");
        connected = true;
    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {
        Log.d(TAG, "Authenticated Successfully");
        connected = true;
        ChatManager.getInstanceFor(privateChatConnection).addChatListener(this);
    }

    @Override
    public void connectionClosed() {
        connected = false;
        Log.d(TAG, "Connection closed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        connected = false;
        Log.d(TAG, "Connection closed on error; error " + e.toString());
    }

    @Override
    public void reconnectionSuccessful() {
        connected = true;
        if (!offlineMessages.isEmpty()) {
            JobExecutor.getInstance().execute(() -> {
                while (!offlineMessages.isEmpty()) {
                    try {
                        Message message = offlineMessages.peek();
                        privateChatConnection.sendStanza(message);
                        if(message.getType() == Message.Type.groupchat) {
                            EventBus.getDefault().post(new PublicMessageSentEvent(message.getStanzaId()));

                            MessageModel messageModel = BizareChatApp.getInstance().getDaoSession().getMessageModelDao()
                                    .queryBuilder()
                                    .where(MessageModelDao.Properties.MessageId.eq(message.getStanzaId()))
                                    .unique();
                            messageModel.setRead(MessageState.DELIVERED);
                            BizareChatApp.getInstance().getDaoSession().getMessageModelDao().updateInTx(messageModel);
                        }
                    } catch (SmackException ex) {
                        break;
                    }
                    offlineMessages.poll();
                }
            });
        }
        Log.d(TAG, "Reconnection successful");
    }

    @Override
    public void reconnectingIn(int i) {
        Log.d(TAG, "Reconnecting in " + i + " sec");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        connected = false;
        Log.d(TAG, "Reconnection failed on error; error " + e.toString());
    }

    private void initPrivateConnection() {
        long currentUserId = CurrentUser.getInstance().getCurrentUserId();
        String currentUserPassword = CurrentUser.getInstance().getCurrentPassword();
        String userName = currentUserId + "-" + ApiConstants.APP_ID;
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setUsernameAndPassword(userName, currentUserPassword);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setServiceName(ApiConstants.CHAT_END_POINT);
        configBuilder.setHost(ApiConstants.CHAT_END_POINT);
        configBuilder.setDebuggerEnabled(true);

        privateChatConnection = new XMPPTCPConnection(configBuilder.build());
        privateChatConnection.addConnectionListener(this);

        ReconnectionManager manager = ReconnectionManager.getInstanceFor(privateChatConnection);
        manager.enableAutomaticReconnection();
        manager.setReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.FIXED_DELAY);
        manager.setFixedDelay(15);

        ProviderManager.addExtensionProvider(Displayed.ELEMENT, Displayed.NAMESPACE, new Displayed.Provider());
        DisplayedManager.getInstanceFor(privateChatConnection).addDisplayedListener(
                (fromJid, toJid, receiptId, receipt) -> {
                    messageService.get().processDisplayed(fromJid, toJid, receiptId, receipt);
                });

        ProviderManager.addExtensionProvider(Received.ELEMENT, Received.NAMESPACE, new Received.Provider());
        ReceivedManager.getInstanceFor(privateChatConnection).addReceivedListener(
                (fromJid, toJid, receiptId, receipt) -> {
                    messageService.get().processReceived(fromJid, toJid, receiptId, receipt);
                });
    }
}