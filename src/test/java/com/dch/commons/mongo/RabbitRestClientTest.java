package com.dch.commons.mongo;

import com.dch.commons.mq.rest.RabbitRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dcherdyntsev on 24.08.2015.
 */
public class RabbitRestClientTest {

    private static final Logger logger = LoggerFactory.getLogger(RabbitRestClientTest.class);

//    @Test
    public void test() throws InterruptedException {
        RabbitRestClient client = new RabbitRestClient();
        logger.debug("{}", client.getQueues().findByName("queue").getMessagesCount());
        Thread.sleep(100);
    }

}
