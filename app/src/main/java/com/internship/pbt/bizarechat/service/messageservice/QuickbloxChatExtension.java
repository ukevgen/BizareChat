package com.internship.pbt.bizarechat.service.messageservice;

import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.util.XmlStringBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class QuickbloxChatExtension implements ExtensionElement {
    private Map<String, String> properties;

    public QuickbloxChatExtension(){
        properties = new HashMap<>();
    }

    @Override
    public String getNamespace() {
        return "jabber:client";
    }

    @Override
    public String getElementName() {
        return "extraParams";
    }

    @Override
    public CharSequence toXML() {
        XmlStringBuilder builder = new XmlStringBuilder();
        builder.halfOpenElement("extraParams").xmlnsAttribute("jabber:client").rightAngleBracket();
        Iterator<String> iterator = properties.keySet().iterator();

        String key;
        while(iterator.hasNext()){
            key = iterator.next();
            builder.element(key, this.getProperty(key));
        }

        builder.closeElement("extraParams");
        return builder;
    }

    public void setProperty(String name, String value) {
        properties.put(name, value);
    }

    public void setProperties(Map<String, String> properties) {
        this.properties.putAll(properties);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getProperty(String key){
        return properties.get(key);
    }
}
