package com.mi.nanqiao.common;

/**
 * @author mi11
 */

public enum OrderState {
    UNPAID(0,"未付款"),
    PAID(2,"已付款");

    int code;
    String msg;

    OrderState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
