package com.lh.springboot03.service.exception;

public class InsertGoodsWarnException extends ServiceException {
    public InsertGoodsWarnException() {
        super();
    }

    public InsertGoodsWarnException(String message) {
        super(message);
    }

    public InsertGoodsWarnException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertGoodsWarnException(Throwable cause) {
        super(cause);
    }

    protected InsertGoodsWarnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
