package com.internship.pbt.bizarechat.domain.events;


import org.jivesoftware.smack.packet.Message;

public class PrivateMessageEvent {
    private Message message;

    public PrivateMessageEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
