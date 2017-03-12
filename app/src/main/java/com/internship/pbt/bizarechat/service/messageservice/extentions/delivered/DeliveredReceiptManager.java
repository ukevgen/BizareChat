package com.internship.pbt.bizarechat.service.messageservice.extentions.delivered;


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

public class DeliveredReceiptManager extends Manager {
    private static final StanzaFilter MESSAGES_WITH_DELIVERED_RECEIPT = new AndFilter(StanzaTypeFilter.MESSAGE,
            new StanzaExtensionFilter(DeliveredReceipt.ELEMENT, DeliveredReceipt.NAMESPACE));

    private static Map<XMPPConnection, DeliveredReceiptManager> instances = new WeakHashMap<>();
    private Set<ReceiptReceivedListener> receiptReceivedListeners = new CopyOnWriteArraySet<>();

    private DeliveredReceiptManager(XMPPConnection connection) {
        super(connection);
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        sdm.addFeature(DeliveredReceipt.NAMESPACE);
        connection.addAsyncStanzaListener(packet -> {
            DeliveredReceipt receipt = DeliveredReceipt.from((Message) packet);
            for (ReceiptReceivedListener listener : receiptReceivedListeners) {
                listener.onReceiptReceived(packet.getFrom(), packet.getTo(), receipt.getId(), packet);
            }
        }, MESSAGES_WITH_DELIVERED_RECEIPT);
    }

    public static synchronized DeliveredReceiptManager getInstanceFor(XMPPConnection connection) {
        DeliveredReceiptManager receiptManager = instances.get(connection);

        if (receiptManager == null) {
            receiptManager = new DeliveredReceiptManager(connection);
            instances.put(connection, receiptManager);
        }

        return receiptManager;
    }

    public void addDeliveredReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.add(listener);
    }

    public void removeDeliveredReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.remove(listener);
    }
}
