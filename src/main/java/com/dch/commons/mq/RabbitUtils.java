package com.dch.commons.mq;

import com.rabbitmq.client.AMQP;

import javax.jms.DeliveryMode;

/**
 * Created by dcherdyntsev on 17.08.2015.
 */
public class RabbitUtils {

    public static AMQP.BasicProperties persistenceDeliveryMode() {
        return new AMQP.BasicProperties().builder().deliveryMode(DeliveryMode.PERSISTENT).build();
    }

}
