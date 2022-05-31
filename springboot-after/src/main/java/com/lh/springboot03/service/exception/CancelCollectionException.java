package com.lh.springboot03.service.exception;

public class CancelCollectionException extends ServiceException{
    public CancelCollectionException() {
        super();
    }

    public CancelCollectionException(String message) {
        super(message);
    }

    public CancelCollectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelCollectionException(Throwable cause) {
        super(cause);
    }

    protected CancelCollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
