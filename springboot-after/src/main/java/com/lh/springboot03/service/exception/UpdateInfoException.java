package com.lh.springboot03.service.exception;

public class UpdateInfoException extends ServiceException {
    public UpdateInfoException() {
        super();
    }

    public UpdateInfoException(String message) {
        super(message);
    }

    public UpdateInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateInfoException(Throwable cause) {
        super(cause);
    }

    protected UpdateInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
