package com.dch.commons.mq.rest;

/**
 * Created by dcherdyntsev on 24.08.2015.
 */
public class RabbitQueue {

    private String name;
    private int messagesCount;

    public RabbitQueue(String name, int messagesCount) {
        this.name = name;
        this.messagesCount = messagesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(int messagesCount) {
        this.messagesCount = messagesCount;
    }
}
