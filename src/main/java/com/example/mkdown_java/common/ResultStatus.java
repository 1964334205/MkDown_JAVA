package com.example.mkdown_java.common;

import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常信息的描述
 */
@ToString
@Getter
public enum ResultStatus implements IResultStatus {


    // 0  操作成功
    SUCCESS(0,"操作成功"),

    // 10xx  用户业务逻辑错误

    USER_NOT_LOGIN(1000,"用户未登录"),
    USER_NAME_ILLEGAL (1001,"用户名非法"),
    USER_ID_NOT_EXIST(1002,"用户ID不存在"),
    USER_PASSWORD_LENGTH_ILLEGAL(1003,"密码长度非法"),
    USER_NAME_PRESENCE (1004,"用户名已存在"),
    USER_LOGGEN_IN(1005,"用户已登录"),
    USER_NO_PERMISSION(1006,"用户无权限查看此笔记"),


    // 11xx 笔记业务逻辑错误

    NOTE_SAVE_FAIL(1101,"笔记保存失败"),
    NOTE_FAIL(1102,"笔记不存在"),


    // 10000 内网服务错误
    INTERNAL_ELASTIC_CONNECT_FAIL(10000,"ES连接错误"),

    INTERNAL_MYSQ_CONNECT_FAIL(10001,"MYSQL连接错误"),


    INTERNAL_REDIS_CONNECT_FAIL(10002,"REDIS连接错误"),
    INTERNAL_SERVICE_CONNECT_FAIL(10003,"系统内部服务连接错误"),

    //  101xx 公网服务错误
    EXTERNAL_QINIUYUN_CONNECT_FAIL(10100,"七牛云连接错误"),

    // 4xx 网络接口错误


    // 5XX 系统异常状态
    SYSTEM_MAINTENANCE(80000,"系统维护中，暂停服务"),
    INTERNAL_SERVER_ERROR(80001, "内部服务器错误"),


    UNKNOWN(99999,"未知错误");

//    SUCCESS(200, "OK"),
//    BAD_REQUEST(400, "Bad Request"),
//    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    /** 业务异常码 */
    private Integer code;
    /** 业务异常信息描述 */
    private String message;

    ResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}