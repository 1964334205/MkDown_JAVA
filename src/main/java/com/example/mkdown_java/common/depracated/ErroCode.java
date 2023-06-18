package com.example.mkdown_java.common.depracated;

/**
 * 状态码类
 *已废弃
 *
 */
public enum ErroCode implements IErrorCode {

    // 0  操作成功
    SUCESS(0,"操作成功"),

    // 10xx  用户业务逻辑错误

    USER_NOT_LOGIN(1000,"用户未登录"),
    USER_NAME_ILLEGAL (1001,"用户名非法"),
    USER_ID_NOT_EXIST(1002,"用户ID不存在"),
    USER_PASSWORD_LENGTH_ILLEGAL(1003,"密码长度非法"),


    // 11xx 笔记业务逻辑错误

    NOTE_SAVE_FAIL(1101,"笔记保存失败"),


    // 10000 内网服务错误
    INTERNAL_ELASTIC_CONNECT_FAIL(10000,"ES连接错误"),
    INTERNAL_MYSQ_CONNECT_FAIL(10001,"MYSQL连接错误"),
    INTERNAL_REDIS_CONNECT_FAIL(10002,"REDIS连接错误"),

    //  101xx 公网服务错误
    EXTERNAL_QINIUYUN_CONNECT_FAIL(10100,"七牛云连接错误"),

    // 4xx 网络接口错误


    // 5XX 系统异常状态
    SYSTEM_MAINTENANCE(80000,"系统维护中，暂停服务"),



    UNKNOWN(99999,"未知错误");

    // 错误码
    private long code;
    // 错误码描述
    private String message;

    /**
     * 构造器
     * @param code
     * @param message
     */
    private ErroCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public long getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
