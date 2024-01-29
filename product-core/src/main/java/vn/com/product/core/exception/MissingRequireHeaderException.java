package vn.com.product.core.exception;

import vn.com.product.core.constant.ExceptionMessage;

public class MissingRequireHeaderException extends BaseException{
    public MissingRequireHeaderException(ExceptionMessage exceptionMessage, String errorField) {
        super(exceptionMessage, errorField);
    }

    public MissingRequireHeaderException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage);
    }
}
