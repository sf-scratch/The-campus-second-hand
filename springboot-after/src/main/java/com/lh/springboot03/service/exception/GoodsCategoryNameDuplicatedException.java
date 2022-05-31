package com.lh.springboot03.service.exception;

public class GoodsCategoryNameDuplicatedException extends ServiceException{
    public GoodsCategoryNameDuplicatedException() {
        super();
    }

    public GoodsCategoryNameDuplicatedException(String message) {
        super(message);
    }

    public GoodsCategoryNameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodsCategoryNameDuplicatedException(Throwable cause) {
        super(cause);
    }

    protected GoodsCategoryNameDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
