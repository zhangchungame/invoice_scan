package com.dandinglong.exception;

public class NoBaiduAppCanUseException extends MyRunntimeException {

    public NoBaiduAppCanUseException(String message) {
        super(message);
        this.code=403;
    }
}
