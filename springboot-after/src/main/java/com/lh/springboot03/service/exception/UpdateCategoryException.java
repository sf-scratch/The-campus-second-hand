package com.lh.springboot03.service.exception;

public class UpdateCategoryException  extends ServiceException{
    public UpdateCategoryException() {
        super();
    }

    public UpdateCategoryException(String message) {
        super(message);
    }

    public UpdateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateCategoryException(Throwable cause) {
        super(cause);
    }

    protected UpdateCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
