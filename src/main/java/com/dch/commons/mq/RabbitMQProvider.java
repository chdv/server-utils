package com.dch.commons.mq;

import com.dch.commons.beans.ConfigBean;
import com.dch.commons.beans.IConfigBean;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dch.commons.CommonModule;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.io.IOException;

/**
 * Created by dcherdyntsev on 05.08.2015.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class RabbitMQProvider {

    public static final String GLOBAL_NAME = CommonModule.BEAN_PREFIX + "RabbitMQProvider";

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProvider.class);

    protected volatile Connection rabbitConnection;

    @EJB(beanInterface = IConfigBean.class)
    private IConfigBean configBean;

    @PostConstruct
    protected void init() throws Exception {
        createConnection();
    }

    @Lock(LockType.WRITE)
    protected void createConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(getMqConnectionUri());
        rabbitConnection = factory.newConnection();
    }

    protected String getMqConnectionUri() {
        return configBean.getProperty(ConfigBean.RABBIT_URI);
    }

    @Lock(LockType.WRITE)
    public Channel createChannel() throws Exception {
        if(rabbitConnection!=null && rabbitConnection.isOpen()) {
            return rabbitConnection.createChannel();
        } else {
            createConnection();
            return createChannel();
        }
    }

    @PreDestroy
    protected void destroy() throws IOException {
        rabbitConnection.close();
    }
}
