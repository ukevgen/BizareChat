package com.internship.pbt.bizarechat.domain.events;



public class PublicMessageSentEvent {
    private String messageId;

    public PublicMessageSentEvent(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
