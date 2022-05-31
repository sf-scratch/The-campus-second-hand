package com.lh.springboot03.service.exception;

public class CancelReleaseGoodsException extends ServiceException{
    public CancelReleaseGoodsException() {
        super();
    }

    public CancelReleaseGoodsException(String message) {
        super(message);
    }

    public CancelReleaseGoodsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelReleaseGoodsException(Throwable cause) {
        super(cause);
    }

    protected CancelReleaseGoodsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
