package vn.com.product.core.exception;

import lombok.Getter;
import vn.com.product.core.constant.ExceptionMessage;

@Getter
public abstract class BaseException extends RuntimeException {
    private final transient ExceptionMessage exceptionMessage;
    private final transient String errorField;

    protected BaseException(ExceptionMessage exceptionMessage, String errorField) {
        super("BusinessException");
        this.exceptionMessage = exceptionMessage;
        this.errorField = errorField;
    }

    protected BaseException(ExceptionMessage exceptionMessage) {
        super("BusinessException");
        this.exceptionMessage = exceptionMessage;
        this.errorField = null;
    }
}
