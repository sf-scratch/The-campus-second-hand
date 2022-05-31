package com.lh.springboot03.service.exception;

public class UpdateGoodsStatusException extends ServiceException{
    public UpdateGoodsStatusException() {
        super();
    }

    public UpdateGoodsStatusException(String message) {
        super(message);
    }

    public UpdateGoodsStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateGoodsStatusException(Throwable cause) {
        super(cause);
    }

    protected UpdateGoodsStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
