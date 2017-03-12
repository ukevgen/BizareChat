package com.internship.pbt.bizarechat.service.messageservice.extentions.markable;


import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.List;
import java.util.Map;

public class Markable implements ExtensionElement {
    public static final String NAMESPACE = "urn:xmpp:chat-markers:0";
    public static final String ELEMENT = "markable";

    public static Markable from(Message message) {
        return message.getExtension(ELEMENT, NAMESPACE);
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
        builder.closeEmptyElement();
        return builder;
    }

    public static class Provider extends EmbeddedExtensionProvider<Markable> {
        @Override
        protected Markable createReturnExtension(String currentElement,
                                                 String currentNamespace,
                                                 Map<String, String> attributeMap,
                                                 List<? extends ExtensionElement> content) {
            return new Markable();
        }
    }
}
