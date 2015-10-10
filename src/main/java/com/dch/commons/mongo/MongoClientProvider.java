package com.dch.commons.mongo;

import com.dch.commons.CommonModule;
import com.dch.commons.beans.ConfigBean;
import com.dch.commons.beans.IConfigBean;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.HashMap;
import java.util.Map;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoClientProvider {

    public static final String GLOBAL_NAME = CommonModule.BEAN_PREFIX + "MongoClientProvider";

    private static final Logger logger = LoggerFactory.getLogger(MongoClientProvider.class);

    @EJB(beanInterface = IConfigBean.class)
    private IConfigBean configBean;

    protected MongoClient mongoClient = null;

    protected Map<String, MongoDbFactory> springDbFactory = new HashMap<>();

    @Lock(LockType.READ)
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @PostConstruct
    @Lock(LockType.WRITE)
    public void init() {
        mongoClient = new MongoClient(new MongoClientURI(getMongoConnectionUri()));
    }

    protected String getMongoConnectionUri() {
        return configBean.getProperty(ConfigBean.MONGO_URI);
    }

    @Lock(LockType.READ)
    public MongoDatabase getDatabase(String name) {
        return getMongoClient().getDatabase(name);
    }

    @Lock(LockType.READ)
    public MongoTemplate getMongoTemplate(String dataBase) {
        MongoDbFactory dbFactory = getSpringDbFactory(dataBase);
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        MappingMongoConverter mappingConverter =
                new MappingMongoConverter(
                        new DefaultDbRefResolver(dbFactory),
                        new MongoMappingContext());
        mappingConverter.setTypeMapper(typeMapper);

        return new MongoTemplate(dbFactory, mappingConverter);
    }

    protected MongoDbFactory getSpringDbFactory(String dbName) {
        if(springDbFactory.get(dbName) == null) {
            springDbFactory.put(dbName,
                    new SimpleMongoDbFactory(mongoClient, dbName));
        }
        return springDbFactory.get(dbName);
    }

    @PreDestroy
    protected void destroy() {
        mongoClient.close();
    }
}
