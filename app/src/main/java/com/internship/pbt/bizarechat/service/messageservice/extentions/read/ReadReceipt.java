package com.internship.pbt.bizarechat.service.messageservice.extentions.read;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;

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
    public String toXML() {
        return "<read xmlns='" + NAMESPACE + "' id='" + id + "'/>";
    }

    public static class Provider extends EmbeddedExtensionProvider<ReadReceipt> {
        @Override
        protected ReadReceipt createReturnExtension(String currentElement, String currentNamespace, Map<String, String> attributeMap, List<? extends ExtensionElement> content) {
            return new ReadReceipt(attributeMap.get("id"));
        }
    }
}
