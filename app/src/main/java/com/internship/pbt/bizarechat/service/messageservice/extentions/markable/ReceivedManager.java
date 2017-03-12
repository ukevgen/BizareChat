package com.internship.pbt.bizarechat.service.messageservice.extentions.markable;

import org.jivesoftware.smack.Manager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaExtensionFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class ReceivedManager extends Manager {
    private static final StanzaFilter MESSAGES_WITH_RECEIVED = new AndFilter(StanzaTypeFilter.MESSAGE,
            new StanzaExtensionFilter(Received.ELEMENT, Received.NAMESPACE));

    private static Map<XMPPConnection, ReceivedManager> instances = new WeakHashMap<>();
    private Set<ReceiptReceivedListener> receiptReceivedListeners = new CopyOnWriteArraySet<>();

    private ReceivedManager(XMPPConnection connection) {
        super(connection);
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        sdm.addFeature(Received.NAMESPACE);
        connection.addAsyncStanzaListener(packet -> {
            Received receipt = Received.from((Message) packet);
            for (ReceiptReceivedListener listener : receiptReceivedListeners) {
                listener.onReceiptReceived(packet.getFrom(), packet.getTo(), receipt.getId(), packet);
            }
        }, MESSAGES_WITH_RECEIVED);
    }

    public static synchronized ReceivedManager getInstanceFor(XMPPConnection connection) {
        ReceivedManager receivedManager = instances.get(connection);

        if (receivedManager == null) {
            receivedManager = new ReceivedManager(connection);
            instances.put(connection, receivedManager);
        }

        return receivedManager;
    }

    public void addReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.add(listener);
    }

    public void removeReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.remove(listener);
    }
}
