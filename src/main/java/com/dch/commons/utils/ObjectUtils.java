package com.dch.commons.utils;

/**
 * Created by dcherdyntsev on 22.08.2015.
 */
public class ObjectUtils {

    public static boolean equals(Object o1, Object o2) {
        if(o1 == o2)
            return true;

        if(o1 ==null || o2 == null)
            return false;

        return o1.equals(o2);
    }

}
