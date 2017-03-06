package com.internship.pbt.bizarechat.domain.events;


import com.internship.pbt.bizarechat.data.datamodel.MessageModel;

public class PublicMessageEvent {
    private MessageModel message;

    public PublicMessageEvent(MessageModel message) {
        this.message = message;
    }

    public MessageModel getMessage() {
        return message;
    }
}
