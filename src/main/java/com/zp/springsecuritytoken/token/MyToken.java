package com.zp.springsecuritytoken.token;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zp
 * @create 2020/7/9 15:23
 */
public class MyToken {
    public static ConcurrentHashMap tokenMap = new ConcurrentHashMap();
}
