package com.internship.pbt.bizarechat.domain.events;


import com.internship.pbt.bizarechat.data.datamodel.MessageModel;

import java.util.List;

public class DisplayedEvent {
    private List<MessageModel> messages;

    public DisplayedEvent(List<MessageModel> messages) {
        this.messages = messages;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }
}
