package com.internship.pbt.bizarechat.service.messageservice.extentions.markable;


import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

public class Received implements ExtensionElement {
    public static final String NAMESPACE = "urn:xmpp:chat-markers:0";
    public static final String ELEMENT = "received";

    private String id;

    public Received(String id) {
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

    public static Received from(Message message) {
        return message.getExtension(ELEMENT, NAMESPACE);
    }

    public static class Provider extends EmbeddedExtensionProvider<Received> {
        @Override
        protected Received createReturnExtension(String currentElement,
                                                 String currentNamespace,
                                                 Map<String, String> attributeMap,
                                                 List<? extends ExtensionElement> content) {
            return new Received(attributeMap.get("id"));
        }
    }
}
