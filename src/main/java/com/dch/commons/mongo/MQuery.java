package com.dch.commons.mongo;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dcherdyntsev on 29.07.2015.
 */
public class MQuery {

    public static BasicDBObject and(BasicDBObject... objs) {
        return basic("$and", Arrays.asList(objs));
    }

    public static BasicDBObject and(List<BasicDBObject> objs) {
        return basic("$and", objs);
    }

    public static BasicDBObject gte(Object obj) {
        return basic("$gte", obj);
    }

    public static BasicDBObject gt(Object obj) {
        return basic("$gt", obj);
    }

    public static BasicDBObject eq(Object obj) {
        return basic("$eq", obj);
    }

    public static BasicDBObject eq(String field, Object obj) {
        return basic(field, eq(obj));
    }

    public static BasicDBObject lte(Object obj) {
        return basic("$lte", obj);
    }

    public static BasicDBObject basic(String name, Object value) {
        return new BasicDBObject(name, value);
    }

    public static BasicDBObject append(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("Params count error");
        }
        BasicDBObject result = new BasicDBObject();
        for (int i = 0; i < objects.length; i += 2) {
            result.append((String) objects[i], objects[i + 1]);
        }
        return result;
    }

    public static BasicDBObject regex(String field, Object value) {
        return basic(field, regex(value));
    }

    public static BasicDBObject regex(Object value) {
        return basic("$regex", value);
    }

    public static BasicDBObject geoWithin(Object obj) {
        return basic("$geoWithin", obj);
    }

    public static BasicDBObject geometry(Object obj) {
        return basic("$geometry", obj);
    }

    public static BasicDBObject polygon(List<List<Double>> polygon) {
        return append(
                "type", "Polygon",
                "coordinates", Arrays.asList(polygon)
        );
    }

    public static BasicDBObject elemMatch(String field, Object value) {
        return basic(field, elemMatch(value));
    }

    public static BasicDBObject elemMatch(Object value) {
        return basic("$elemMatch", value);
    }

    public static BasicDBObject between(Object start, Object end) {
        return append(
                "$gte", start,
                "$lt", end
        );
    }

    public static BasicDBObject in(Object obj) {
        return basic("$in", obj);
    }

    public static BasicDBObject in(String field, Object value) {
        return basic(field, in(value));
    }

    public static BasicDBObject between(String field, Object start, Object end) {
        return basic(field, between(start, end));
    }

    public static BasicDBObject exists(boolean exists) {
        return basic("$exists", exists);
    }

    public static BasicDBObject exists(String field, boolean exists) {
        return basic(field, exists(exists));
    }

    public static BasicDBObject match(Object obj) {
        return basic("$match", obj);
    }

    public static BasicDBObject group(Object obj) {
        return basic("$group", obj);
    }

    public static BasicDBObject sum(Object obj) {
        return basic("$sum", obj);
    }

    public static BasicDBObject sum(String field, Object obj) {
        return basic(field, sum(obj));
    }

    public static BasicDBObject gte(String field, Object value) {
        return basic(field, gte(value));
    }

    public static BasicDBObject gt(String field, Object value) {
        return basic(field, gt(value));
    }

    public static BasicDBObject lte(String field, Object value) {
        return basic(field, lte(value));
    }

    public static BasicDBObject geoWithin(String field, Object value) {
        return basic(field, geoWithin(value));
    }
}
