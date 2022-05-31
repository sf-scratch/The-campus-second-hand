package com.lh.springboot03.service.exception;

public class DeleteGoodsCategoryException extends ServiceException{
    public DeleteGoodsCategoryException() {
        super();
    }

    public DeleteGoodsCategoryException(String message) {
        super(message);
    }

    public DeleteGoodsCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteGoodsCategoryException(Throwable cause) {
        super(cause);
    }

    protected DeleteGoodsCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
