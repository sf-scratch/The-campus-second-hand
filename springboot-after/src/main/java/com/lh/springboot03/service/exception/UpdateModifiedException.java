package com.lh.springboot03.service.exception;

public class UpdateModifiedException extends ServiceException {
    public UpdateModifiedException() {
        super();
    }

    public UpdateModifiedException(String message) {
        super(message);
    }

    public UpdateModifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateModifiedException(Throwable cause) {
        super(cause);
    }

    protected UpdateModifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
