package com.dandinglong.exception;

public class WxException extends MyRunntimeException {
    public WxException(String message) {
        super(message);
        this.code=410;
    }
}
