package vn.com.product.core.exception;

import lombok.Getter;

@Getter
public class TimeoutException extends RuntimeException {
    private final transient String fieldName;
    private final transient Object[] objects;
    private final transient String message;

    public TimeoutException(String fieldName, String message, Object... objects) {
        super("TimeoutException");
        this.fieldName = fieldName;
        this.message = message;
        this.objects = objects;
    }

    public TimeoutException(String message) {
        super("TimeoutException");
        this.fieldName = null;
        this.message = message;
        this.objects = null;
    }
}
