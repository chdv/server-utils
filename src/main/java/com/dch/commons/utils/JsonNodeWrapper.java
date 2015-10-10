package com.dch.commons.utils;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by dcherdyntsev on 14.08.2015.
 */
public class JsonNodeWrapper {

    private JsonNode node;

    public JsonNodeWrapper(JsonNode node) {
        this.node = node;
    }

    public JsonNodeWrapper n(String name) {
        return new JsonNodeWrapper(node.get(name));
    }

    public String t(String name) {
        return node.get(name).asText();
    }

    public JsonNode node() {
        return node;
    }

}
