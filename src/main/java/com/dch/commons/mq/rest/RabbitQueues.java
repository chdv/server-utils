package com.dch.commons.mq.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcherdyntsev on 24.08.2015.
 */
public class RabbitQueues {

    private List<RabbitQueue> queues = new ArrayList<>();

    public List<RabbitQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<RabbitQueue> queues) {
        this.queues = queues;
    }

    public RabbitQueue findByName(String name) {
        for(RabbitQueue q : queues) {
            if(q.getName().equals(name))
                return q;
        }

        throw new IllegalArgumentException("queue '" + name + "' not found");
    }

}
