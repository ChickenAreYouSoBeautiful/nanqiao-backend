package com.mi.nanqiao.common;

public enum PayType {
    ALI_PAY(1,"支付宝支付"),
    WX_PAY(2,"微信支付"),
    CARD_PAY(3,"银行卡支付");

    int code;
    String msg;

    PayType(int code, String msg) {
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
