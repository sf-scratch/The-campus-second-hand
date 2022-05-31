package com.lh.springboot03.service.exception;

public class InsertGoodsException extends ServiceException{
    public InsertGoodsException() {
        super();
    }

    public InsertGoodsException(String message) {
        super(message);
    }

    public InsertGoodsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertGoodsException(Throwable cause) {
        super(cause);
    }

    protected InsertGoodsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
