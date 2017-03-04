package com.internship.pbt.bizarechat.service.messageservice;


import android.util.Log;

import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Displayed;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.DisplayedManager;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Markable;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Received;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.ReceivedManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.lang.ref.WeakReference;

public final class QuickbloxPrivateXmppConnection
        implements ConnectionListener, ChatMessageListener {
    private static final String TAG = QuickbloxPrivateXmppConnection.class.getSimpleName();
    private static volatile QuickbloxPrivateXmppConnection INSTANCE;

    private XMPPTCPConnection privateChatConnection;
    private WeakReference<BizareChatMessageService> messageService;

    private QuickbloxPrivateXmppConnection(BizareChatMessageService messageService) {
        this.messageService = new WeakReference<>(messageService);
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

    public void sendDisplayedReceipt(String receiverJid, String stanzaId, String dialog_id) throws SmackException {
        Message message = new Message(receiverJid);
        Displayed read = new Displayed(stanzaId);
        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("dialog_id", dialog_id);

        message.setStanzaId(StanzaIdUtil.newStanzaId());
        message.setType(Message.Type.chat);
        message.addExtension(read);
        message.addExtension(extension);

        privateChatConnection.sendStanza(message);
    }

    public void sendReceivedReceipt(String receiverJid, String stanzaId, String dialog_id) throws SmackException {
        Message message = new Message(receiverJid);
        Received delivered = new Received(stanzaId);
        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("dialog_id", dialog_id);

        message.setStanzaId(StanzaIdUtil.newStanzaId());
        message.setType(Message.Type.chat);
        message.addExtension(delivered);
        message.addExtension(extension);

        privateChatConnection.sendStanza(message);
    }

    public void sendMessage(String body, String receiverJid, long timestamp, String stanzaId) throws SmackException {
        Log.d(TAG, "Sending message to : " + receiverJid);

        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");
        extension.setProperty("save_to_history", "1");

        Message message = new Message(receiverJid);
        message.setStanzaId(stanzaId);
        message.setBody(body);
        message.setType(Message.Type.chat);
        message.addExtension(new Markable());
        message.addExtension(extension);

        privateChatConnection.sendStanza(message);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        messageService.get().processPrivateMessage(message);
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
    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {
        Log.d(TAG, "Authenticated Successfully");
    }

    @Override
    public void connectionClosed() {
        Log.d(TAG, "Connection closed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.d(TAG, "Connection closed on error; error " + e.toString());
    }

    @Override
    public void reconnectionSuccessful() {
        Log.d(TAG, "Reconnection successful");
    }

    @Override
    public void reconnectingIn(int i) {
        Log.d(TAG, "Reconnecting in " + i + " sec");
    }

    @Override
    public void reconnectionFailed(Exception e) {
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