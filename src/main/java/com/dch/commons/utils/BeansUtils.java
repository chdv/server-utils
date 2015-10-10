package com.dch.commons.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dcherdyntsev on 29.07.2015.
 */
public class BeansUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeansUtils.class);

    public static String getProperty(Object o, String beanProperty, String notFoundMessage) {
        if(o == null)
            return notFoundMessage;
        Object result = null;
        try {
            result = PropertyUtils.getProperty(o, beanProperty);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return notFoundMessage;
        }
        if(result == null)
            return notFoundMessage;
        return result.toString();
    }

    public static String getProperty(Object o, String beanProperty) {
        if(o == null)
            return null;
        Object result = null;
        try {
            result = PropertyUtils.getProperty(o, beanProperty);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        if(result == null)
            return null;
        return result.toString();
    }

}
