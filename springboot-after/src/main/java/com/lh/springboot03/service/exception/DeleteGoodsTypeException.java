package com.lh.springboot03.service.exception;

public class DeleteGoodsTypeException extends ServiceException{
    public DeleteGoodsTypeException() {
        super();
    }

    public DeleteGoodsTypeException(String message) {
        super(message);
    }

    public DeleteGoodsTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteGoodsTypeException(Throwable cause) {
        super(cause);
    }

    protected DeleteGoodsTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
