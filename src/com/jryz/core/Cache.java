package com.jryz.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	public volatile static   Map<String, String>              SYSCONFIG_DATA          = new ConcurrentHashMap<>();
    public volatile static   Map<String, List<String>>        SYSCONFIG_DATA_LIST     = new ConcurrentHashMap<>();
    public static final      String                           LOGIN_KEY               = "LOGIN_KEY";
    public static final      Boolean                          LOGIN_CHECK             = true;
    public static final      int                              PROJECT_ID              = 1;
    public static final      String                           PROJECT_REDIS_KEY       = "PROJECT_REDIS_KEY";
    public volatile static   Map<String, Boolean>             FILESTATE_MAP           = new ConcurrentHashMap<>();


    public static void clean(){
        SYSCONFIG_DATA.clear();
        SYSCONFIG_DATA_LIST.clear();
    }
}
