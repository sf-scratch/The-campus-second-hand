package com.lh.springboot03.service.exception;

public class UpdateGoodsWarnException extends ServiceException {
    public UpdateGoodsWarnException() {
        super();
    }

    public UpdateGoodsWarnException(String message) {
        super(message);
    }

    public UpdateGoodsWarnException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateGoodsWarnException(Throwable cause) {
        super(cause);
    }

    protected UpdateGoodsWarnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
