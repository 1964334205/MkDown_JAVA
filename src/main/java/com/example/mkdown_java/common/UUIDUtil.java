package com.example.mkdown_java.common;
import java.util.UUID;

/**
 * UUID生成类
 */
public class UUIDUtil {
    /**
     * 生成UUID
     */
    public static Integer getUUID(){
        Integer uuid=UUID.randomUUID().toString().replaceAll("-","").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;//String.hashCode() 值会为空

        return uuid;
    }
}


