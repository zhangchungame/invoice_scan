package com.dandinglong.exception;

public class BacthProcessException extends MyRunntimeException {
    public BacthProcessException(String message) {
        super(message);
        this.code=410;
    }
}
