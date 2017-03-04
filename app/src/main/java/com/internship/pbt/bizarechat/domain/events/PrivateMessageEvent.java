package com.internship.pbt.bizarechat.domain.events;


import com.internship.pbt.bizarechat.data.datamodel.MessageModel;

public class PrivateMessageEvent {
    private MessageModel message;

    public PrivateMessageEvent(MessageModel message) {
        this.message = message;
    }

    public MessageModel getMessage() {
        return message;
    }
}
