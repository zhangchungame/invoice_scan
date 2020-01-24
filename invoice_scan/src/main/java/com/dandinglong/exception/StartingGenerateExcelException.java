package com.dandinglong.exception;

/**
 * 生成excel中的异常
 */
public class StartingGenerateExcelException extends MyRunntimeException {
    public StartingGenerateExcelException(String message) {
        super(message);
        this.code=406;
    }
}
