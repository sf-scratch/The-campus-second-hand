package com.lh.springboot03.service.exception;

public class UpdatePurchaseInfoStatusException extends ServiceException {
    public UpdatePurchaseInfoStatusException() {
        super();
    }

    public UpdatePurchaseInfoStatusException(String message) {
        super(message);
    }

    public UpdatePurchaseInfoStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdatePurchaseInfoStatusException(Throwable cause) {
        super(cause);
    }

    protected UpdatePurchaseInfoStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
