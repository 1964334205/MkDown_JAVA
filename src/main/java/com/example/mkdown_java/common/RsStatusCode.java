package com.example.mkdown_java.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author <a href="mailto:qsunyi@qq.com">Roy</a>
 * @date 2018/8/28
 */
public class RsStatusCode {
    public static final StatusCode SUCCESS = new StatusCode(0, "success");
    public static final StatusCode SYSTEM_ERROR = new StatusCode(-1, "system.error");
    public static final StatusCode APPLICATION_ERROR = new StatusCode(-2, "application.error");
    public static final StatusCode FAILED = new StatusCode(-3, "failed");
    public static final StatusCode API_SYSTEM_ERROR = new StatusCode(-11, "api.system.error");
    public static final StatusCode API_APPLICATION_ERROR = new StatusCode(-13, "api.application.error");

    public static final StatusCode UPLOAD_ERROR = new StatusCode(-4, "upload.error");

    public static final StatusCode LOGIN_ERROR_AUTHENTICATION = new StatusCode(-3, "login.error.authentication");
    public static final StatusCode LOGIN_ERROR_UNKNOWN_ACCOUNT = new StatusCode(-3, "login.error.unknown.account");
    public static final StatusCode LOGIN_ERROR_INCORRECT_CREDENTIALS = new StatusCode(-3, "login.error.incorrect.credentials");
    public static final StatusCode LOGIN_ERROR_LOCKED_ACCOUNT = new StatusCode(-3, "login.error.locked.account");
    public static final StatusCode LOGIN_ERROR_DISABLED_ACCOUNT = new StatusCode(-3, "login.errologin.error.incorrect.credentialsr.disabled.account");

    static public class StatusCode {
        private int code;
        private String msgKey;

        public StatusCode(int code, String msgKey) {
            this.code = code;
            this.msgKey = msgKey;
        }

        public int getCode() {
            return code;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public String getDesc() {
            return SpringContext.getBean(MessageSource.class).getMessage(msgKey, null, LocaleContextHolder.getLocale());
        }

        public String getDesc(Object[] objs) {
            return SpringContext.getBean(MessageSource.class).getMessage(msgKey, objs, LocaleContextHolder.getLocale());
        }
    }

}
