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
//        RabbitRestClient client = new RabbitRestClient("http://127.0.0.1:15672", "emp", "emp");
        logger.debug("{}", client.getQueues().findByName("Pxl.DataHub.NewEntity").getMessagesCount());
        Thread.sleep(100);
    }

}
