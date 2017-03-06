package com.internship.pbt.bizarechat.service.messageservice.extentions.read;

import org.jivesoftware.smack.packet.ExtensionElement;
<<<<<<< HEAD
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
=======
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;
import org.jivesoftware.smack.util.XmlStringBuilder;
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894

import java.util.List;
import java.util.Map;

public class ReadReceipt implements ExtensionElement {

<<<<<<< HEAD

=======
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
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
<<<<<<< HEAD
    public String toXML() {
        return "<read xmlns='" + NAMESPACE + "' id='" + id + "'/>";
=======
    public XmlStringBuilder toXML() {
        XmlStringBuilder builder = new XmlStringBuilder(this);
        builder.attribute("id", id);
        builder.closeEmptyElement();
        return builder;
    }

    public static ReadReceipt from(Message message) {
        return message.getExtension(ELEMENT, NAMESPACE);
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
    }

    public static class Provider extends EmbeddedExtensionProvider<ReadReceipt> {
        @Override
<<<<<<< HEAD
        protected ReadReceipt createReturnExtension(String currentElement, String currentNamespace, Map<String, String> attributeMap, List<? extends ExtensionElement> content) {
=======
        protected ReadReceipt createReturnExtension(String currentElement,
                                                    String currentNamespace,
                                                    Map<String, String> attributeMap,
                                                    List<? extends ExtensionElement> content) {
>>>>>>> bbb26234cf928191b04be84fdd658537db7f9894
            return new ReadReceipt(attributeMap.get("id"));
        }
    }
}
