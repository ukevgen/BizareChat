package com.internship.pbt.bizarechat.domain.events;



public class GcmMessageReceivedEvent {
    private String message;

    public GcmMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
