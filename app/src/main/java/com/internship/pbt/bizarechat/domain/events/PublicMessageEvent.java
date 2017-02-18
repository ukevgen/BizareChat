package com.internship.pbt.bizarechat.domain.events;


import org.jivesoftware.smack.packet.Message;

public class PublicMessageEvent {
    private Message message;

    public PublicMessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
