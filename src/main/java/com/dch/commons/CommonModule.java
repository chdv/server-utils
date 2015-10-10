package com.dch.commons;

public class CommonModule {

    public static final String MODULE_NAME = "pxl-commons-ejb";

    public static final String GLOBAL_STORAGE = "java:global";

    public static final String APP_STORAGE = "java:app";

    public static final String STORAGE = APP_STORAGE;

    public static final String BEAN_PREFIX = STORAGE + "/" + MODULE_NAME + "/";

    public static final String LOCAL_STORAGE = "java:module/";

    public static final String CONFIG_PERSISTENCE_UNIT = "ConfigPersistenceUnit";


}
