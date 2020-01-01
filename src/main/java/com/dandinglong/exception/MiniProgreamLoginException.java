package com.dandinglong.exception;

public class MiniProgreamLoginException extends MyRunntimeException{
    public MiniProgreamLoginException(String message) {
        super(message);
        this.code=401;
    }
}
