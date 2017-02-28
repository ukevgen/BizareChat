package com.internship.pbt.bizarechat.domain.model.chatroom;




public final class MessageState {
    public static final int SENT = 100;
    public static final int DELIVERED = 200;
    public static final int READ = 300;
    public static final int NOT_SENT = 400;

    private MessageState(){}
}
