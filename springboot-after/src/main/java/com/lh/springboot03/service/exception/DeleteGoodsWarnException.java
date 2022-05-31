package com.lh.springboot03.service.exception;

public class DeleteGoodsWarnException extends ServiceException {
    public DeleteGoodsWarnException() {
        super();
    }

    public DeleteGoodsWarnException(String message) {
        super(message);
    }

    public DeleteGoodsWarnException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteGoodsWarnException(Throwable cause) {
        super(cause);
    }

    protected DeleteGoodsWarnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
