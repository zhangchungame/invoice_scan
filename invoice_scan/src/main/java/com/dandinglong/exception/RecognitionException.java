package com.dandinglong.exception;

public class RecognitionException  extends MyRunntimeException{

    public RecognitionException(String message) {
        super(message);
        this.code=404;
    }
}
