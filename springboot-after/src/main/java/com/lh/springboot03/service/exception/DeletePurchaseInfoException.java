package com.lh.springboot03.service.exception;

public class DeletePurchaseInfoException extends ServiceException {
    public DeletePurchaseInfoException() {
        super();
    }

    public DeletePurchaseInfoException(String message) {
        super(message);
    }

    public DeletePurchaseInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeletePurchaseInfoException(Throwable cause) {
        super(cause);
    }

    protected DeletePurchaseInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
