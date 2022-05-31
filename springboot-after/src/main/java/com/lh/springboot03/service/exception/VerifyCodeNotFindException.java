package com.lh.springboot03.service.exception;

public class VerifyCodeNotFindException extends ServiceException {
    public VerifyCodeNotFindException() {
        super();
    }

    public VerifyCodeNotFindException(String message) {
        super(message);
    }

    public VerifyCodeNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyCodeNotFindException(Throwable cause) {
        super(cause);
    }

    protected VerifyCodeNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
