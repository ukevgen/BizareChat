package com.internship.pbt.bizarechat.service.messageservice;


import android.util.Log;

import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.service.messageservice.extentions.read.ReadReceipt;
import com.internship.pbt.bizarechat.service.messageservice.extentions.read.ReadReceiptManager;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.receipts.DeliveryReceipt;
import org.jivesoftware.smackx.receipts.DeliveryReceiptManager;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Random;

public final class QuickbloxPrivateXmppConnection
        implements ConnectionListener, ChatMessageListener, ReceiptReceivedListener {
    private static final String TAG = QuickbloxPrivateXmppConnection.class.getSimpleName();
    private static volatile QuickbloxPrivateXmppConnection INSTANCE;

    private XMPPTCPConnection privateChatConnection;
    private DeliveryReceiptManager deliveryReceiptManager;
    private WeakReference<BizareChatMessageService> messageService;

    private ReceiptReceivedListener receiptReceivedListener;

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

    public void sendReadReceipt(String receiverJid, String stanzaId) {
        Message message = new Message(receiverJid);
        ReadReceipt read = new ReadReceipt(stanzaId);
        message.addExtension(read);
        try {
            privateChatConnection.sendStanza(message);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String body, String receiverJid, long timestamp) throws SmackException {
        Random random = new Random(timestamp + body.length() + receiverJid.length());
        Log.d(TAG, "Sending message to : " + receiverJid);
        Chat chat = ChatManager.getInstanceFor(privateChatConnection).createChat(receiverJid, this);

        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");

        Message message = new Message();
        message.setStanzaId(receiverJid + random.nextInt());
        message.setBody(body);
        message.addExtension(extension);
        message.addExtension(new DeliveryReceipt(StanzaIdUtil.newStanzaId()));

        chat.sendMessage(message);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        messageService.get().processPrivateMessage(message);
    }

    @Override
    public void onReceiptReceived(String fromJid, String toJid, String receiptId, Stanza receipt) {
        messageService.get().onReceiptReceived();
    }

    public void connect() throws IOException, XMPPException, SmackException {
        ProviderManager.addExtensionProvider(ReadReceipt.ELEMENT, ReadReceipt.NAMESPACE, new ReadReceipt.Provider());
        receiptReceivedListener = (fromJid, toJid, receiptId, receipt) -> {
            // TODO Handle read event
        };
        ReadReceiptManager.getInstanceFor(privateChatConnection).addReadReceivedListener(receiptReceivedListener);
        initPrivateConnection();
        privateChatConnection.connect();
        privateChatConnection.login();
    }

    public void disconnect() {
        ReadReceiptManager.getInstanceFor(privateChatConnection).removeRemoveReceivedListener(receiptReceivedListener);
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
        String jid = currentUserId + "-" + ApiConstants.APP_ID;
        privateChatConnection = new XMPPTCPConnection(
                jid, currentUserPassword, ApiConstants.CHAT_END_POINT);
        privateChatConnection.addConnectionListener(this);
        deliveryReceiptManager = DeliveryReceiptManager.getInstanceFor(privateChatConnection);
        deliveryReceiptManager.setAutoReceiptMode(DeliveryReceiptManager.AutoReceiptMode.always);
        deliveryReceiptManager.addReceiptReceivedListener(this);
    }
}