package com.dandinglong.exception;

public class MultipyUserException extends MyRunntimeException{

    public MultipyUserException(String message) {
        super(message);
        this.code=402;
    }
}
