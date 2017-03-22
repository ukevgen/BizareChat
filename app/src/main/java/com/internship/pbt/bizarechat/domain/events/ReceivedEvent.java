package com.internship.pbt.bizarechat.domain.events;


import com.internship.pbt.bizarechat.data.datamodel.MessageModel;

import java.util.List;

public class ReceivedEvent {
    private List<MessageModel> messages;

    public ReceivedEvent(List<MessageModel> messages) {
        this.messages = messages;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

}
