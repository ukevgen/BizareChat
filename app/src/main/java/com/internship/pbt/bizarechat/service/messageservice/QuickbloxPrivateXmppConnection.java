package com.internship.pbt.bizarechat.service.messageservice;


import android.util.Log;

import com.internship.pbt.bizarechat.data.executor.JobExecutor;
import com.internship.pbt.bizarechat.data.net.ApiConstants;
import com.internship.pbt.bizarechat.logs.Logger;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Displayed;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.DisplayedManager;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Markable;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.Received;
import com.internship.pbt.bizarechat.service.messageservice.extentions.markable.ReceivedManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
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
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class QuickbloxPrivateXmppConnection
        implements ConnectionListener, ChatMessageListener, ChatManagerListener, MessageListener {
    private static final String TAG = QuickbloxPrivateXmppConnection.class.getSimpleName();
    private static volatile QuickbloxPrivateXmppConnection INSTANCE;
    private volatile boolean connected = false;
    private XMPPTCPConnection privateChatConnection;
    private WeakReference<BizareChatMessageService> messageService;
    private ConcurrentLinkedQueue<Message> offlineMessages;
    private Map<String, Chat> privateChats;
    private Map<String, MultiUserChat> publicChats;
    private String publicChatToLeave = "";

    private QuickbloxPrivateXmppConnection(BizareChatMessageService messageService) {
        this.messageService = new WeakReference<>(messageService);
        offlineMessages = new ConcurrentLinkedQueue<>();
        privateChats = new ConcurrentHashMap<>();
        publicChats = new ConcurrentHashMap<>();
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
        if ((chat = privateChats.get(receiverJid)) == null) {
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
            Logger.logExceptionToFabric(ex);
            offlineMessages.add(message);
        }
    }

    public void sendReceivedReceipt(String receiverJid, String stanzaId, String dialog_id) {
        Chat chat;
        if ((chat = privateChats.get(receiverJid)) == null) {
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
        if ((chat = privateChats.get(receiverJid)) == null) {
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

        MultiUserChat mucChat = publicChats.get(chatJid);

        QuickbloxChatExtension extension = new QuickbloxChatExtension();
        extension.setProperty("date_sent", timestamp + "");
        extension.setProperty("save_to_history", "1");

        Message message = new Message(chatJid);
        message.setStanzaId(stanzaId);
        message.setBody(body);
        message.setType(Message.Type.groupchat);
        message.addExtension(extension);

        try {
            if (mucChat != null) {
                mucChat.sendMessage(message);
            }
        } catch (SmackException.NotConnectedException ex) {
            offlineMessages.add(message);
        }
    }

    public void joinPublicChat(String chatJid, long lastMessageDate) {
        try {
            MultiUserChat mucChat;
            if ((mucChat = publicChats.get(chatJid)) == null) {
                mucChat = MultiUserChatManager.getInstanceFor(privateChatConnection).getMultiUserChat(chatJid);
                mucChat.addMessageListener(this);
                publicChats.put(chatJid, mucChat);
            }

            DiscussionHistory history = new DiscussionHistory();
            if(lastMessageDate != 0)
                history.setSince(new Date(lastMessageDate * 1000));
            else
                history.setMaxChars(0);
            mucChat.join(
                    CurrentUser.getInstance().getCurrentUserId().toString(),
                    null,
                    history,
                    privateChatConnection.getPacketReplyTimeout());
        } catch (Exception ex) {
            Logger.logExceptionToFabric(ex);
        }
    }

    public void leavePublicChat(String chatJid) {
        MultiUserChat muc = publicChats.get(chatJid);
        try {
            if (publicChats.get(chatJid) != null) {
                muc.leave();
            }

        } catch (SmackException.NotConnectedException ex){
            Logger.logExceptionToFabric(ex);
            publicChatToLeave = chatJid;
        }
    }

    @Override
    public void processMessage(Message message) {
        processMessage(null, message);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        if (message.getBody() == null) {
            return;
        }

        if (message.getType() == Message.Type.groupchat) {
            messageService.get().processPublicMessage(message);
            return;
        }

        messageService.get().processPrivateMessage(message);
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        if (!privateChats.containsKey(chat.getParticipant())) {
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
        if (privateChatConnection != null) {
            privateChatConnection.disconnect();
        }
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
                        if (!publicChatToLeave.isEmpty()) {
                            publicChats.get(publicChatToLeave).leave();
                            publicChatToLeave = "";
                        }
                        Message message = offlineMessages.peek();
                        privateChatConnection.sendStanza(message);
                    } catch (SmackException ex) {
                        Logger.logExceptionToFabric(ex);
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