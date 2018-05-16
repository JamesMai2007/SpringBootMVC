package com.example.sbmvc.config.exception;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {
    private int code;
    private String msg;


    public SecurityException(int code , String msg , Throwable e){
        super(msg , e);
        this.msg = msg;
        this.code = code;
    }

    public SecurityException(String msg){
        super(msg);
        this.msg = msg;
        this.code = 100;
    }

    public SecurityException(int code , String msg){
        super(msg);
        this.msg = msg;
        this.code = code;
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
}
