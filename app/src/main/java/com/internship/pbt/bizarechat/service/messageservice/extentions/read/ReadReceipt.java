package com.internship.pbt.bizarechat.service.messageservice.extentions.read;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

public class ReadReceipt implements ExtensionElement {

    public static final String NAMESPACE = "urn:xmpp:read";
    public static final String ELEMENT = "read";

    private String id;  // original ID of the delivered message

    public ReadReceipt(String id) {
        this.id = id;
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

    public static ReadReceipt from(Message message) {
        return message.getExtension(ELEMENT, NAMESPACE);
    }

    public static class Provider extends EmbeddedExtensionProvider<ReadReceipt> {
        @Override
        protected ReadReceipt createReturnExtension(String currentElement,
                                                    String currentNamespace,
                                                    Map<String, String> attributeMap,
                                                    List<? extends ExtensionElement> content) {
            return new ReadReceipt(attributeMap.get("id"));
        }
    }
}