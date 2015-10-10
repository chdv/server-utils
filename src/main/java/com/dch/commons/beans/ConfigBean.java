package com.dch.commons.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dch.commons.CommonModule;
import com.dch.commons.jpa.PropertiesDAO;
import com.dch.commons.jpa.entities.Property;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.*;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class ConfigBean implements IConfigBean {

    public static final String GLOBAL_NAME = CommonModule.BEAN_PREFIX + "ConfigBean";

    private static final Logger logger = LoggerFactory.getLogger(ConfigBean.class);

    private final Map<String, String> properties = new HashMap<>();

    private String APP_NAME = CommonModule.MODULE_NAME;

    public static final String MONGO_URI = "ejb.db.mongodb.uri";
    public static final String RABBIT_URI = "ejb.mq.rabbit.uri";
    public static final String RABBIT_REST = "ejb.mq.rabbit.rest-uri";
    public static final String RABBIT_LOGIN = "ejb.mq.rabbit.login";
    public static final String RABBIT_PASSWD = "ejb.mq.rabbit.passwd";
    public static final String MONGO_STAGE = "ejb.db.mongodb.stage.uri";

    @EJB(beanInterface = PropertiesDAO.class)
    private PropertiesDAO propertiesDAO;

    private void initDb() {
        List<Property> props = new ArrayList<>();

        props.add(new Property(10,
                MONGO_URI,
                "mongodb://10.250.21.10:27017",
                "Строка коннекта к Mongo",
                APP_NAME));

        props.add(new Property(20,
                RABBIT_URI,
                "amqp://emp:emp@10.250.9.11:5672",
                "Строка коннекта к Rabbit MQ",
                APP_NAME));

        props.add(new Property(30,
                MONGO_STAGE,
                "mongodb://10.250.21.10:27017",
                "Строка коннекта к Stage Mongo",
                APP_NAME));

        props.add(new Property(40,
                RABBIT_REST,
                "http://127.0.0.1:15672",
                "Строка коннекта к Rabbit MQ Rest",
                APP_NAME));

        props.add(new Property(50,
                RABBIT_LOGIN,
                "emp",
                "Rabbit MQ rest логин",
                APP_NAME));

        props.add(new Property(60,
                RABBIT_PASSWD,
                "emp",
                "Rabbit MQ rest пароль",
                APP_NAME));

        updateProperties(props);
    }

    @Lock(LockType.READ)
    public String getProperty(String property) {
        if(properties.get(property) == null)
            throw new IllegalArgumentException("Property '" + property + "' not found!");
        return properties.get(property);
    }

    @Override
    @Lock(LockType.READ)
    public Integer getIntProperty(String property) {
        return Integer.parseInt(getProperty(property));
    }

    @Override
    @Lock(LockType.READ)
    public Long getLongProperty(String property) {
        return Long.parseLong(property);
    }

    @Override
    @Lock(LockType.READ)
    public Boolean getBooleanProperty(String property) {
        return Boolean.parseBoolean(property);
    }

    @PostConstruct
    @Lock(LockType.WRITE)
    private void init() {
        initDb();
    }

    @Lock(LockType.WRITE)
    public void updateProperties(List<Property> properties) {
        for(Property p : properties) {
            Property dbProperty = propertiesDAO.find(p.getId());
            if(dbProperty==null) {
                propertiesDAO.update(p);
            } else {
                if(!dbProperty.getKey().equals(p.getKey())) {
                    propertiesDAO.update(p);
                } else if(
                        !dbProperty.getDescription().equals(p.getDescription()) ||
                        !dbProperty.getModule().equals(p.getModule())
                        ) {
                    p.setValue(dbProperty.getValue());
                    propertiesDAO.update(p);
                }
            }
        }
        reloadProperties();
    }

    @Lock(LockType.WRITE)
    @Schedule(persistent = false, second = "*/30", minute = "*", hour = "*", info = "ConfigBean check run scheduler")
    private void reloadProperties() {
        properties.clear();
        for(Property property : propertiesDAO.findAll() ) {
            properties.put(property.getKey(), property.getValue());
        }
    }

}
