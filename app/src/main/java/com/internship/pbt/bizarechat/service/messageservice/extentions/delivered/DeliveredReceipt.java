package com.internship.pbt.bizarechat.service.messageservice.extentions.delivered;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

public class DeliveredReceipt implements ExtensionElement {

    public static final String NAMESPACE = "urn:xmpp:delivered";
    public static final String ELEMENT = "delivered";

    private String id;

    public DeliveredReceipt(String id) {
        this.id = id;
    }

    public static DeliveredReceipt from(Message message) {
        return message.getExtension(ELEMENT, NAMESPACE);
    }

    public String getId() {
        return id;
    }

    @Override
    public String getElementName() {
        return ELEMENT;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public XmlStringBuilder toXML() {
        XmlStringBuilder builder = new XmlStringBuilder(this);
        builder.attribute("id", id);
        builder.closeEmptyElement();
        return builder;
    }

    public static class Provider extends EmbeddedExtensionProvider<DeliveredReceipt> {
        @Override
        protected DeliveredReceipt createReturnExtension(String currentElement,
                                                         String currentNamespace,
                                                         Map<String, String> attributeMap,
                                                         List<? extends ExtensionElement> content) {
            return new DeliveredReceipt(attributeMap.get("id"));
        }
    }
}
