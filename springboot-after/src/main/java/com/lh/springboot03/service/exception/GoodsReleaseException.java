package com.lh.springboot03.service.exception;

public class GoodsReleaseException extends ServiceException {
    public GoodsReleaseException() {
        super();
    }

    public GoodsReleaseException(String message) {
        super(message);
    }

    public GoodsReleaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsReleaseException(Throwable cause) {
        super(cause);
    }

    protected GoodsReleaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
