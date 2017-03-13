package com.internship.pbt.bizarechat.service.messageservice;

import android.util.Log;

import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.service.messageservice.extentions.read.ReadReceipt;
import com.internship.pbt.bizarechat.service.messageservice.extentions.read.ReadReceiptManager;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Random;


public final class QuickbloxGroupXmppConnection implements ConnectionListener, MessageListener {
    private static final String TAG = QuickbloxGroupXmppConnection.class.getSimpleName();
    private static volatile QuickbloxGroupXmppConnection INSTANCE;

    private XMPPTCPConnection groupChatConnection;
    private WeakReference<BizareChatMessageService> messageService;

    private ReceiptReceivedListener receiptReceivedListener;

    private QuickbloxGroupXmppConnection(BizareChatMessageService messageService) {
        this.messageService = new WeakReference<>(messageService);
    }

    public static QuickbloxGroupXmppConnection getInstance(BizareChatMessageService messageService) {
        if (INSTANCE == null) {
            synchronized (QuickbloxGroupXmppConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new QuickbloxGroupXmppConnection(messageService);
                }
            }
        }
        return INSTANCE;
    }

    public void connect() throws IOException, XMPPException, SmackException {
        ProviderManager.addExtensionProvider(ReadReceipt.ELEMENT, ReadReceipt.NAMESPACE, new ReadReceipt.Provider());
        receiptReceivedListener = (fromJid, toJid, receiptId, receipt) -> {
            // TODO Handle read event
        };
        ReadReceiptManager.getInstanceFor(groupChatConnection).addReadReceivedListener(receiptReceivedListener);
        initGroupConnection();
        groupChatConnection.connect();
        groupChatConnection.login();
    }

    public void disconnect() {
        ReadReceiptManager.getInstanceFor(groupChatConnection).removeRemoveReceivedListener(receiptReceivedListener);
        if (groupChatConnection != null) {
            groupChatConnection.disconnect();
        }
        groupChatConnection = null;
    }

    public void sendMessage(String body, String chatJid, long timestamp) throws SmackException {
        Random random = new Random(timestamp + body.length() + chatJid.length());
        Log.d(TAG, "Sending message to : " + chatJid);
        MultiUserChat chat = MultiUserChatManager.getInstanceFor(groupChatConnection)
                .getMultiUserChat(chatJid);
        chat.addMessageListener(this);

        Message message = new Message();
        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");
        message.setStanzaId(StanzaIdUtil.newStanzaId());
        message.setBody(body);
        message.addExtension(extension);
        message.setType(Message.Type.chat);

        chat.sendMessage(message);
    }

    @Override
    public void processMessage(Message message) {
        messageService.get().processPublicMessage(message);
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

    private void initGroupConnection() {
        long currentUserId = CurrentUser.getInstance().getCurrentUserId();
        String currentUserPassword = CurrentUser.getInstance().getCurrentPassword();
        String jid = currentUserId + "-" + ApiConstants.APP_ID;
        groupChatConnection = new XMPPTCPConnection(
                jid, currentUserPassword, ApiConstants.MULTI_USERS_CHAT_ENDPOINT);
        groupChatConnection.addConnectionListener(this);
    }
}