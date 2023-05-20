package com.example.mkdown_java.common;

import java.util.Date;

/**
 * @author <a href="mailto:qsunyi@qq.com">Roy</a>
 * @date 2018/9/11
 */
public class Response<T> {
    //Hidden attribute
    private RsStatusCode.StatusCode statusCode;
    private int code;
    private String msg;
    private T body;
    private int count;

    public Response() {
        this.statusCode = RsStatusCode.SUCCESS;
        this.code = statusCode.getCode();
        this.msg = statusCode.getDesc();
    }

    public Response(T body) {
        this.statusCode = RsStatusCode.SUCCESS;
        this.code = statusCode.getCode();
        this.msg = statusCode.getDesc();
        this.body = body;
    }

    public Response(int code) {
        this.code = code;
    }

    public Response(String msg) {
        this.msg = msg;
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public Response(int code, String msg, T body, int count) {
        this.code = code;
        this.msg = msg;
        this.body = body;
        this.count = count;
    }

    public Response(RsStatusCode.StatusCode statusCode) {
        this.statusCode = statusCode;
        this.code = statusCode.getCode();
        this.msg = statusCode.getDesc();
    }

    public Response(RsStatusCode.StatusCode statusCode, T body) {
        this.statusCode = statusCode;
        this.code = statusCode.getCode();
        this.msg = statusCode.getDesc();
        this.body = body;
    }

    public Response(RsStatusCode.StatusCode statusCode, T body, int count) {
        this.statusCode = statusCode;
        this.code = statusCode.getCode();
        this.msg = statusCode.getDesc();
        this.body = body;
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getSysTime() {
        return new Date();
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + body +
                ", count=" + count +
                '}';
    }

}
