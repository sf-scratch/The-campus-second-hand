package com.lh.springboot03.service.exception;

public class LoginExpirationException extends ServiceException {
    public LoginExpirationException() {
        super();
    }

    public LoginExpirationException(String message) {
        super(message);
    }

    public LoginExpirationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginExpirationException(Throwable cause) {
        super(cause);
    }

    protected LoginExpirationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
