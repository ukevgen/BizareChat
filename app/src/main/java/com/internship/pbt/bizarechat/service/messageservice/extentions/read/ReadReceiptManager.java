package com.internship.pbt.bizarechat.service.messageservice.extentions.read;

<<<<<<< HEAD
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.StanzaExtensionFilter;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.receipts.ReceiptReceivedListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class ReadReceiptManager implements StanzaListener {


    private static Map<XMPPConnection, ReadReceiptManager> instances = Collections.synchronizedMap(new WeakHashMap<XMPPConnection, ReadReceiptManager>());

    private Set<ReceiptReceivedListener> receiptReceivedListeners = Collections.synchronizedSet(new HashSet<ReceiptReceivedListener>());

    private ReadReceiptManager(XMPPConnection connection) {
        ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        sdm.addFeature(ReadReceipt.NAMESPACE);
        instances.put(connection, this);

        connection.addAsyncStanzaListener(this, new StanzaExtensionFilter(ReadReceipt.NAMESPACE));
=======
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
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
    }

    public static synchronized ReadReceiptManager getInstanceFor(XMPPConnection connection) {
        ReadReceiptManager receiptManager = instances.get(connection);

        if (receiptManager == null) {
            receiptManager = new ReadReceiptManager(connection);
<<<<<<< HEAD
=======
            instances.put(connection, receiptManager);
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
        }

        return receiptManager;
    }

    public void addReadReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.add(listener);
    }

    public void removeRemoveReceivedListener(ReceiptReceivedListener listener) {
        receiptReceivedListeners.remove(listener);
    }

<<<<<<< HEAD
    @Override
    public void processPacket(Stanza packet) throws SmackException.NotConnectedException {

    }
=======
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
}
