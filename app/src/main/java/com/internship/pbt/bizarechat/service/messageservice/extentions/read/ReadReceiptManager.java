package com.internship.pbt.bizarechat.service.messageservice.extentions.read;

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

public class ReadReceiptManager extends Manager {
    private static final StanzaFilter MESSAGES_WITH_READ_RECEIPT = new AndFilter(StanzaTypeFilter.MESSAGE,
            new StanzaExtensionFilter(ReadReceipt.ELEMENT, ReadReceipt.NAMESPACE));

    private static Map<XMPPConnection, ReadReceiptManager> instances = new WeakHashMap<>();
    private Set<ReceiptReceivedListener> receiptReceivedListeners = new CopyOnWriteArraySet<>();

    private ReadReceiptManager(XMPPConnection connection) {
        super(connection);
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        sdm.addFeature(ReadReceipt.NAMESPACE);
        connection.addAsyncStanzaListener(packet -> {
            ReadReceipt receipt = ReadReceipt.from((Message)packet);
            for(ReceiptReceivedListener listener : receiptReceivedListeners){
                listener.onReceiptReceived(packet.getFrom(), packet.getTo(), receipt.getId(), packet);
            }
        }, MESSAGES_WITH_READ_RECEIPT);
    }

    public static synchronized ReadReceiptManager getInstanceFor(XMPPConnection connection) {
        ReadReceiptManager receiptManager = instances.get(connection);

        if (receiptManager == null) {
            receiptManager = new ReadReceiptManager(connection);
            instances.put(connection, receiptManager);
        }

        return receiptManager;
    }

    public void addReadReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.add(listener);
    }

    public void removeRemoveReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.remove(listener);
    }

}