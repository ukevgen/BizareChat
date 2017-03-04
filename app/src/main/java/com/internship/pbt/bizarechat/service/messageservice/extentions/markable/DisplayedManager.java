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

public class DisplayedManager extends Manager {
    private static final StanzaFilter MESSAGES_WITH_DISPLAYED = new AndFilter(StanzaTypeFilter.MESSAGE,
            new StanzaExtensionFilter(Displayed.ELEMENT, Displayed.NAMESPACE));

    private static Map<XMPPConnection, DisplayedManager> instances = new WeakHashMap<>();
    private Set<ReceiptReceivedListener> receiptReceivedListeners = new CopyOnWriteArraySet<>();

    private DisplayedManager(XMPPConnection connection) {
        super(connection);
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        sdm.addFeature(Displayed.NAMESPACE);
        connection.addAsyncStanzaListener(packet -> {
            Displayed receipt = Displayed.from((Message)packet);
            for(ReceiptReceivedListener listener : receiptReceivedListeners){
                listener.onReceiptReceived(packet.getFrom(), packet.getTo(), receipt.getId(), packet);
            }
        }, MESSAGES_WITH_DISPLAYED);
    }

    public static synchronized DisplayedManager getInstanceFor(XMPPConnection connection) {
        DisplayedManager displayedManager = instances.get(connection);

        if (displayedManager == null) {
            displayedManager = new DisplayedManager(connection);
            instances.put(connection, displayedManager);
        }

        return displayedManager;
    }

    public void addDisplayedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.add(listener);
    }

    public void removeDisplayedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.remove(listener);
    }
}

