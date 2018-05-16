package com.example.sbmvc.vo;

import java.io.Serializable;

public class Rdata implements Serializable{

    private int code;
    private String msg;
    private Object data;

    public Rdata(){

    }

    public Rdata(int code , String msg){
        this.code = code;
        this.msg = msg;
        //this.data = "";
    }

    public Rdata(int code, Object data) {
        this.code = code;
        this.msg = "";
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
