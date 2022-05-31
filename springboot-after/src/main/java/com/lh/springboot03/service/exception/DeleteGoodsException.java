package com.lh.springboot03.service.exception;

public class DeleteGoodsException extends ServiceException{
    public DeleteGoodsException() {
        super();
    }

    public DeleteGoodsException(String message) {
        super(message);
    }

    public DeleteGoodsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteGoodsException(Throwable cause) {
        super(cause);
    }

    protected DeleteGoodsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
