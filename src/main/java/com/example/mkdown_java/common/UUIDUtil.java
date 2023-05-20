package com.example.mkdown_java.common;
import java.util.UUID;

public class UUIDUtil {

    public static String getUUID(){
        String id = UUID.randomUUID().toString();
        String uid = id.replaceAll("-", "");

        return uid;
    }
}


