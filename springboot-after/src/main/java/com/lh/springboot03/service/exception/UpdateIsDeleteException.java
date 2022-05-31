package com.lh.springboot03.service.exception;

public class UpdateIsDeleteException extends ServiceException{
    public UpdateIsDeleteException() {
        super();
    }

    public UpdateIsDeleteException(String message) {
        super(message);
    }

    public UpdateIsDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateIsDeleteException(Throwable cause) {
        super(cause);
    }

    protected UpdateIsDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
