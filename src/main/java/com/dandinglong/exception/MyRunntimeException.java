package com.dandinglong.exception;

public class MyRunntimeException extends RuntimeException {
    protected int code;

    public MyRunntimeException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
