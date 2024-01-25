package vn.com.product.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final transient String fieldName;
    private final transient Object[] objects;
    private final transient String message;

    public BusinessException(String fieldName, String message, Object... objects) {
        super("BusinessException");
        this.fieldName = fieldName;
        this.message = message;
        this.objects = objects;
    }

    public BusinessException(String message) {
        super("BusinessException");
        this.fieldName = null;
        this.message = message;
        this.objects = null;
    }
}
