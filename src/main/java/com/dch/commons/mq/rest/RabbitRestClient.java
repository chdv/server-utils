package com.dch.commons.mq.rest;

import com.dch.commons.beans.ConfigBean;
import com.dch.commons.beans.IConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dch.commons.CommonModule;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.json.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by dcherdyntsev on 24.08.2015.
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class RabbitRestClient {

    public static final String GLOBAL_NAME = CommonModule.BEAN_PREFIX + "RabbitRestClient";

    private static final Logger logger = LoggerFactory.getLogger(RabbitRestClient.class);

    private static String QUEUES_URI = "/api/queues/";

    private String url;

    private Client client;

    @EJB(beanInterface = IConfigBean.class)
    private IConfigBean configBean;

    @PostConstruct
    @Lock(LockType.WRITE)
    private void init() {
        url = configBean.getProperty(ConfigBean.RABBIT_REST);
        if(url.endsWith("/"))
            this.url = url.substring(0, url.length() - 1);
        client = ClientBuilder.newClient().register(
                new Authenticator(
                    configBean.getProperty(ConfigBean.RABBIT_LOGIN),
                    configBean.getProperty(ConfigBean.RABBIT_PASSWD)
                ));
    }

    @Lock(LockType.WRITE)
    public RabbitQueues getQueues() {
        JsonArray objectResponse = (JsonArray)createRequest(url + QUEUES_URI);

        RabbitQueues result = new RabbitQueues();

        for(JsonValue obj : objectResponse) {
            JsonObject object = (JsonObject)obj;
            String name = object.getJsonString("name").getString();
            int count = 0;
            if(object.getJsonNumber("messages_ready") != null) {
                count = object.getJsonNumber("messages_ready").intValue();
            }
            RabbitQueue queue =
                    new RabbitQueue(
                            name,
                            count);
            result.getQueues().add(queue);
        }
        return result;
    }

    private JsonStructure createRequest(String reqUrl) {
        Response response = client.target(reqUrl).request().get();

        JsonReader jsonReader = Json.createReader(new StringReader(response.readEntity(String.class)));
        JsonStructure jsonStructure = jsonReader.read();
        jsonReader.close();

        return jsonStructure;
    }

    private class Authenticator implements ClientRequestFilter {

        private final String user;
        private final String password;

        public Authenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public void filter(ClientRequestContext requestContext) throws IOException {
            MultivaluedMap<String, Object> headers = requestContext.getHeaders();
            final String basicAuthentication = getBasicAuthentication();
            headers.add("Authorization", basicAuthentication);
        }

        private String getBasicAuthentication() {
            String token = this.user + ":" + this.password;
            return "Basic " + DatatypeConverter.printBase64Binary(token.getBytes());
        }
    }
}
