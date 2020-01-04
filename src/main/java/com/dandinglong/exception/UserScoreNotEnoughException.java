package com.dandinglong.exception;

public class UserScoreNotEnoughException extends MyRunntimeException {
    public UserScoreNotEnoughException(String message) {
        super(message);
        this.code=405;
    }
}
