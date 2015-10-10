package com.dch.commons.jpa;

import com.dch.commons.CommonModule;
import com.dch.commons.jpa.entities.Property;

import javax.ejb.*;
import java.util.List;

/**
 * Created by dcherdyntsev on 05.08.2015.
 */
@Stateless
public class PropertiesDAO extends AbstractConfigDAO<Property, Integer> {

    public static final String GLOBAL_NAME = CommonModule.BEAN_PREFIX + "PropertiesDAO";

    @Override
    protected Class<Property> entityClass() {
        return Property.class;
    }

    public Property findByKey(String key) {
        List<Property> list = getEntityManager().
                createNamedQuery("findPropertyByKey", Property.class).
                setParameter("key", key).
                getResultList();
        if(list.size() > 0)
            return list.get(0);
        else
            return null;
    }

}
