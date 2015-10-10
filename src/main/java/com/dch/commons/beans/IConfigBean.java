package com.dch.commons.beans;

import com.dch.commons.jpa.entities.Property;

import java.util.List;

/**
 * Created by dcherdyntsev on 27.07.2015.
 */
public interface IConfigBean {

    String getProperty(String property);

    Integer getIntProperty(String property);

    Long getLongProperty(String property);

    Boolean getBooleanProperty(String property);

    void updateProperties(List<Property> properties);

}
