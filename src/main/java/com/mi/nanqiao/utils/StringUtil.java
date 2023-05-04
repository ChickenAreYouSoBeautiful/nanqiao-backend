package com.mi.nanqiao.utils;

import java.util.UUID;

public class StringUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
